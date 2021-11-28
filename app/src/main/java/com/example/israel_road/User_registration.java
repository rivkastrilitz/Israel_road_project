package com.example.israel_road;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class User_registration extends AppCompatActivity {
    private Button btn2_signup;
    private EditText user_name,name, pass_word ,user_type ;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);
        user_name=findViewById(R.id.registration_username);
        pass_word=findViewById(R.id.registration_password);
        user_type=findViewById(R.id.registration_UserType);
        name=findViewById(R.id.registration_name);
        btn2_signup=findViewById(R.id.registration_signUp);

        mAuth=FirebaseAuth.getInstance();
        databaseRef = FirebaseDatabase.getInstance().getReference();



        btn2_signup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String txtUser_name = user_name.getText().toString().trim();
                String txtPassword= pass_word.getText().toString().trim();
                String txtType=user_type.getText().toString().trim();
                String txtName=name.getText().toString().trim();


                if(txtUser_name.isEmpty())
                {
                    user_name.setError("Email is empty");
                    user_name.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(txtUser_name).matches())
                {
                    user_name.setError("Enter the valid txtEmail address");
                    user_name.requestFocus();
                    return;
                }
                if(txtPassword.isEmpty())
                {
                    pass_word.setError("Enter the txtPassword");
                    pass_word.requestFocus();
                    return;
                }
                if(txtPassword.length()<6)
                {
                    pass_word.setError("Length of the txtPassword should be more than 6");
                    pass_word.requestFocus();
                    return;
                }

                registerUser( txtName , txtUser_name , txtPassword,txtType);



            }


        });
    }

    private void registerUser(final String Name, final String UserName, String Password,final String Type){

        mAuth.createUserWithEmailAndPassword(UserName , Password).addOnSuccessListener(new OnSuccessListener<AuthResult>(){
            @Override
            public void onSuccess(AuthResult authResult) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("name", Name);
                map.put("Email", UserName);
                map.put("type", Type);
                map.put("id", mAuth.getCurrentUser().getUid());

                databaseRef.child(Type).child(mAuth.getCurrentUser().getUid()).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(User_registration.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            Toast.makeText(User_registration.this,"You are successfully Registered", Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                            finish();

                        }
                    }

                });
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(User_registration.this,"You are not Registered! Try again",Toast.LENGTH_SHORT).show();

            }
        });

    }


}
