import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class JsonUtils {
    public static JSONObject fromByteArray(byte[] bytes) {
        String jsonString = new String(bytes);
        return new JSONObject(jsonString);
    }

    public static byte[] toByteArray(JSONObject object) {

        return object.toString().getBytes();
    }

    /**
     * Creates JSON object of type message to be sent to client
     *
     * @param input
     * @return
     */
    public  JSONObject message(String input) {
        JSONObject json = new JSONObject();
        json.put("ok", true);
        json.put("type", "text");
        json.put("data", input);
        return json;
    }

    public  JSONObject question(String input) {
        JSONObject json = new JSONObject();
        json.put("ok", true);
        json.put("type", "question");
        json.put("data", input);
        return json;
    }

    public JSONArray getQuestions() throws IOException {
        InputStream is = getClass().getClassLoader().getResourceAsStream("data.json");
        StringBuilder textBuilder = new StringBuilder();
        try (InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {
            String line;
            while ((line = reader.readLine()) != null) {
                textBuilder.append(line);
            }
        }
        return new JSONArray(textBuilder.toString());
    }

    public JSONObject getRandomQuestion(JSONArray questions) {
        Random rand = new Random();
        int index = rand.nextInt(questions.length());
        return questions.getJSONObject(index);
    }
}