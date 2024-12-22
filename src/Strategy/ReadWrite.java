package Strategy;

import com.google.protobuf.InvalidProtocolBufferException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;

public class ReadWrite {
    private ReadWriteStrategy strategy;

    public void setStrategy(ReadWriteStrategy strategy) {
        this.strategy = strategy;
    }

    public ArrayList<String> read(String file, boolean isExpression) throws IOException, ParserConfigurationException, SAXException {
        return strategy.read(file, isExpression);
    }

    public void write(String file, ArrayList<String> data, boolean isExpression) throws IOException, InvalidProtocolBufferException {
        strategy.write(file, data, isExpression);
    }
}

