package com.example.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Html;
import android.view.View;

import android.widget.Button;
import android.widget.TextView;

import com.example.model.traveler;

public class Traveler_homePage extends AppCompatActivity {
    private traveler Traveler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traveler_home_page);
        //dynamically insert traveler name
        Resources res = getResources();
        String text = String.format(res.getString(R.string.welcome),Traveler.getName());
        CharSequence styledText = Html.fromHtml(text);




    }


}