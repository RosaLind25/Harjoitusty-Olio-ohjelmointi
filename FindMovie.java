package com.example.harjoitustyfinnkino;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


public class FindMovie extends AppCompatActivity implements Serializable {

    private Spinner spinner_t;
    private Spinner spinner_m;
    private EditText editTextDate;
    private EditText editTextTime;
    private ListView movie_list;

    private String start;
    private String title;
    private String lengthInMinutes;
    private String theatre;
    private String ID;
    private String jpg;
    public String id;

    //setting lists for information needed (lists created in ReadFiles.java)
    ArrayList<MovieInformation> EventsList = new ArrayList<>();
    ArrayList<TheatreInformation> TheatreList = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.find_movies);

        //executing actions from ReadFiles.java
        ReadFiles files = new ReadFiles();
        EventsList = files.XMLEvent();
        TheatreList = files.XMLTheatres();

        spinner_t = (Spinner) findViewById(R.id.spinner_theatre);
        spinner_m = (Spinner) findViewById(R.id.spinner_movie);
        editTextDate = (EditText) findViewById(R.id.editTextDate);
        editTextTime = (EditText) findViewById(R.id.editTextTime);
        movie_list = (ListView) findViewById(R.id.movie_list);

        //setting current time as default when "Etsi elokuva" is pressed
        LocalTime localTime = LocalTime.now();
        String t = localTime.format(DateTimeFormatter.ofPattern("HH:mm"));
        editTextTime.setText(t);

        //setting current date as default when "Etsi elokuva" is pressed
        LocalDate localDate = LocalDate.now();
        String d = localDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        editTextDate.setText(d);

        //setting spinner for theatres
        spinner_t.setAdapter(new ArrayAdapter<>(FindMovie.this, android.R.layout.simple_spinner_dropdown_item, TheatreList));
        spinner_t.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        //setting spinner for movies
        spinner_m.setAdapter(new ArrayAdapter<>(FindMovie.this, android.R.layout.simple_spinner_dropdown_item, EventsList));
        spinner_m.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        //making selected time editable
        editTextTime.setOnClickListener(view -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(FindMovie.this, (timePicker, i, i1) -> {
                @SuppressLint("DefaultLocale") String minute2 = String.format("%02d", i1);
                String time = i + ":" + minute2;
                editTextTime.setText(time);
            }, hour, minute, true);
            timePickerDialog.show();
        });

        //making selected date editable
        editTextDate.setOnClickListener((view -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(FindMovie.this, (datePicker, i, i1, i2) -> {
                i1 = i1 + 1;
                String date = i2 + "." + i1 + "." + i;
                editTextDate.setText(date);
            }, year, month, day);
            datePickerDialog.show();
        }));

        XMLCurrentTime();

    }
    public ArrayList<ShowInformation> FilteredList = new ArrayList<>();

    public void XMLCurrentTime () {

        Editable date = editTextDate.getText();
        Editable time = editTextTime.getText();

        //create list only with movies that start after the current date and time
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            String url = "https://www.finnkino.fi/xml/Schedule/?dt=" + date;
            System.out.println("###########" + url);
            Document doc = builder.parse(url);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getDocumentElement().getElementsByTagName("Show");
            if (!FilteredList.isEmpty()) {
                FilteredList.clear();
            }
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node nde = nodeList.item(i);
                if (nde.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) nde;
                    start = element.getElementsByTagName("dttmShowStart").item(0).getTextContent();
                    title = element.getElementsByTagName("Title").item(0).getTextContent();
                    lengthInMinutes = element.getElementsByTagName("LengthInMinutes").item(0).getTextContent();
                    theatre = element.getElementsByTagName("Theatre").item(0).getTextContent();
                    ID = element.getElementsByTagName("TheatreID").item(0).getTextContent();

                    //Parses time, removes seconds etc.
                    String time_movie = start.substring(start.length() - 8, start.length() - 3);

                    @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

                    //t11 and time are time chosen by user, t22 and time_movie are the movie start time
                    Date t11 = sdf.parse(String.valueOf(time));
                    Date t22 = sdf.parse(time_movie);

                    //If user's time is before movie time, movie is added to the list
                    if (t11.before(t22)) {
                        NodeList img = element.getElementsByTagName("Images");
                        Node iNode = img.item(0);
                        Element image = (Element) iNode;
                        try{
                            jpg = image.getElementsByTagName("EventSmallImagePortrait").item(0).getTextContent();
                            FilteredList.add(new ShowInformation(time_movie, title, lengthInMinutes, theatre, ID, jpg));
                        }
                        catch (NullPointerException ex){
                            jpg = null;
                            FilteredList.add(new ShowInformation(time_movie, title, lengthInMinutes, theatre, ID, jpg));
                        }
                    }
                }
            }
            //adding the list to ListView, if there are now movies available, the program shows message informing this
            if (FilteredList.isEmpty()){
                Toast.makeText(FindMovie.this, "Ei elokuvia tarjolla nykyisillä valinnoilla", Toast.LENGTH_SHORT).show();
            }
            else {
                adapter_find_movies adapter2 = new adapter_find_movies(this, R.layout.adapter_find_movies, FilteredList);
                movie_list.setAdapter(adapter2);
            }

        } catch (IOException | SAXException | ParserConfigurationException | ParseException e) {
            System.out.println("VIRHE");
            e.printStackTrace();
        } finally {
            System.out.println("#######DONE#######");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void FilterMovies(View v) throws ParseException {
        String selected_theatre = spinner_t.getSelectedItem().toString();
        String selected_movie = spinner_m.getSelectedItem().toString();
        Editable date = editTextDate.getText();
        Editable time = editTextTime.getText();

        //compare selected theatre with all theatres to find matching ID from movie list
        String selected_id = "null";
        for (int i = 0; i < TheatreList.size(); i++) {
            if (selected_theatre.equals(TheatreList.get(i).theatre_name)) {
                selected_id = TheatreList.get(i).id;
            }
        }
        //create new list only with that fit the filters picked by the user
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            String url = "https://www.finnkino.fi/xml/Schedule/?area=" + selected_id + "&dt=" + date;
            System.out.println("###########" + url);
            Document doc = builder.parse(url);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getDocumentElement().getElementsByTagName("Show");
            if (!FilteredList.isEmpty()) {
                FilteredList.clear();
            }
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node nde = nodeList.item(i);
                if (nde.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) nde;
                    start = element.getElementsByTagName("dttmShowStart").item(0).getTextContent();
                    title = element.getElementsByTagName("Title").item(0).getTextContent();
                    lengthInMinutes = element.getElementsByTagName("LengthInMinutes").item(0).getTextContent();
                    theatre = element.getElementsByTagName("Theatre").item(0).getTextContent();
                    ID = element.getElementsByTagName("TheatreID").item(0).getTextContent();

                    //Parses time, removes seconds etc.
                    String time_movie = start.substring(start.length() - 8, start.length() - 3);

                    @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

                    //t11 and time are time chosen by user, t22 and time_movie are the movie start time
                    Date t11 = sdf.parse(String.valueOf(time));
                    Date t22 = sdf.parse(time_movie);

                    //If user's time is before movie time and the movie is the same as selected (or the movie isn't selected) movie is added to the list
                    if (t11.before(t22)) {
                        if ((selected_movie.equals(title)) || (selected_movie.equals("Valitse Elokuva"))) {
                            NodeList img = element.getElementsByTagName("Images");
                            Node iNode = img.item(0);
                            Element image = (Element) iNode;
                            try{
                                jpg = image.getElementsByTagName("EventSmallImagePortrait").item(0).getTextContent();
                                FilteredList.add(new ShowInformation(time_movie, title, lengthInMinutes, theatre, ID, jpg));
                            }
                            catch (NullPointerException ex){
                                jpg = null;
                                FilteredList.add(new ShowInformation(time_movie, title, lengthInMinutes, theatre, ID, jpg));
                            }
                        }
                    }
                }
            }
        } catch (IOException | SAXException | ParserConfigurationException e) {
            System.out.println("VIRHE");
            e.printStackTrace();
        } finally {
            System.out.println("#######DONE#######");
        }

        //adding the list to ListView, if there are now movies available, the program shows message informing this
        if (FilteredList.isEmpty()){
            Toast.makeText(FindMovie.this, "Ei elokuvia tarjolla nykyisillä valinnoilla", Toast.LENGTH_SHORT).show();
        }
        else {
            adapter_find_movies adapter1 = new adapter_find_movies(this, R.layout.adapter_find_movies, FilteredList);
            movie_list.setAdapter(adapter1);
        }

    }

}
