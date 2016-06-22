package br.udesc.ceavi.core.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author Ricardo Augusto Küstner
 */
public class DistanceMatrixCalculator {

    private String baseUrl            = "https://maps.googleapis.com/maps/api/distancematrix/xml?";
    private String lang               = "PT-br";
    private final String assurancekey = "AIzaSyD47B3DSRESznB3S37SmhnqFfNq8xMdEbM", //Ricardo
                         key          = "AIzaSyA-anpZjdAwBjXHZcLrb-eNvh4zPQCCYpA"; // Samuel

    private String origin;
    private List<String> destinations = new ArrayList();

    public void setOrigin(double latitude, double longitude) {
        this.origin = latitude + "," + longitude;
    }

    public void addDestination(double latitude, double longitude) {
        String destination = latitude + "," + longitude;

        this.destinations.add(destination);
    }

    public List<String> getResult() {
        String line, outputString = "";

        try {
            URL url = new URL(buildUrl());

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));

            while ((line = reader.readLine()) != null) {
                outputString += line;
            }
            System.out.println(outputString);

        } catch (IOException ex) {
            Logger.getLogger(DistanceMatrixCalculator.class.getName()).log(Level.SEVERE, null, ex);
        }

        List<String> ret = new ArrayList();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db;
        try {
            db = dbf.newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(outputString));

            Document doc;

            doc = db.parse(is);
            NodeList nodes = doc.getElementsByTagName("element");

            for (int i = 0; i < nodes.getLength(); i++) {
               Element element = (Element) nodes.item(i);

               NodeList name = element.getElementsByTagName("text");
               System.out.println("Valor: " + name.item(0).getTextContent());
               ret.add(name.item(0).getTextContent());
            }

        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(DistanceMatrixCalculator.class.getName()).log(Level.SEVERE, null, ex);
        }

        return ret;
    }

    private static String getCharacterDataFromElement(Element e) {
        Node child = e.getFirstChild();
        if (child instanceof CharacterData) {
           CharacterData cd = (CharacterData) child;
           return cd.getData();
        }
        return "?";
      }

    public String buildUrl() {
        StringBuilder ret = new StringBuilder();
        ret.append(baseUrl);
        ret.append("origins=").append(origin);
        ret.append("&destinations=").append(String.join("|", destinations));
        ret.append("&language=").append(lang);
        ret.append("&key=").append(key);

        return ret.toString();
    }

    public String getOriginAddress() {
        String line, outputString = "";

        try {
            URL url = new URL(buildUrl());

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));

            while ((line = reader.readLine()) != null) {
                outputString += line;
            }
            System.out.println(outputString);

        } catch (IOException ex) {
            Logger.getLogger(DistanceMatrixCalculator.class.getName()).log(Level.SEVERE, null, ex);
        }

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db;
        try {
            db = dbf.newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(outputString));

            Document doc;

            doc = db.parse(is);
            String address = doc.getElementsByTagName("origin_address").item(0).getTextContent();

            return address;

        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(DistanceMatrixCalculator.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}