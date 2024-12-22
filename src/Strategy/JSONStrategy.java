package Strategy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;

public class JSONStrategy implements ReadWriteStrategy {
    @Override
    public ArrayList<String> read(String jsonFile, boolean isExpression) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(jsonFile))) {
            ArrayList<String> data = new ArrayList<>();
            StringBuilder jsonString = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonString.append(line);
            }

            JSONArray jsonArray = new JSONArray(jsonString.toString());

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String content = jsonObject.getString(isExpression ? "expression" : "result");
                data.add(content);
            }
            return data;
        } catch (JSONException | IOException e) {
            throw new IOException("Ошибка чтения JSON: " + e.getMessage(), e);
        }
    }

    @Override
    public void write(String filename, ArrayList<String> data, boolean isExpression) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            JSONArray array = new JSONArray();
            for (String item : data) {
                JSONObject object = new JSONObject();
                object.put(isExpression ? "expression" : "result", item);
                array.put(object);
            }
            writer.write(array.toString(2));
        } catch (IOException | JSONException e) {
            throw new IOException("Ошибка записи JSON: " + e.getMessage(), e);
        }
    }
}

