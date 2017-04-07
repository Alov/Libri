package model;

import java.util.Arrays;
import java.util.List;

/**
 * Created by User on 001 01.04.17.
 */
public class Book {
    private int bookID;
    private String name;
    private List<String> authors;
    private int year;
    private boolean aviable;

    @Override
    public String toString() {
        return authors +" " + name + "; " + year;
    }

    public boolean getAviable() {
        return aviable;
    }

    public void setAviable(boolean aviable) {
        this.aviable = aviable;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public String getAuthors(){
        String result = "";
        for (String author : authors){
            result += author+", ";
        }
        return result.substring(0, result.length() - 2);
    }

    public void setAuthors(String authors){
        this.authors  = Arrays.asList(authors.split(", "));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (year != book.year) return false;
        if (!name.equals(book.name)) return false;
        return authors.equals(book.authors);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + authors.hashCode();
        result = 31 * result + year;
        return result;
    }
}
