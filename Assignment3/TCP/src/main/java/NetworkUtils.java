import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class NetworkUtils {
    JsonUtils jsonUtils = new JsonUtils();
    // https://mkyong.com/java/java-convert-byte-to-int-and-vice-versa/
    public static byte[] intToBytes(final int data) {
        return new byte[] { (byte) ((data >> 24) & 0xff), (byte) ((data >> 16) & 0xff), (byte) ((data >> 8) & 0xff),
                (byte) ((data >> 0) & 0xff), };
    }

    // https://mkyong.com/java/java-convert-byte-to-int-and-vice-versa/
    public static int bytesToInt(byte[] bytes) {
        return ((bytes[0] & 0xFF) << 24) | ((bytes[1] & 0xFF) << 16) | ((bytes[2] & 0xFF) << 8) | ((bytes[3] & 0xFF) << 0);
    }

    public static void Send(OutputStream out, byte... bytes) throws IOException {
        out.write(intToBytes(bytes.length));
        out.write(bytes);
        out.flush();
    }

    // read the bytes on the stream
    // read the bytes on the stream
    private static byte[] Read(InputStream in, int length) throws IOException {
        byte[] bytes = new byte[length];
//        System.out.println("Read in bytes: " + length);
        int bytesRead = 0;
        try {
            bytesRead = in.read(bytes, 0, length);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        if (bytesRead < length && bytesRead > 0) {
            byte[] newBytes = Read(in, length-bytesRead);
            System.arraycopy(newBytes, 0, bytes, bytesRead, newBytes.length);
        }
        return bytes;
    }

    // first 4 bytes we read give us the length of the message we are about to receive
    // next we call read again with the length of the actual bytes in the data we are interested in
    public static byte[] Receive(InputStream in) throws IOException {
        byte[] lengthBytes = Read(in, 4);
        if (lengthBytes == null)
            return new byte[0];
        int length = NetworkUtils.bytesToInt(lengthBytes);
        byte[] message = Read(in, length);
        if (message == null)
            return new byte[0];
        return message;
    }

    /**
     * Sends JSONObject to client
     *
     * @param out
     * @param obj
     */
    public  void sendToClient(OutputStream out, JSONObject obj) throws IOException {
        byte[] outputBytes = JsonUtils.toByteArray(obj);
        Send(out, outputBytes);
    }

    /**
     * Receives JSON from client
     *
     * @param in
     */
    public  JSONObject receiveFromClient(InputStream in) throws IOException {
        byte[] inputBytes = Receive(in);
        JSONObject input = JsonUtils.fromByteArray(inputBytes);
        return input;
    }

    public String getName(OutputStream out, InputStream in) throws IOException {
        sendToClient(out, jsonUtils.question("Enter your name to receive your boarding pass"));
        JSONObject response = receiveFromClient(in);
        String name = response.getString("data");
        System.out.println("Received from client: " + name);
        return name;
    }

    public  int getNumQuestions(OutputStream out, InputStream in, String name) throws IOException {
        int numQuestions = 0;
        Exception exc ;
        sendToClient(out, jsonUtils.question("Welcome " + name + "! How many riddles can you answer before we arrive in Topeka?"));
        do {
            exc = null;
            JSONObject response = receiveFromClient(in);
            try{
                numQuestions = response.getInt("data");
            } catch(JSONException e){
                sendToClient(out, jsonUtils.question("Please enter an integer"));
                exc = e;
            }
        }while(exc != null);

        System.out.println("Received response from client: " + numQuestions);
        return numQuestions;
    }

    public void startGame(OutputStream out, InputStream in, int numQuestions,String name) throws IOException {
        String start = "";
        while(!start.equalsIgnoreCase("start")) {
            sendToClient(out, jsonUtils.question("enter \"Start\" to begin"));
            JSONObject response = receiveFromClient(in);
            start = response.getString("data");
            System.out.println("Received response from client: " + start);
        }
        playGame(out,in,numQuestions,name);
//        return start;
    }


    public void playGame(OutputStream out, InputStream in, int numQuestions,String name) throws IOException {
        int time = numQuestions*5;
        long t = System.currentTimeMillis();
        long end = t + (time*1000);
        int numCorrect = 0;
        boolean winner = false;
        JSONArray questions = jsonUtils.getQuestions();
        JSONObject question = jsonUtils.getRandomQuestion(questions);
        while(System.currentTimeMillis() < end) {
            sendToClient(out, question);
            String response = receiveFromClient(in).getString("data");
            System.out.println("Correct Answer: " + question.getString("answer"));
            System.out.println("Player Answer: " + response);

            if (response.equalsIgnoreCase(question.getString("answer"))) {
                sendToClient(out, jsonUtils.message("Correct!"));
                numCorrect++;
                if(numCorrect == numQuestions){
                    winner = true;
                    break;
                }
                question = jsonUtils.getRandomQuestion(questions);
            } else if (response.equalsIgnoreCase("next")){
                question = jsonUtils.getRandomQuestion(questions);
            } else{
                sendToClient(out, jsonUtils.message("Wrong!"));
            }
        }
        if(!winner) {
            sendToClient(out, jsonUtils.message("Time is up!"));
            playAgain(out,in,name);
        } else {
            sendToClient(out, jsonUtils.message("You win!!"));
            playAgain(out,in,name);
        }
    }

    public void playAgain(OutputStream out, InputStream in, String name) throws IOException {
        while(true) {
            sendToClient(out, jsonUtils.question("Enter your name to play again or enter \"quit\" to quit"));
            JSONObject response = receiveFromClient(in);
            String input = response.getString("data");
            if (input.contentEquals(name)) {
                int numQuestions = getNumQuestions(out,in,name);
                startGame(out,in,numQuestions,name);
            } else if (input.equalsIgnoreCase("quit")) {
                sendToClient(out, jsonUtils.message("We hope you enjoyed your ride on Blaine the Mono!"));
                return;
            } else{
                sendToClient(out, jsonUtils.message("Incorrect Input"));
            }
        }
    }
}
