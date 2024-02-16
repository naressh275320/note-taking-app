package com.example.notesapp;

public class notes {

    String text, title;

    public notes(String text, String title) {
        this.text = text;
        this.title = title;
    }

    public String getText() {
        return text;
    }
    public String getTitle() {
        return title;
    }

    public void setText(String text) {
        this.text = text;
    }
    public void setTitle(String title) {
        this.title = title;
    }
}
