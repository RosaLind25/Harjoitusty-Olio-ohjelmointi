package com.example.harjoitustyfinnkino;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;


public class LogIn extends AppCompatActivity implements View.OnClickListener {
    private EditText eEmail;
    private EditText ePassword;

    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.login);
        eEmail = findViewById(R.id.etName);
        ePassword = findViewById(R.id.etPassword);
        Button eLogin = findViewById(R.id.btn);
        TextView tvRegister = findViewById(R.id.textViewRegister);

        tvRegister.setOnClickListener(this);
        eLogin.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.textViewRegister:{
                startActivity(new Intent(this,RegisterUser.class));
                break;
            }
            case R.id.btn:{
                signUserIn();
                break;
            }


        }

    }

    //checking if email and password fields are filled
    private void signUserIn() {
        String email = eEmail.getText().toString().trim();
        String password = ePassword.getText().toString().trim();
        if (email.isEmpty()){
            eEmail.setError("Sähköpostiosoite vaaditaan!");
            eEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            eEmail.setError("Aseta toimiva sähköpostiosoite!");
            eEmail.requestFocus();
            return;
        }
        if (password.isEmpty()){
            ePassword.setError("Salasana vaaditaan!");
            ePassword.requestFocus();
            return;
        }
        if(password.length()<12){
            ePassword.setError("Minimipituus on 12 merkkiä!");
            ePassword.requestFocus();
        }

        //checking if matching account can be found
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                Toast.makeText(LogIn.this, "Sisäänkirjautuminen onnistui!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LogIn.this,MainActivity.class));
            }
            else{
                Toast.makeText(LogIn.this, "Sisäänkirjautuminen epäonnistui. Tarkista tunnuksesi.", Toast.LENGTH_LONG).show();
            }
        });



    }
}
