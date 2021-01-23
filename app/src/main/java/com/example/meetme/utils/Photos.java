package com.example.meetme.utils;

public class Photos {

    private String[] urls;

    public  Photos(){}
    public  Photos(String[] urls){
        this.urls = urls;
    }

    public String[] getUrls() {
        return urls;
    }

    public void setUrls(String[] urls) {
        this.urls = urls;
    }
}
