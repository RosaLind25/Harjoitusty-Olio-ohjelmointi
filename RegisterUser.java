package com.example.harjoitustyfinnkino;


import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUser extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText userName, password, userAge, userEMail;
    private Button register;
    private String specialCharactersString = "!@#$%&*()'+,-./:;<=>?[]^_`{|}";




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        setContentView(R.layout.register_user);

        mAuth = FirebaseAuth.getInstance();

        userName = (EditText) findViewById(R.id.etUserName);
        password = (EditText) findViewById(R.id.etUserPassword);
        userAge = (EditText) findViewById(R.id.etAge);
        userEMail = (EditText) findViewById(R.id.etEmail);

        register = (Button) findViewById(R.id.registerButton);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                registerUser();
            }
        });
    }

    //check to see if the password and email meets the requirements and name and age are given
    private void registerUser() {
        String name = userName.getText().toString().trim();
        String pswrd = password.getText().toString().trim();
        String age = userAge.getText().toString().trim();
        String eMail = userEMail.getText().toString().trim();

        int trueCheck = 0;
        char[] ch = pswrd.toCharArray();
        if (pswrd.length() < 12) {
            password.setError("Salasanan täytyy sisältää vähintään 12 merkkiä!");
            password.requestFocus();
        }
        for (char h : ch) {
            if (Character.isDigit(h)) {
                trueCheck ++;
                break;
            }
        }
        for (char c : ch) {
            if (Character.isUpperCase(c)) {
                trueCheck ++;
                break;
            }
        }
        for (char a : ch) {
            if (Character.isLowerCase(a)) {
                trueCheck++;
                break;
            }
        }
        for (char r : ch) {
            if (specialCharactersString.contains(Character.toString(r))) {
                trueCheck++;
                break;
            }
        }

        if (name.isEmpty()){
            userName.setError("Nimi vaaditaan!");
            userName.requestFocus();

        }
        else if (pswrd.isEmpty()){
            password.setError("Salasana vaaditaan!");
            password.requestFocus();
        }
        else if (age.isEmpty()){
            userAge.setError("Ikä vaaditaan!");
            userAge.requestFocus();
        }
        else if (eMail.isEmpty()){
            userEMail.setError("Sähköpostiosoite vaaditaan!");
            userEMail.requestFocus();
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(eMail).matches()){
            userEMail.setError("Anna toimiva sähköpostiosoite!");
            userEMail.requestFocus();
        }

        if (trueCheck < 4) {
            password.setError("Salasanan täytyy sisältää vähintään 12 merkkiä, numero, iso ja pienikirjain sekä erikoismerkki!");
            password.requestFocus();
        }
        else {
            mAuth.createUserWithEmailAndPassword(eMail, pswrd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        Toast.makeText(RegisterUser.this, "Rekisteröinti onnistui!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(RegisterUser.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(RegisterUser.this, "Sisäänkirjautuminen epäonnistui. Yritä uudelleen.", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }


    }
}
