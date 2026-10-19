package edu.course.lab04;

import java.util.Objects;

public class Book {
    private String isbn;
    private String title;

    public Book(String isbn, String title){
        this.isbn = isbn;
        this.title = title;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) return false;

        Book b2 = (Book) obj;
        return Objects.equals(this.isbn, b2.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.isbn);
    }

    public String getIsbn(String value) { return this.isbn; }
    public String getTitle(String value) { return this.title; }

    public void setIsbn(String value) { this.isbn = value; }
    public void setTitle(String value) { this.title = value; }

}
