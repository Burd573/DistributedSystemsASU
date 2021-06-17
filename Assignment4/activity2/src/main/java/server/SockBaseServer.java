package server;

import buffers.RequestProtos.Logs;
import buffers.RequestProtos.Message;
import buffers.RequestProtos.Request;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.JsonUtils;
import utils.ProtoUtils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

class SockBaseServer implements Runnable{
    static String logFilename = "logs.txt";

    String clientName;
    String questionData;
    String answerData;
    String clientAnswer;
    String verifyName = "";

    InputStream in = null;
    OutputStream out = null;
    Socket clientSocket = null;
    Game game;

    public SockBaseServer(Socket clientSocket, Game game) {
        this.clientSocket = clientSocket;
        this.game = game;
        try{
            in = clientSocket.getInputStream();
            out = clientSocket.getOutputStream();
        } catch (Exception e) {
            System.out.println("Error in constructor: " + e);
        }
    }

    public void run(){
        int revealSegments = 3; // number of segments needed to reveal the full image
        JSONArray arr = null;
        Message message;

        try {
            arr = JsonUtils.getQuestions();
        } catch (IOException e) {
            System.out.println("Error getting question data");
        }

        System.out.println("Ready...");
        try {

            while (true) {
                Request clientReq = Request.parseDelimitedFrom(in);
                JSONObject question = JsonUtils.getRandomQuestion(arr);

                switch (clientReq.getOperationType()) {
                    case NAME:
                        clientName = clientReq.getName();

                        //When exiting the leaderboard, need to send name to get back to main menu
                        //but it was increasing the login by one every time. This ensures that the
                        //client returning to the main menu will not effect their login attempts
                        if(!(verifyName.equals(clientName))){
                            System.out.println("New client connected: " + clientName);
                            JsonUtils.writeToLeaderboard(clientName, 0, 1);
                            message = Message.CONNECT;
                            writeToLog(clientName,message);
                        } else {
                            System.out.println("Client in main menu: " + clientName);
                            JsonUtils.writeToLeaderboard(clientName, 0, 0);

                        }
                        verifyName = clientName;
                        System.out.println("Sending greeting...");
                        ProtoUtils.greetResponse(clientName, out);
                        break;

                    case LEADER:
                        System.out.println(clientName + " requests to see leaderboard.");
                        System.out.println("Sending leaderboard...");
                        JsonUtils.readLeaderBoard(out);
                        break;

                    case NEW:
                        System.out.println(clientName + " requests a new game.");
                        questionData = question.getString("question");
                        answerData = question.getString("answer");
                        game.newGame();
                        System.out.println("Question sent: " + questionData);
                        System.out.println("Answer expected: " + answerData);
                        ProtoUtils.taskResponse(game, questionData, out);
                        message = Message.START;
                        writeToLog(clientName,message);
                        break;

                    case ANSWER:
                        clientAnswer = clientReq.getAnswer();
                        System.out.println(clientName + " guessed: " + clientAnswer);
                        System.out.println("Answer expected: " + answerData);
                        if (game.getIdx() == game.getIdxMax() || game.getWon()) {
                            game.setWon();
                            ProtoUtils.wonResponse(out);
                            JsonUtils.writeToLeaderboard(clientName, 1, 0);
                            message = Message.WIN;
                            writeToLog(clientName,message);
                            break;
                        }
                        if (clientAnswer.equalsIgnoreCase(answerData)) {
                            System.out.println(clientName + " answered correctly.");
                            System.out.println("Revealing segments and sending new question");
                            for (int i = 0; i < (game.getIdxMax() / revealSegments); i++) {
                                if (game.getIdxMax() > game.getIdx())
                                    game.replaceOneCharacter();
                            }
                            question = JsonUtils.getRandomQuestion(arr);
                            questionData = question.getString("question");
                            answerData = question.getString("answer");
                            System.out.println("Question sent: " + questionData);
                            System.out.println("Answer expected: " + answerData);

                            ProtoUtils.taskResponse(game, "CORRECT!\n" + questionData, out);
                        } else {
                            System.out.println(clientName + " answered incorrectly. Sending question again.");
                            ProtoUtils.taskResponse(game, "WRONG!\n" + questionData, out);
                        }
                        break;
                    case QUIT:
                        System.out.println(clientName + " is quitting..");
                        ProtoUtils.byeResponse(out);
                        break;
                }
            }
        } catch (Exception ex) {
            System.out.println("Client disconnected: " + clientName);
        } finally {
            try {
                if (out != null) out.close();
                if (in != null) in.close();
                if (clientSocket != null) clientSocket.close();
            } catch (Exception ex) {
                System.out.println("Error while closing client connection...");
            }
        }
    }


    /**
     * Reading the current log file
     *
     * @return Logs.Builder a builder of a logs entry from protobuf
     */
    public Logs.Builder readLogFile() throws Exception {
        Logs.Builder logs = Logs.newBuilder();

        try {
            // just read the file and put what is in it into the logs object
            return logs.mergeFrom(new FileInputStream("logs.txt"));
        } catch (FileNotFoundException e) {
            System.out.println(logFilename + ": File not found.  Creating a new file.");
            return logs;
        }
    }

    /**
     * Writing a new entry to our log
     *
     * @param name    - Name of the person logging in
     * @param message - type Message from Protobuf which is the message to be written in the log (e.g. Connect)
     * @return String of the new hidden image
     */
    public void writeToLog(String name, Message message) {
        try {
            // read old log file
            Logs.Builder logs = readLogFile();

            // get current time and data
            Date date = java.util.Calendar.getInstance().getTime();

            // we are writing a new log entry to our log
            // add a new log entry to the log list of the Protobuf object
            logs.addLog(date.toString() + ": " + name + " - " + message);

            // open log file
            FileOutputStream output = new FileOutputStream("logs.txt");
            Logs logsObj = logs.build();

            // This is only to show how you can iterate through a Logs object which is a protobuf object
            // which has a repeated field "log"

            for (String log : logsObj.getLogList()) {

                System.out.println(log);
            }

            // write to log file
            logsObj.writeTo(output);
        } catch (Exception e) {
            System.out.println("Issue while trying to save");
            e.printStackTrace();
        }
    }

    public static void main(String args[]) throws Exception {
        Socket clientSocket = null;
        ServerSocket serverSock = null;
        Game game = new Game();

        if (args.length != 2) {
            System.out.println("Expected arguments: <port(int)> <delay(int)>");
            System.exit(1);
        }
        int port = 9099; // default port
        int sleepDelay = 10000; // default delay

        try {
            port = Integer.parseInt(args[0]);
            sleepDelay = Integer.parseInt(args[1]);
        } catch (NumberFormatException nfe) {
            System.out.println("[Port|sleepDelay] must be an integer");
            System.exit(2);
        }

        try {
            serverSock = new ServerSocket(port);
            System.out.println("Server running at port: " + port);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(2);
        }

        try {
            while (true) {
                System.out.println("Waiting for clients...");
                clientSocket = serverSock.accept();
                System.out.println("Client connection successful...");
                SockBaseServer server = new SockBaseServer(clientSocket,game);
                Thread t = new Thread(server);
                t.start();

            }
        } catch (Exception e) {
            System.out.println("Closing server.");
            serverSock.close();
            System.exit(0);
        }
    }

}



