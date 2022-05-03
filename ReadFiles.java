package com.example.harjoitustyfinnkino;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class ReadFiles extends AppCompatActivity{
    private String rating = "null";
    private String name = "Valitse Elokuva";
    private String SSynopsis = "null";
    private String jpg = "null";
    ArrayList<MovieInformation> movies = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_movies);
    }

    //Arraylist for Events xml where all the movies can be found
    public ArrayList<MovieInformation> XMLEvent() {
        try {
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();

            String moviesUrl = "https://www.finnkino.fi/xml/Events/";
            Document doc = db.parse(moviesUrl);
            doc.getDocumentElement().normalize();
            System.out.println("Root element: " + doc.getDocumentElement().getNodeName());

            movies.add(new MovieInformation(name, rating, SSynopsis, jpg));

            NodeList nList = doc.getDocumentElement().getElementsByTagName("Event");
            for (int i = 0; i < nList.getLength(); i++) {
                Node node = nList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    rating = element.getElementsByTagName("Rating").item(0).getTextContent();
                    name = element.getElementsByTagName("Title").item(0).getTextContent();
                    SSynopsis = element.getElementsByTagName("ShortSynopsis").item(0).getTextContent();



                    NodeList img = element.getElementsByTagName("Images");
                    Node cNode = img.item(0);
                    Element image = (Element) cNode;
                    try {
                        jpg = image.getElementsByTagName("EventSmallImagePortrait").item(0).getTextContent();
                        movies.add(new MovieInformation(name, rating, SSynopsis, jpg));
                    }catch (NullPointerException e){
                        try{
                            jpg = image.getElementsByTagName("EventSmallImageLandscape").item(0).getTextContent();
                            movies.add(new MovieInformation(name, rating, SSynopsis, jpg));
                        }catch (NullPointerException ex) {
                            jpg = null;
                            movies.add(new MovieInformation(name, rating, SSynopsis, jpg));
                        }
                    }
                }
            }
        } catch (
                IOException | ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        } finally {


            System.out.println("####DONE####");
        }
        return movies;
    }


    public String id = "null";
    public String theatre_name = "Valitse Teatteri";
    ArrayList<TheatreInformation> theatres = new ArrayList<>();

    //Arraylist for Theatres xml where all the theatres can be found
    public ArrayList<TheatreInformation> XMLTheatres () {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            String theatreUrl = "https://www.finnkino.fi/xml/TheatreAreas/";
            Document doc = builder.parse(theatreUrl);
            doc.getDocumentElement().normalize();
            System.out.println("Root element: " + doc.getDocumentElement().getNodeName());

            NodeList Nlist = doc.getDocumentElement().getElementsByTagName("TheatreArea");

            theatres.add(new TheatreInformation(id, theatre_name));

            for (int i = 0; i < Nlist.getLength(); i++) {
                Node N = Nlist.item(i);
                if (N.getNodeType() == Node.ELEMENT_NODE) {
                    Element element1 = (Element) N;
                    id = element1.getElementsByTagName("ID").item(0).getTextContent();
                    theatre_name = element1.getElementsByTagName("Name").item(0).getTextContent();
                    if(theatre_name.contains(":")) {
                        theatres.add(new TheatreInformation(id, theatre_name));
                    }
                }
            }
        } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }
        return theatres;
    }
}
