package com.github.tckz916.breezecore.manager;

import com.github.tckz916.breezecore.BreezeCore;
import com.github.tckz916.breezecore.BreezeCoreManager;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;

/**
 * Created by tckz916 on 2015/10/19.
 */
public class XmlManager {

    private BreezeCore plugin = BreezeCore.getInstance();
    private BreezeCoreManager breezeCoreManager = BreezeCoreManager.getInstance();

    private String xml;
    private Document document;


    public void getXml(String url) {
        URL data;
        URLConnection conn;
        StringBuilder builder;
        try {
            data = new URL(url);
            conn = data.openConnection();
            conn.setRequestProperty("User-agent", "Mozilla/5.0");
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            builder = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            plugin.getLogger().log(Level.WARNING, format(false, "error.connect"));
            return;
        }
        this.xml = builder.toString();
        readXml();
    }

    private void readXml() {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            this.document = documentBuilder.parse(new InputSource(new StringReader(xml)));
        } catch (SAXException | IOException | ParserConfigurationException e) {
            e.printStackTrace();
            plugin.getLogger().log(Level.WARNING, format(false, "error.connect"));
        }
    }

    public String getAttribute(String nodename, String attribute) {
        Element root = document.getDocumentElement();
        NodeList rootlist = root.getChildNodes();
        for (int i = 0; i < rootlist.getLength(); i++) {
            Node node = rootlist.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                if (element.getNodeName().equals(nodename)) {
                    return element.getAttribute(attribute);
                }
            }
        }
        return null;
    }

    public NodeList getNodeList(String nodename) {
        Element root = document.getDocumentElement();
        NodeList rootlist = root.getChildNodes();
        for (int i = 0; i < rootlist.getLength(); i++) {
            Node node = rootlist.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                if (element.getNodeName().equals(nodename)) {
                    return node.getChildNodes();
                }
            }
        }
        return null;
    }

    private String format(boolean prefix, String key, Object... args) {
        return breezeCoreManager.getMessageFormat().format(prefix, key, args);
    }

}
