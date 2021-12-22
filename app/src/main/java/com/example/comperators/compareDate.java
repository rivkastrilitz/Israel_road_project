package com.example.comperators;


import java.util.Date;

public class compareDate implements java.util.Comparator<String>{


    @Override
    public int compare(String date1,String date2) {

        String [] arr1 =date1.split("/");
        String [] arr2 =date2.split("/");
        if(arr1[2].compareTo(arr2[2])!=0){
            return arr1[2].compareTo(arr2[2]);
        }else if(arr1[1].compareTo(arr2[1])!=0){
            return arr1[1].compareTo(arr2[1]);
        }else{
            return arr1[0].compareTo(arr2[0]);
        }
    }
}
