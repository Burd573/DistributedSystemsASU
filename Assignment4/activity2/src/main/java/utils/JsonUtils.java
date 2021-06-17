package utils;

import buffers.ResponseProtos;
import buffers.ResponseProtos.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class JsonUtils {


    public static void writeToLeaderboard(String name, int win, int login) {
        try {
            InputStream inS = new FileInputStream("src/main/resources/leaderboard.json");
            JSONObject jsonObj = (JSONObject) new JSONTokener(inS).nextValue();
            JSONArray players = jsonObj.getJSONArray("players");

            for (int i = 0; i < players.length(); i++) {
                JSONObject player = players.getJSONObject(i);
                if (player.getString("name").equals(name)) {
                    int numWins = player.getInt("wins");
                    int numLogins = player.getInt("logins");
                    numWins += win;
                    numLogins += login;
                    player.put("wins", numWins);
                    player.put("logins", numLogins);

                    try {
                        FileWriter file = new FileWriter("src/main/resources/leaderboard.json");
                        file.write(jsonObj.toString());
                        file.close();
                    } catch (IOException io) {
                        System.out.println("Error writing to leaderboard");
                    }
                    return;
                }
            }

            JSONObject newPlayer = new JSONObject();
            newPlayer.put("name", name);
            newPlayer.put("wins", win);
            newPlayer.put("logins", login);
            players.put(newPlayer);
            try {
                FileWriter file = new FileWriter("src/main/resources/leaderboard.json");
                file.write(jsonObj.toString());
                file.close();
            } catch (IOException io) {
                System.out.println("Error writing to leaderboard");
            }

        } catch (Exception e) {
            System.out.println("Error retrieving leaderboard");
        }
    }

    public static void readLeaderBoard(OutputStream out) {
        Response.Builder leaderResp = Response.newBuilder()
                .setResponseType(Response.ResponseType.LEADER);
        List<ResponseProtos.Entry> protoEntries = new ArrayList<ResponseProtos.Entry>();

        try {
            InputStream in = new FileInputStream("src/main/resources/leaderboard.json");
            JSONObject jsonObj = (JSONObject) new JSONTokener(in).nextValue();
            JSONArray players = jsonObj.getJSONArray("players");

            for (int i = 0; i < players.length(); i++) {
                String playerName = players.getJSONObject(i).getString("name");
                int numWins = players.getJSONObject(i).getInt("wins");
                int numLogins = players.getJSONObject(i).getInt("logins");
                protoEntries.add(ProtoUtils.entryBuilder(playerName, numWins, numLogins));
            }
            leaderResp.addAllLeader(protoEntries);
            Response fullResponse = leaderResp.build();
            fullResponse.writeDelimitedTo(out);

        } catch (Exception e) {
            System.out.println("Error retrieving Leaderboard");
            e.printStackTrace();
        }
    }

    public static JSONArray getQuestions() throws IOException {
        InputStream inS = new FileInputStream("src/main/resources/data.json");
        JSONObject jsonObj = (JSONObject) new JSONTokener(inS).nextValue();
        JSONArray data = jsonObj.getJSONArray("data");
        return data;
    }

    public static JSONObject getRandomQuestion(JSONArray questions) {
        Random rand = new Random();
        int index = rand.nextInt(questions.length());
        return questions.getJSONObject(index);
    }

}
