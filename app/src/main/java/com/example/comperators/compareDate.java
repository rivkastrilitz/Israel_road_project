package com.example.comperators;


import java.util.Date;

public class compareDate implements java.util.Comparator<Date>{


    @Override
    public int compare(Date d1, Date d2) {
        return d1.compareTo(d2);
    }
}
