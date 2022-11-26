package com.company;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.xml.sax.SAXException;

public class JavaXmlDomRW {

    NodeList nList;
    File xmlFile;

    DocumentBuilderFactory factory;
    DocumentBuilder dBuilder;
    Document doc;

    String filename;

    public JavaXmlDomRW(String filename) throws IOException, SAXException, ParserConfigurationException {
        this.filename = filename;
        xmlFile = new File(filename);

        factory = DocumentBuilderFactory.newInstance();
        dBuilder = factory.newDocumentBuilder();
        doc = dBuilder.parse(xmlFile);
    }

    public ConcurrentMap<String, String> getUsersDatabase() {
        ConcurrentMap<String, String> users = new ConcurrentHashMap<>();

        doc.getDocumentElement().normalize();

        nList = doc.getElementsByTagName("user");
        for (int i = 0; i < nList.getLength(); i++) {
            Node nNode = nList.item(i);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) nNode;
                String login = elem.getElementsByTagName("login").item(0).getTextContent();
                String password = elem.getElementsByTagName("password").item(0).getTextContent();
                users.put(login, password);
            }
        }
        return users;
    }

    public void saveToDatabase(ConcurrentMap<String, String> users) throws TransformerException, ParserConfigurationException, IOException, SAXException {
        Element root = doc.getDocumentElement();

        Map<String, String> treeMap = new TreeMap<>(users);
        Iterator<Map.Entry<String, String>> it = treeMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> me = it.next();
            root.appendChild(createUser(doc, me.getKey(), me.getValue()));
            users.remove(me.getKey());
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transf = transformerFactory.newTransformer();

        transf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transf.setOutputProperty(OutputKeys.INDENT, "yes");
        transf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

        StreamResult file = new StreamResult(xmlFile);
        DOMSource source = new DOMSource(doc);

        transf.transform(source, file);
    }

    private Node createUser(Document doc, String login,
                            String password) {

        Element user = doc.createElement("user");

        user.appendChild(createUserElement(doc, "login", login));
        user.appendChild(createUserElement(doc, "password", password));

        return user;
    }

    private Node createUserElement(Document doc, String name,
                                   String value) {

        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));

        return node;
    }
}
