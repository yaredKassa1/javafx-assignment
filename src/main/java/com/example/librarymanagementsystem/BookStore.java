package com.example.librarymanagementsystem;

import java.sql.Blob;

public class BookStore {
    private int BookID;
    private String Title;
    private String Author;
    private int Edition;
    private String Publisher;
    private String PublicationDate;

    public BookStore(int bookID, String Title, String Author, int Edition, String Publisher, String PublicationDate, Blob file) {
        this.BookID = bookID;
        this.Title=Title;
        this.Author=Author;
        this.Edition=Edition;
        this.Publisher=Publisher;
        this.PublicationDate=PublicationDate;
    }

    public int getBookID() {

        return BookID;
    }

    public void setBookID(int bookID) {
       BookID = bookID;
   }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getAuthor() {
        return Author;
    }
    public void setAuthor(String author) {
        Author = author;
    }

    public int getEdition() {
        return Edition;
    }

    public void setEdition(int edition) {
        Edition = edition;
    }

    public String getPublisher() {
        return Publisher;
    }

    public void setPublisher(String publisher) {
        Publisher = publisher;
    }

    public String getPublicationDate() {
        return PublicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        PublicationDate = publicationDate;
    }
}
