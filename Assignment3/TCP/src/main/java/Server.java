import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String args[]) {
        JsonUtils jsonUtils = new JsonUtils();
        NetworkUtils networkUtils = new NetworkUtils();
        ServerSocket socket = null;
        InputStream fromClient = null;
        OutputStream toClient = null;
        int port = 8080;
        if(args.length == 1){
            port = Integer.parseInt(args[0]);
        }

        try {
            String name;
            int numRiddles;
            int time;
            String response;
            socket = new ServerSocket(port);
            System.out.println("Ready at port " + port);

            while (true) {
                System.out.println("Waiting for Client...");
                Socket clientSock = socket.accept();

                fromClient = clientSock.getInputStream();
                toClient = clientSock.getOutputStream();

                System.out.println("Connected to Client");
                networkUtils.sendToClient(toClient, jsonUtils.message("Connected to Server \nAll aboard Blaine the Mono\nBlaine is a pain"));
                name = networkUtils.getName(toClient, fromClient);
                numRiddles = networkUtils.getNumQuestions(toClient, fromClient, name);
                time = numRiddles * 5;
                networkUtils.sendToClient(toClient, jsonUtils.message("Blaine will only stop you can correctly answer " + numRiddles + " riddles. You have " + time +
                        " seconds before we crash into Topeka!!"));

                networkUtils.startGame(toClient,fromClient,numRiddles,name);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e){
            System.out.println("Client Disconnected");
        }
    }







}