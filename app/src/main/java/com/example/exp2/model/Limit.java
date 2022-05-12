package com.example.exp2.model;

public class Limit {

    public String type,limitdata;

    public Limit(String type, String limitdata) {
        this.type = type;
        this.limitdata = limitdata;
    }

    public String getType() {
        return type;
    }

    public String getLimitdata() {
        return limitdata;
    }
}
