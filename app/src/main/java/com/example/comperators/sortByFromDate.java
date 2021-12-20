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
        String txtTodate1=p1.getToDate();
        String txtTodate2=p2.getToDate();


        Date Fromdate1=null;
        Date Fromdate2=null;
        Date Todate1=null;
        Date Todate2=null;

        try{
            Fromdate1=dateFormat.parse(txtFromdate1);
            Fromdate2=dateFormat.parse(txtFromdate2);
            Todate1=dateFormat.parse(txtTodate1);
            Todate2=dateFormat.parse(txtTodate2);


        }catch (ParseException e){
            e.printStackTrace();
        }


        assert Fromdate1 != null;
        return Fromdate1.compareTo(Fromdate2);
    }
}
