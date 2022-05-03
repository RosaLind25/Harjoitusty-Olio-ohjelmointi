package com.example.harjoitustyfinnkino;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private boolean MoviesExecuted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        File dir = getFilesDir();
        File file = new File(dir, "favourites.txt");
        boolean deleted = file.delete();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.MoviesExecuted = true;
        }

    }

    //"Elokuvat" buttons actions
    public void showMovies(View v){
        Intent intent = new Intent(MainActivity.this,ShowMovies.class);
        if (MoviesExecuted){
            intent.putExtra("state",MoviesExecuted);
        }
        startActivity(intent);
    }

    //"Etsi näytös" buttons actions
    public void findMovies(View v){
        Intent intent = new Intent(MainActivity.this,FindMovie.class);
        startActivity(intent);
    }

    //"Omat suosikit" buttons actions
    public void checkFavourites(View v){
        Intent intent = new Intent(MainActivity.this,CheckFavourites.class);
        startActivity(intent);
    }
}
