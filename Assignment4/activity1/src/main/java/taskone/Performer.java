/**
  File: Performer.java
  Author: Student in Fall 2020B
  Description: Performer class in package taskone.
*/

package taskone;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

/**
 * Class: Performer 
 * Description: Threaded Performer for server tasks.
 */
public class Performer {

    private StringList state;
    private Socket conn;

    public Performer(Socket sock, StringList strings) {
        this.conn = sock;
        this.state = strings;
    }

    public JSONObject quit() {
        JSONObject json = new JSONObject();
        json.put("datatype", 0);
        json.put("type", "quit");
        json.put("data", String.valueOf(this.state.size()));
        System.out.println("JSON to send: " + json);
        return json;
    }

    public JSONObject add(String str) {
        JSONObject json = new JSONObject();
        json.put("datatype", 1);
        json.put("type", "add");
        state.add(str);
        json.put("data", state.toString());
        return json;
    }

    public JSONObject pop(){
        JSONObject json = new JSONObject();
        json.put("datatype", 2);
        json.put("type", "pop");
        if(state.strings.isEmpty()) {
            json.put("data","empty");
        } else{
            String top = state.strings.get(state.size()-1);
            json.put("data",top);
            state.strings.remove(state.size()-1);
        }
        System.out.println("Sending " + json + " to server");
        return json;
    }

    public JSONObject display(){
        String display = "";
        JSONObject json = new JSONObject();
        json.put("datatype", 3);
        json.put("type", "display");
        for(String s : state.strings){
            display +=  s + "\n";
        }
        json.put("data",state.toString());
        return json;
    }

    public JSONObject count() {
        JSONObject json = new JSONObject();
        json.put("datatype", 4);
        json.put("type", "count");
        json.put("data", String.valueOf(this.state.size()));
        System.out.println("JSON to send: " + json);
        return json;
    }

    public JSONObject switching(int num1, int num2){
        JSONObject json = new JSONObject();
        json.put("datatype", 5);
        json.put("type", "switch");

        try{
            String index1 = state.strings.get(num1);
            String index2 = state.strings.get(num2);

            state.strings.set(num1,index2);
            state.strings.set(num2,index1);

            json.put("data","Success");
        } catch(Exception e){
            json.put("data","Error");
        }

        return json;
    }

    public static JSONObject error(String err) {
        JSONObject json = new JSONObject();
        json.put("error", err);
        return json;
    }

    public void doPerform() {
        boolean quit = false;
        OutputStream out = null;
        InputStream in = null;
        try {
            out = conn.getOutputStream();
            in = conn.getInputStream();
            System.out.println("Server connected to client:");
            while (!quit) {
                byte[] messageBytes = NetworkUtils.receive(in);
                JSONObject message = JsonUtils.fromByteArray(messageBytes);
                JSONObject returnMessage = new JSONObject();
   
                int choice = message.getInt("selected");
                    switch (choice) {
                        case (0):
                            returnMessage = quit();
                            break;
                        case (1):
                            String inStr = (String) message.get("data");
                            returnMessage = add(inStr);
                            break;
                        case (2):
                            System.out.println("Popping...");
                            returnMessage = pop();
                            break;
                        case (3):
                            returnMessage = display();
                            break;
                        case (4):
                            returnMessage = count();
                            break;
                        case (5):
                            String data = (String) message.get("data");
                            String[] params = data.split(" ");
                            int num1 = Integer.parseInt(params[0]);
                            int num2 = Integer.parseInt(params[1]);
                            returnMessage = switching(num1-1, num2-1);
                            break;
                        default:
                            returnMessage = error("Invalid selection: " + choice 
                                    + " is not an option");
                            break;
                    }
                // we are converting the JSON object we have to a byte[]
                byte[] output = JsonUtils.toByteArray(returnMessage);
                NetworkUtils.send(out, output);
            }
            // close the resource
            System.out.println("close the resources of client ");
            out.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
