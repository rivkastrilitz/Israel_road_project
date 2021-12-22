package com.example.comperators;

import android.annotation.SuppressLint;

import com.example.model.post;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class sortByFromDate implements java.util.Comparator<post>{
    @Override
    public int compare(post p1, post p2) {
//        @SuppressLint("SimpleDateFormat") DateFormat dateFormat =new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//        String txtFromdate1=p1.getFromDate();
//        String txtFromdate2=p2.getFromDate();
//
//
//
//        Date Fromdate1 = new Date();
//        Date Fromdate2 = new Date();
//
//
//        try{
//            Fromdate1=dateFormat.parse(txtFromdate1);
//            Fromdate2=dateFormat.parse(txtFromdate2);
//
//
//
//        }catch (ParseException e){
//            e.printStackTrace();
//        }
//        assert Fromdate1 != null;
//        return Fromdate1.compareTo(Fromdate2);


        String txtFromDate1=p1.getFromDate();
        String txtFromDate2=p2.getFromDate();
        String [] arr1 =txtFromDate1.split("/");
        String [] arr2 =txtFromDate2.split("/");
        if(arr1[2].compareTo(arr2[2])!=0){
            return arr1[2].compareTo(arr2[2]);
        }else if(arr1[1].compareTo(arr2[1])!=0){
            return arr1[1].compareTo(arr2[1]);
        }else{
            return arr1[0].compareTo(arr2[0]);
        }





    }
}
