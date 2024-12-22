package Strategy;

import java.io.*;
import java.util.ArrayList;

public class TXTStrategy implements ReadWriteStrategy{
    public ArrayList<String> read(String filename, boolean is_expression) throws IOException {
        ArrayList<String> results = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null) {
                results.add(line);
            }
            reader.close();
            return results;
        }
        catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            return results;
        }
    }

    public void write(String outputFile, ArrayList<String> results, boolean is_expression) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
        for (String result : results) {
            writer.write(result);
            writer.newLine();
        }
        writer.close();
    }
}
