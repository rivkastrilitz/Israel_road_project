package com.example.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;

public class restrictionsPopUpActivity extends AppCompatActivity {

    String restrictions;
    TextView txtRestrictions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restrictions_pop_up);
        DisplayMetrics dm= new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width= dm.widthPixels;
        int height =dm.heightPixels;

        getWindow().setLayout((int)(width*.6),(int)(height*.6));

        txtRestrictions=findViewById(R.id.PostRestrictions);
        getRestrictions();
        txtRestrictions.setText(restrictions);

    }

    public void getRestrictions(){
        Intent intent=getIntent();
        Bundle restrictionsFromPostFeed = intent.getExtras();
        if(restrictionsFromPostFeed != null)
        {
            restrictions= restrictionsFromPostFeed.getString("restrictions");
        }
    }
}