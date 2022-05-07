package com.example.exp2.model;

public class Data {
     int amount;
     String type,typeday,typemonth,typeweek;
     String note;
     String id;
     String date;
    int week,month;

    public Data(){ }
    public Data(int amount, String type, String typeday, String typemonth, String typeweek, String note, String id, String date, int week, int month) {
        this.amount = amount;
        this.type = type;
        this.typeday = typeday;
        this.typemonth = typemonth;
        this.typeweek = typeweek;
        this.note = note;
        this.id = id;
        this.date = date;
        this.week = week;
        this.month = month;

    }


    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeday() {
        return typeday;
    }

    public void setTypeday(String typeday) {
        this.typeday = typeday;
    }

    public String getTypemonth() {
        return typemonth;
    }

    public void setTypemonth(String typemonth) {
        this.typemonth = typemonth;
    }

    public String getTypeweek() {
        return typeweek;
    }

    public void setTypeweek(String typeweek) {
        this.typeweek = typeweek;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }
}
