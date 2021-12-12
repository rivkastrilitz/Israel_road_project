package com.example.model;

import java.util.HashMap;

public class post {


    private String description;
    private String imageurl;
    private String postid;
    private String publisher;
    private String address,fromDate,toDate,capacity,restrictions;

    public post() {
    }

    public post(String description, String imageurl, String postid, String publisher) {
        this.description = description;
        this.imageurl = imageurl;
        this.postid = postid;
        this.publisher = publisher;
    }

    public post(String Address,String fromDate,String toDate,String capacity,String restrictions){
        this.address=Address;
        this.fromDate=fromDate;
        this.toDate=toDate;
        this.capacity=capacity;
        this.restrictions=restrictions;

    }
    //copy constructor
    public post(post p){
        this.address=p.getAddress();
        this.fromDate=p.getFromDate();
        this.toDate=p.getToDate();
        this.capacity=p.getCapacity();
        this.restrictions=p.getRestrictions();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getAddress() {
        return address;
    }

    public String getCapacity() {
        return capacity;
    }

    public String getFromDate() {
        return fromDate;
    }

    public String getRestrictions() {
        return restrictions;
    }

    public String getToDate() {
        return toDate;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public void setRestrictions(String restrictions) {
        this.restrictions = restrictions;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

}
