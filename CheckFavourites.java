package com.example.harjoitustyfinnkino;

import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CheckFavourites extends AppCompatActivity {
    ArrayList<FavouritesInformation> favourites = new ArrayList<>();
    ListView listView;
    Context context = null;
    String file = "favourites.txt";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.check_favourites);
        context = CheckFavourites.this;
        getFavourites();
        listView = findViewById(R.id.listView3);
        FavouritesListAdapter adapter = new FavouritesListAdapter(this, R.layout.favourites_list_adapter, favourites);
        listView.setAdapter(adapter);
    }
    //the name of the movie is being saved to memory of the phone
    public void getFavourites(){
        try{
            InputStream ins = context.openFileInput(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(ins));
            String line;
            while((line = br.readLine()) != null){
                favourites.add(new FavouritesInformation(line));
            }
            ins.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
