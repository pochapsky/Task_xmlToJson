package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        String fileNameXML = "data.xml";
        String nameForFileWrite = "data.json";
        List<Employee> list = parseXML(fileNameXML);
        String json2 = listToJson(list);
        writeString(json2, nameForFileWrite);
    }

    public static List<Employee> parseXML(String fileNameXML) throws ParserConfigurationException, IOException, SAXException {

        List<Employee> employeeList = new ArrayList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new File(fileNameXML));

        Node root = doc.getDocumentElement();
        NodeList nodeList = root.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node nodeRootChild = nodeList.item(i);

            if (Node.ELEMENT_NODE == nodeRootChild.getNodeType()) {
                NodeList nodeListChild = nodeRootChild.getChildNodes();
                long id = 0;
                String firstName = null;
                String lastName = null;
                String country = null;
                int age = 0;
                for (int j = 0; j < nodeListChild.getLength(); j++) {
                    Node node__ = nodeListChild.item(j);
                    if (Node.ELEMENT_NODE == node__.getNodeType()) {
                        Element element = (Element) node__;
                        Node childNode = element.getFirstChild();
                        if (Node.TEXT_NODE == childNode.getNodeType()) {
                            if (node__.getNodeName() == "id") {
                                id = Integer.valueOf(childNode.getNodeValue());
                            }
                            if (node__.getNodeName() == "firstName") {
                                firstName = childNode.getNodeValue();
                            }
                            if (node__.getNodeName() == "lastName") {
                                lastName = childNode.getNodeValue();
                            }
                            if (node__.getNodeName() == "country") {
                                country = childNode.getNodeValue();
                            }
                            if (node__.getNodeName() == "age") {
                                age = Integer.valueOf(childNode.getNodeValue());
                            }
                        }
                    }
                }
                employeeList.add(new Employee(id, firstName, lastName, country, age));
            }
        }
        return employeeList;
    }

    private static String listToJson(List<Employee> list) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Type listType = new TypeToken<List<Employee>>() {
        }.getType();
        String json = gson.toJson(list, listType);
        return json;
    }

    public static void writeString(String jsonStr, String nameFileWrite) {

        try (FileWriter file = new FileWriter(nameFileWrite)) {
            file.write(jsonStr);
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
