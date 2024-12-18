package com.example.librarymanagementsystem.models;

public class Book {
    private int id;
    private String title;
    private String author;
    private String availability;

    public Book(int id, String title, String author, String availability) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.availability = availability;
    }

    public int getId(){
        return id;
    }

    public boolean isAvailable(){
        return availability.equals("Available");
    }
    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getAvailability() {
        return availability;
    }
}
