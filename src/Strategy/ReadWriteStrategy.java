package Strategy;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;

public interface ReadWriteStrategy {
    ArrayList<String> read(String file, boolean isExpression) throws IOException, ParserConfigurationException, SAXException;
    void write(String file, ArrayList<String> data, boolean isExpression) throws IOException;
}

