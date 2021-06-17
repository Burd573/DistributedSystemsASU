package client;

import buffers.ResponseProtos.Response;
import utils.ProtoUtils;

import java.io.*;
import java.net.Socket;

class SockBaseClient {

    public static void mainMenu(BufferedReader stdin, OutputStream out) throws IOException {
        System.out.println("\nWhat would you like to do? \n 1 - to see the leader board \n 2 - to enter a game \n 3 - Quit");
        String menuInput = stdin.readLine();
        while (!menuInput.equals("1") && !menuInput.equals("2") && !menuInput.equals("3")) {
            System.out.println("Invalid Answer. Please type your choice");
            menuInput = stdin.readLine();
        }
        switch (menuInput) {
            case ("1"):
                System.out.println("Sending request to view Leaderboard to server...");
                ProtoUtils.sendLeader(out);
                break;
            case ("2"):
                System.out.println("Sending new game request to server...");
                ProtoUtils.sendNewGame(out);
                break;
            case ("3"):
                System.out.println("Sending quit request to server...");
                ProtoUtils.sendQuit(out);
                break;
        }
    }

    public static void main (String args[]) throws Exception {
        int port = 9099; // default port
        Socket serverSocket = null;
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        OutputStream out = null;
        InputStream in = null;
        String userInput;
        String userName;
        Boolean isRunning = true;


        // Make sure two arguments are given
        if (args.length != 2) {
            System.out.println("Expected arguments: <host(String)> <port(int)>");
            System.exit(1);
        }
        String host = args[0];
        try {
            port = Integer.parseInt(args[1]);
        } catch (NumberFormatException nfe) {
            System.out.println("[Port] must be integer");
            System.exit(2);
        }

        System.out.println("Please provide your name for the server. ( ͡❛ ͜ʖ ͡❛)");
        userName = stdin.readLine();;

        try {
            serverSocket = new Socket(host, port);
            out = serverSocket.getOutputStream();
            in = serverSocket.getInputStream();

            ProtoUtils.sendName(userName, out);

            while (isRunning) {
                Response serverResp = Response.parseDelimitedFrom(in);
                String players = serverResp.toString();

                switch (serverResp.getResponseType()) {
                    case GREETING:
                        System.out.println(serverResp.getGreeting());
                        mainMenu(stdin, out);
                        break;
                    case LEADER:
                        System.out.println("\n================ LEADERBOARD ================");
                        System.out.println(players);
                        System.out.println("Press the ENTER key to go to return to the main menu...\n");
                        userInput = stdin.readLine();
                        ProtoUtils.sendName(userName, out);
                        break;
                    case TASK:
                        System.out.println("IMAGE:\n" + serverResp.getImage());
                        System.out.println("TASK:\n" + serverResp.getTask());
                        System.out.println("\nPlease Type An Answer:\n");
                        userInput = stdin.readLine();
                        ProtoUtils.sendAnswer(userInput, out);
                        break;
                    case WON:
                        System.out.println("\nWinner!!\n");
                        System.out.println("Press the ENTER key to go to return to the main menu...\n");
                        ProtoUtils.sendName(userName, out);
                        break;
                    case ERROR:
                        System.out.println("Error");
                        ProtoUtils.errorResponse(out);
                        break;
                    case BYE:
                        isRunning = false;
                        break;
                }
            }

        } catch (Exception e) {
            System.out.println("Couldn't connect to server. Please try again later.");
            e.printStackTrace();
        } finally {
            if (in != null)   in.close();
            if (out != null)  out.close();
            if (serverSocket != null) serverSocket.close();
        }
    }




}
