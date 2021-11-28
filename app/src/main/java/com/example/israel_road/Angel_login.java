package com.example.israel_road;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;

public class Angel_login extends AppCompatActivity {
    private EditText user_name, pass_word;
    private  Button btn_login;
    FirebaseAuth mAuth;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_angel_login);


            user_name = findViewById(R.id.Angel_email);
            pass_word = findViewById(R.id.Angel_password);
            btn_login = findViewById(R.id.Angel_btn_login);

            mAuth = FirebaseAuth.getInstance();

            btn_login.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v){


                    String email = user_name.getText().toString().trim();
                    String password = pass_word.getText().toString().trim();

                    if (email.isEmpty()) {
                        user_name.setError("Email is empty");
                        user_name.requestFocus();
                        return;
                    }
                    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        user_name.setError("Enter the valid email");
                        user_name.requestFocus();
                        return;
                    }
                    if (password.isEmpty()) {
                        pass_word.setError("Password is empty");
                        pass_word.requestFocus();
                        return;
                    }
                    if (password.length() < 6) {
                        pass_word.setError("Length of password is more than 6");
                        pass_word.requestFocus();
                        return;
                    }

                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(Angel_login.this, MainActivity.class));
                        } else {
                            Toast.makeText(Angel_login.this,
                                    "Please Check Your login Credentials",
                                    Toast.LENGTH_SHORT).show();
                        }

                    });

                }
            });


    }

}

