package org.vervebridge.proj.libmanagementsystem;

import java.util.Objects;

public final class AvailableBooks {
    private final String title;
    private final String author;
    private final String genre;
    private final String image;
    private final String date;

    public AvailableBooks(String title, String author, String genre, String image, String date) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.image = image;
        this.date = date;
    }


    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public String getDate() {
        return date;
    }
}
