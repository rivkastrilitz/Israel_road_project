package com.example.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.model.user;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class User_Login extends AppCompatActivity {
    private EditText user_name, pass_word;
    private Button btn_login;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseRef;
    private String txtEmail, txtPassword ,uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        user_name = findViewById(R.id.email);
        pass_word = findViewById(R.id.password);
        btn_login = findViewById(R.id.btn_login);

        mAuth = FirebaseAuth.getInstance();
        databaseRef = FirebaseDatabase.getInstance().getReference();
        btn_login.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                txtEmail = user_name.getText().toString().trim();
                txtPassword = pass_word.getText().toString().trim();
                if (txtEmail.isEmpty()) {
                    user_name.setError("Email is empty");
                    user_name.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(txtEmail).matches()) {
                    user_name.setError("Enter the valid email");
                    user_name.requestFocus();
                    return;
                }
                if (txtPassword.isEmpty()) {
                    pass_word.setError("Password is empty");
                    pass_word.requestFocus();
                    return;
                }
                if (txtPassword.length() < 6) {
                    pass_word.setError("Length of password is more than 6");
                    pass_word.requestFocus();
                    return;
                }

                mAuth.signInWithEmailAndPassword(txtEmail, txtPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            uid = mAuth.getCurrentUser().getUid();
                            databaseRef.child("Users").child(uid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                public void onComplete(@NonNull Task<DataSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        user currUser = task.getResult().getValue(user.class);
                                        assert currUser != null;

                                        Intent intent = new Intent(User_Login.this, HomePageActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        Toast.makeText(User_Login.this, "Angel", Toast.LENGTH_SHORT).show();
                                        //sent currUser id and name to next activity
                                        intent.putExtra("Uid", uid);
                                        intent.putExtra("name",currUser.getName());
                                        startActivity(intent);
                                        finish();

                                    }
                                }
                            });

                        } else {
                            Toast.makeText(User_Login.this,
                                    "Please Check Your login Credentials",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                });


            }
        });


    }
}