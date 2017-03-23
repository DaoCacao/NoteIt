package com.example.legion.noteit;

import io.realm.RealmObject;

public class Note extends RealmObject {
    private String title, text;
    private String pass;

    public Note(){}
    public Note(String title, String text) {
        this.title = title;
        this.text = text;
        this.pass = "";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
