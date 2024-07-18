package org.vervebridge.proj.libmanagementsystem;

public class returnBook {
    private final String title;
    private final String author;
    private final String genre;
    private final String date;
    private final String image;

    public returnBook(
            String title,
            String author,
            String genre,
            String image,
            String date
    ){

        this.title = title;
        this.author = author;
        this.genre = genre;
        this.date = date;
        this.image = image;
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

    public String getImage() {
        return image;
    }
}
