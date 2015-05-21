package ua.pbank.onec.onecpb;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Vsevolod on 09.04.2015.
 */
public class MyXMLParser {
    //File f;
    DocumentBuilderFactory factory;
    DocumentBuilder builder;
    Document document;
    Element root;


    public MyXMLParser(String url) throws ParserConfigurationException, IOException, SAXException {
        factory = DocumentBuilderFactory.newInstance();
        builder = factory.newDocumentBuilder();
        document = builder.parse(url);
        root = document.getDocumentElement();
    }

    public NodeList parseXML(String tag) {
    // для простоты сразу берем message
    //Element message = (Element) root.getElementsByTagName(tag).item(1);
    //String textContent = message.getTextContent(); // тоже для упрощения
    return root.getElementsByTagName(tag);
}



}
