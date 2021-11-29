package com.example.activities;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Html;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import com.example.model.angel;


public class Angel_homePage  extends AppCompatActivity {

   private angel Angel;
    private Button login_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_angel_homepage);
        Resources res = getResources();
        String text = String.format(res.getString(R.string.welcome),Angel.getName());
        CharSequence styledText = Html.fromHtml(text);








    }


}
