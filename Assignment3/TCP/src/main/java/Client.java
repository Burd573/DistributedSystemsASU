import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner((System.in));
        InputStream fromServer = null;
        OutputStream toServer = null;


        int port = 8080; // default port
        String host = "localhost"; // default host

        if (args.length != 2 && args.length != 0) {
            System.out.println("Use 'gradle runClientTCP -Pport=[] -Phost=[]'");
            System.out.println("Or 'gradle runClientTCP' for port=8080 & localhost");
            System.exit(0);
        }
        try {
            if (args.length == 2) { // change port num if it was provided
                port = Integer.parseInt(args[1]);
                host = args[0];
            }
        } catch (NumberFormatException nfe) {
            System.out.println("[Pport] must be an integer!");
            nfe.printStackTrace();
            System.exit(1);
        }
        try{
            Socket socket = new Socket(host, port);
            fromServer = socket.getInputStream();
            toServer = socket.getOutputStream();

            while(true){
                JSONObject question = receiveFromServer(fromServer,toServer);
                String q = question.getString("data");
                String response;

                System.out.println(q);
                if(question.get("type").equals("question")){
                    response = scan.next();
                    sendToServer(toServer,response);
                    System.out.println("sent: " + response);
                }
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static JSONObject receiveFromServer(InputStream in, OutputStream out) throws IOException {
        byte[] inputBytes = NetworkUtils.Receive(in);
        JSONObject input = JsonUtils.fromByteArray(inputBytes);
        return input;
    }


    public static void sendToServer(OutputStream out, String answer) throws IOException {
        JSONObject ans = new JSONObject();
        ans.put("data", answer);
        byte[] outputBytes = JsonUtils.toByteArray(ans);
        NetworkUtils.Send(out, outputBytes);
    }


}
