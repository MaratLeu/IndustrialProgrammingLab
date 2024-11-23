import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Zapis {
    private String expression, result;
    public Zapis(String expression, String result) {
        this.expression = expression;
        this.result = result;
    }
    public String getExpression() {
        return expression;
    }
    public String getResult() {
        return result;
    }
    public void setExpression(String expression) {
        this.expression = expression;
    }
    public void setResult(String result) {
        this.result = result;
    }
    public void write_to(String filename) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        writer.write(expression);
        writer.newLine();
        writer.write(result);
        writer.close();
    }
}
