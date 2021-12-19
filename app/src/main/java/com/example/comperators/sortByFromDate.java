package com.example.comperators;

import com.example.model.post;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class sortByFromDate implements java.util.Comparator<post>{
    @Override
    public int compare(post p1, post p2) {
        DateFormat dateFormat =new SimpleDateFormat("dd/mm/yyyy ");
        String txtFromdate1=p1.getFromDate();
        String txtFromdate2=p2.getFromDate();


        Date Fromdate1=null;
        Date Fromdate2=null;


        try{
            Fromdate1=dateFormat.parse(txtFromdate1);
            Fromdate2=dateFormat.parse(txtFromdate2);


        }catch (ParseException e){
            e.printStackTrace();
        }

        return  Fromdate1.compareTo(Fromdate2);
    }
}
