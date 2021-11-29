package com.example.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class User_Login extends AppCompatActivity {
    private EditText user_name, pass_word;
    private Button btn_login;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        user_name=findViewById(R.id.email);
        pass_word=findViewById(R.id.password);
        btn_login = findViewById(R.id.btn_login);

        mAuth=FirebaseAuth.getInstance();
        btn_login.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                String txtEmail = user_name.getText().toString().trim();
                String txtPassword = pass_word.getText().toString().trim();
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
                mAuth.signInWithEmailAndPassword(txtEmail, txtPassword).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        String type=readTypeFirebase(txtEmail);
                        if(type.equals("Angel")||type.equals("angel")){
                            Intent intent=new Intent(User_Login.this, Angel_homePage.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            Toast.makeText(User_Login.this,"Angel", Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                            finish();
                        }
                        if(type.equals("Traveler")||type.equals("traveler")){
                            Intent intent=new Intent(User_Login.this, Traveler_homePage.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            Toast.makeText(User_Login.this,"Traveler", Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                            finish();
                        }


                    }else {
                        Toast.makeText(User_Login.this,
                                "Please Check Your login Credentials",
                                Toast.LENGTH_SHORT).show();
                    }

                });
            }
        });

    }
    //todo read from fire base according to id
    public String readTypeFirebase(String email){

        return "Traveler";
    }
}