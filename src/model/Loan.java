package model;

import java.time.LocalDate;
import java.util.Date;

/**
 * Created by User on 004 04.04.17.
 */
public class Loan {
    private int loan_id;
    private Reader reader;
    private Book book;
    private LocalDate Date;
    private boolean state;


    public int getLoan_id() {
        return loan_id;
    }

    public void setLoan_id(int loan_id) {
        this.loan_id = loan_id;
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public LocalDate getDate() {
        return Date;
    }

    public void setDate(LocalDate date) {
        this.Date = date;
    }

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Loan loan = (Loan) o;

        if (loan_id != loan.loan_id) return false;
        if (state != loan.state) return false;
        if (!reader.equals(loan.reader)) return false;
        if (!book.equals(loan.book)) return false;
        return Date.equals(loan.Date);
    }

    @Override
    public int hashCode() {
        int result = loan_id;
        result = 31 * result + reader.hashCode();
        result = 31 * result + book.hashCode();
        result = 31 * result + Date.hashCode();
        result = 31 * result + (state ? 1 : 0);
        return result;
    }


}
