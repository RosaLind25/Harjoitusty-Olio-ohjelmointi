package com.example.harjoitustyfinnkino;


import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ShowMovies extends AppCompatActivity {
    ListView listView;
    private final String file = "favourites.txt";
    ArrayList<MovieInformation> movies = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.show_movies);
        Context context = ShowMovies.this;
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            ReadFiles files = new ReadFiles();
            movies = files.XMLEvent();
            movies.remove(0);
        }

        listView = findViewById(R.id.listView);
        MovieListAdapter adapter = new MovieListAdapter(this,R.layout.adapter_view2,movies);
        listView.setAdapter(adapter);

        adapter.setOnAddListener((position, text) -> FavouritesListCreate(text));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    public void FavouritesListCreate(String text) {
        String line = text +"\n";
        File path = getApplicationContext().getFilesDir();
        try {
            FileOutputStream fos = new FileOutputStream(new File(path,file), true);

            int x = checkFile(text);
            if (x == 1) {
            } else {
                fos.write(line.getBytes());
                fos.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    private int checkFile(String text) {
        int num = 0;
        try {
            InputStream ins = getApplicationContext().openFileInput(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(ins));
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains(text.trim())) {
                    System.out.println(line + " " + text);
                    num = 1;
                }
            }
            System.out.println("###### " + num);
            ins.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return num;
    }


}
