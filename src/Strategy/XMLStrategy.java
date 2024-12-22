package Strategy;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class XMLStrategy implements ReadWriteStrategy{

    @Override
    public ArrayList<String> read(String xml_file, boolean isExpression) throws ParserConfigurationException, SAXException, IOException {
        ArrayList<String> data = new ArrayList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(xml_file);

        String tagName = isExpression ? "expression" : "result";
        NodeList elements = doc.getElementsByTagName(tagName);
        for (int i = 0; i < elements.getLength(); i++) {
            Element element = (Element) elements.item(i);
            String content = element.getTextContent();
            data.add(content);
        }
        return data;
    }

    @Override
    public void write(String filename, ArrayList<String> data, boolean is_expression) throws IOException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            Element rootElement = doc.createElement(is_expression ? "expressions" : "results");
            doc.appendChild(rootElement);

            for (String item : data) {
                Element element = doc.createElement(is_expression ? "expression" : "result");
                element.setTextContent(item);
                rootElement.appendChild(element);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filename));
            transformer.transform(source, result);
        }
        catch (ParserConfigurationException | TransformerException e) {
            System.err.println(e.getMessage());
        }
    }
}
