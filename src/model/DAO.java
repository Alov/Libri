package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

/**
 * Created by User on 001 01.04.17.
 */
public class DAO {

    private Connection connect;

    public boolean setup(String login, String password) throws ClassNotFoundException, SQLException{
        boolean status;
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your PostgreSQL JDBC Driver? "
                    + "Include in your library path!");
            e.printStackTrace();
        }
        try {
            connect = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/Library", login, password);
            status = true;
        } catch (SQLException e){
            System.out.println("Incorrect login or password");
            e.printStackTrace();
            status = false;
        }
        return status;
    }

    public void close(){
        try {
            connect.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed close connection;");
        }
    }

    public int addBook(Book book){
        int id = -1;
        try {
            PreparedStatement ps = connect.prepareStatement(
                    "INSERT INTO books (name, authors, year, aviable) VALUES (?, ?, ?, ?)");
            ps.setString(1, book.getName());
            ps.setString(2, book.getAuthors());
            ps.setInt(3, book.getYear());
            ps.setBoolean(4, book.getAviable());
            ps.execute();
            return id;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int addReader(Reader reader){
        int id = -1;
        try {
            PreparedStatement ps = connect.prepareStatement(
                    "INSERT INTO readers (first_name, second_name, patronymic, loan_books, return_books) VALUES (?, ?, ?, ?, ?)");
            ps.setString(1, reader.getFirstName());
            ps.setString(2, reader.getSecondName());
            ps.setString(3, reader.getPatronymic());
            ps.setInt(4, reader.getLoanCount());
            ps.setInt(5, reader.getReturnCount());
            ps.execute();
            return id;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void addLoan(Loan loan){
        try {
            PreparedStatement ps = connect.prepareStatement(
                    "INSERT INTO loan (book_id, reader_id, date, returned) VALUES (?, ?, ?, ?)");
            ps.setInt(1, loan.getBook().getBookID());
            ps.setInt(2, loan.getReader().getReaderID());
            ps.setDate(3, Date.valueOf(loan.getDate()));
            ps.setBoolean(4, loan.getState());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean updateBook(Book book){
        boolean result = false;
        try {
            PreparedStatement ps = connect.prepareStatement(
                    "UPDATE books SET " +
                            "name = ?, " +
                            "authors = ?, " +
                            "year = ?, " +
                            "aviable = ? " +
                         "WHERE book_id = "+book.getBookID());
            ps.setString(1, book.getName());
            ps.setString(2, book.getAuthors());
            ps.setInt(3, book.getYear());
            ps.setBoolean(4, book.getAviable());
            ps.execute();
            result = true;
        } catch (SQLException e) {
            result = false;
            e.printStackTrace();
        }
        return result;
    }

    public boolean updateReader (Reader reader){
        boolean result;
        try {
            PreparedStatement ps = connect.prepareStatement(
                    "UPDATE readers SET " +
                            "first_name = ?, " +
                            "second_name = ?, " +
                            "patronymic = ?, " +
                            "loan_books = ?, " +
                            "return_books = ? " +
                            "WHERE reader_id = " + reader.getReaderID());
            ps.setString(1, reader.getFirstName());
            ps.setString(2, reader.getSecondName());
            ps.setString(3, reader.getPatronymic());
            ps.setInt(4, reader.getLoanCount());
            ps.setInt(5, reader.getReturnCount());

            ps.execute();
            result = true;
        } catch (SQLException e) {
            result = false;
            e.printStackTrace();
        }
        return result;
    }


    public boolean deleteBook(Book book){
        boolean result;
        try{
            PreparedStatement ps = connect.prepareStatement(
                    "DELETE FROM books WHERE book_id = ?");
            ps.setInt(1, book.getBookID());
            ps.execute();
            result = true;
        } catch (SQLException e){
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    public boolean deleteReader(Reader reader){
        boolean result;
        try{
            PreparedStatement ps = connect.prepareStatement(
                    "DELETE FROM readers WHERE reader_id = ?");
            ps.setInt(1, reader.getReaderID());
            ps.execute();
            result = true;
        } catch (SQLException e){
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    public ObservableList<Book> getAllBooks(){
        ObservableList<Book> booksList = FXCollections.observableArrayList();
        try{
            PreparedStatement ps = connect.prepareStatement(
                    "SELECT * FROM books");
            ResultSet result = ps.executeQuery();
            while (result.next()){
                Book book = new Book();
                book.setBookID(result.getInt("book_id"));
                book.setName(result.getString("name"));
                book.setAuthors(result.getString("authors"));
                book.setYear(result.getInt("year"));
                book.setAviable(result.getBoolean("aviable"));
                booksList.add(book);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return booksList;
    }

    public Book getLastBook(){
        Book book = null;
        try{
            PreparedStatement ps = connect.prepareStatement(
                    "SELECT * FROM books ORDER BY book_id DESC LIMIT 1");
            ResultSet result = ps.executeQuery();
            while (result.next()){
                book = new Book();
                book.setBookID(result.getInt("book_id"));
                book.setName(result.getString("name"));
                book.setAuthors(result.getString("authors"));
                book.setYear(result.getInt("year"));
                book.setAviable(result.getBoolean("aviable"));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return book;
    }

    public Book getBookById (int id){
        Book book = null;
        try {
            PreparedStatement ps = connect.prepareStatement("SELECT * FROM books WHERE book_id = ?");
            ps.setInt(1, id);
            ResultSet result = ps.executeQuery();
            while (result.next()){
                book = new Book();
                book.setBookID(result.getInt("book_id"));
                book.setName(result.getString("name"));
                book.setAuthors(result.getString("authors"));
                book.setYear(result.getInt("year"));
                book.setAviable(result.getBoolean("aviable"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return book;
    }

    public ObservableList<Reader> getAllReader(){
        ObservableList<Reader> readersList = FXCollections.observableArrayList();
        try{
            PreparedStatement ps = connect.prepareStatement(
                    "SELECT * FROM readers");
            ResultSet result = ps.executeQuery();
            while (result.next()){
                Reader reader = new Reader();
                reader.setReaderID(result.getInt("reader_id"));
                reader.setFirstName(result.getString("first_name"));
                reader.setSecondName(result.getString("second_name"));
                reader.setPatronymic(result.getString("patronymic"));
                reader.setReturnCount(result.getInt("return_books"));
                reader.setLoanCount(result.getInt("loan_books"));
                readersList.add(reader);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return readersList;
    }

    public ObservableList<Loan> getAllLoans() {
        ObservableList<Loan> loansList = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = connect.prepareStatement("SELECT * FROM loan");
            ResultSet result = ps.executeQuery();
            while (result.next()){
                Loan loan = new Loan();
                loan.setLoan_id(result.getInt("loan_id"));
                Book book = getBookById(result.getInt("book_id"));
                loan.setBook(book);
                loan.setReader(getReaderById(result.getInt("reader_id")));
                loan.setDate(result.getDate("date").toLocalDate());
                loan.setState(result.getBoolean("returned"));
                loansList.add(loan);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loansList;
    }



    public Reader getLastReader(){
        Reader reader = null;
        try{
            PreparedStatement ps = connect.prepareStatement(
                    "SELECT * FROM readers ORDER BY reader_id DESC LIMIT 1");
            ResultSet result = ps.executeQuery();
            while (result.next()){
                reader = new Reader();
                reader.setReaderID(result.getInt("reader_id"));
                reader.setFirstName(result.getString("first_name"));
                reader.setSecondName(result.getString("second_name"));
                reader.setPatronymic(result.getString("patronymic"));
                reader.setReturnCount(result.getInt("return_books"));
                reader.setLoanCount(result.getInt("loan_books"));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return reader;
    }

    public Reader getReaderById (int id){
        Reader reader = null;
        try {
            PreparedStatement ps = connect.prepareStatement("SELECT * FROM readers WHERE reader_id = ?");
            ps.setInt(1, id);
            ResultSet result = ps.executeQuery();
            while (result.next()){
                reader = new Reader();
                reader.setReaderID(result.getInt("reader_id"));
                reader.setFirstName(result.getString("first_name"));
                reader.setSecondName(result.getString("second_name"));
                reader.setPatronymic(result.getString("patronymic"));
                reader.setReturnCount(result.getInt("return_books"));
                reader.setLoanCount(result.getInt("loan_books"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reader;
    }


    public Loan getLastLoan() {
        Loan loan = null;
        try {
            PreparedStatement ps = connect.prepareStatement(
                    "SELECT * FROM loan ORDER BY loan_id DESC LIMIT 1");
            ResultSet result = ps.executeQuery();
            while (result.next()){
                loan = new Loan();
                loan.setLoan_id(result.getInt("loan_id"));
                loan.setBook(getBookById(result.getInt("book_id")));
                loan.setReader(getReaderById(result.getInt("reader_id")));
                loan.setDate(result.getDate("date").toLocalDate());
                loan.setState(result.getBoolean("returned"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loan;
    }

    public boolean updateLoan(Loan loan) {
        boolean result;
        try {
            PreparedStatement ps = connect.prepareStatement(
                    "UPDATE loan SET " +
                            "book_id = ?, " +
                            "reader_id = ?, " +
                            "date = ?, " +
                            "returned = ? " +
                            "WHERE loan_id = " + loan.getLoan_id());
            ps.setInt(1, loan.getBook().getBookID());
            ps.setInt(2, loan.getReader().getReaderID());
            ps.setDate(3, Date.valueOf(loan.getDate()));
            ps.setBoolean(4, loan.getState());
            ps.execute();
            result = true;
        } catch (SQLException e) {
            result = false;
            e.printStackTrace();
        }
        return result;
    }

    public boolean deleteLoan(Loan loan){
        boolean result;
        try {
            PreparedStatement ps = connect.prepareStatement(
                    "DELETE FROM loan WHERE loan_id = " + loan.getLoan_id());
            ps.execute();
            result = true;
        } catch (SQLException e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }
}
