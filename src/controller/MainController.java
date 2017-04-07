package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import model.Book;
import model.DAO;
import model.Loan;
import model.Reader;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * Created by User on 001 01.04.17.
 */
public class MainController implements Initializable{

    /***********Common************/
    ObservableList<Book> books;
    ObservableList<Reader> readers;
    ObservableList<Loan> loans;


    DAO db;
    int indexBook;
    int indexReader;
    int indexLoan;
    //TableView<Book> bookTable;

    /********Book tab*********/
    @FXML TableView<Book> bookTable;
    @FXML TableColumn<Book, Integer> bookID;
    @FXML TableColumn<Book, String> bookName;
    @FXML TableColumn<Book, String> bookAuthors;
    @FXML TableColumn<Book, Integer> bookYear;
    @FXML TableColumn<Book, Boolean> bookAviable;

    @FXML TextField searchBook, nameBookEdit, authorBookEdit, yearBookEdit;
    @FXML Button addBook, saveBook, deleteBook;
    @FXML CheckBox aviableBookBox;
    @FXML Label idLabel;

    /********Reader tab*********/
    @FXML TableView<Reader> readerTable;
    @FXML TableColumn<Reader, Integer> readerID, readerLoanBook, readerReturnBook;
    @FXML TableColumn<Reader, String> readerSecondName, readerFirstName, readerPatronymic;
    @FXML TextField searchReader, secondNameEdit, firstNameEdit, patronymicEdit;
    @FXML Button addReader, saveReader, deleteReader;


    /********Loan tab***********/
    @FXML TableView<Loan> loanTable;
    @FXML TableColumn<Loan, Integer> loanID;
    @FXML TableColumn<Loan, String> loanReader;
    @FXML TableColumn<Loan, String> loanBook;
    @FXML TableColumn<Loan, String> loanDate;
    @FXML TableColumn<Loan, Boolean> loanReturn;
    @FXML ChoiceBox<Book> loanBookChoice;
    @FXML ChoiceBox<Reader> loanReaderChoice;
    @FXML DatePicker loanDatePicker;
    @FXML CheckBox loanReturnBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Database connection
        db = new DAO();
        try {
            db.setup("user_admin", "12345");

        } catch (ClassNotFoundException e) {e.printStackTrace();
        } catch (SQLException e) { e.printStackTrace();}

        bookID.setCellValueFactory(new PropertyValueFactory<>("bookID"));
        bookName.setCellValueFactory(new PropertyValueFactory<>("name"));
        bookAuthors.setCellValueFactory(new PropertyValueFactory<>("authors"));
        bookYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        bookAviable.setCellValueFactory(new PropertyValueFactory<>("aviable"));

        readerID.setCellValueFactory(new PropertyValueFactory<>("readerID"));
        readerFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        readerSecondName.setCellValueFactory(new PropertyValueFactory<>("secondName"));
        readerPatronymic.setCellValueFactory(new PropertyValueFactory<>("patronymic"));
        readerLoanBook.setCellValueFactory(new PropertyValueFactory<>("loanCount"));
        readerReturnBook.setCellValueFactory(new PropertyValueFactory<>("returnCount"));

        loanID.setCellValueFactory(new PropertyValueFactory<>("loan_id"));
        loanReader.setCellValueFactory(new PropertyValueFactory<>("reader"));
        loanBook.setCellValueFactory(new PropertyValueFactory<>("book"));
        loanDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
        loanReturn.setCellValueFactory(new PropertyValueFactory<>("state"));

        readers = db.getAllReader();
        readerTable.setItems(readers);

        books = db.getAllBooks();
        bookTable.setItems(books);

        loans = db.getAllLoans();
        loanTable.setItems(loans);

        loanReaderChoice.setItems(readers);
        loanBookChoice.setItems(books);


    }

    public void getSelectedBook(MouseEvent mouseEvent) {
        indexBook = bookTable.getSelectionModel().getSelectedIndex();
        idLabel.setText("Row: "+ indexBook +" — ID: "+books.get(indexBook).getBookID());
        nameBookEdit.setText (books.get(indexBook).getName());
        authorBookEdit.setText (books.get(indexBook).getAuthors());
        yearBookEdit.setText (String.valueOf(books.get(indexBook).getYear()));
        aviableBookBox.setSelected (books.get(indexBook).getAviable());
    }

    public void addBookClick(ActionEvent event) {

        //Creating book
        Book book = new Book();
        book.setYear(Integer.parseInt(yearBookEdit.getText()));
        book.setAuthors(authorBookEdit.getText());
        book.setAviable(aviableBookBox.isSelected());
        book.setName(nameBookEdit.getText());

        db.addBook(book);
        book = db.getLastBook();

        //adding to table
        bookTable.getItems().add(book);

        //clearing input field
        nameBookEdit.clear();
        authorBookEdit.clear();
        yearBookEdit.clear();
        aviableBookBox.setSelected(false);
    }

    public void saveBookClick(ActionEvent event) {
        Book book = books.get(indexBook);
        book.setYear(Integer.parseInt(yearBookEdit.getText()));
        book.setAuthors(authorBookEdit.getText());
        book.setAviable(aviableBookBox.isSelected());
        book.setName(nameBookEdit.getText());
        db.updateBook(book);
        bookTable.refresh();
    }

    public void deleteBookClick(ActionEvent event) {
        Book book = books.get(indexBook);
        db.deleteBook(book);
        books.remove(book);
    }

    public void getSelectedReader(MouseEvent mouseEvent) {
        //nameBookEdit.setText (books.get(indexBook).getName());
        indexReader = readerTable.getSelectionModel().getSelectedIndex();
        firstNameEdit.setText(readers.get(indexReader).getFirstName());
        secondNameEdit.setText(readers.get(indexReader).getSecondName());
        patronymicEdit.setText(readers.get(indexReader).getPatronymic());
    }

    public void addReaderClick(ActionEvent event) {
        Reader reader = new Reader();
        reader.setFirstName(firstNameEdit.getText());
        reader.setSecondName(secondNameEdit.getText());
        reader.setPatronymic(patronymicEdit.getText());
        reader.setLoanCount(0);
        reader.setReturnCount(0);

        db.addReader(reader);
        reader.setReaderID(db.getLastReader().getReaderID());

        readerTable.getItems().add(reader);

        firstNameEdit.clear();
        secondNameEdit.clear();
        patronymicEdit.clear();
    }

    public void saveReaderClick(ActionEvent event) {
        Reader reader = readers.get(indexReader);
        reader.setFirstName(firstNameEdit.getText());
        reader.setSecondName(secondNameEdit.getText());
        reader.setPatronymic(patronymicEdit.getText());
        db.updateReader(reader);
        readerTable.refresh();
    }

    public void getSelectedLoan(MouseEvent mouseEvent) {
        indexLoan = loanTable.getSelectionModel().getSelectedIndex();
        Book book = loans.get(indexLoan).getBook();
        Reader reader = loans.get(indexLoan).getReader();
        LocalDate date = loans.get(indexLoan).getDate();
        loanBookChoice.getSelectionModel().select(book);
        loanReaderChoice.getSelectionModel().select(reader);
        loanReturnBox.setSelected(loans.get(indexLoan).getState());
        loanDatePicker.setValue(date);
    }

    public void deleteReaderClick(ActionEvent event) {
        Reader reader = readers.get(indexReader);
        db.deleteReader(reader);
        readers.remove(reader);
    }

    public void addLoanClick(ActionEvent event) {
        Loan loan = new Loan();
        loan.setBook(loanBookChoice.getSelectionModel().getSelectedItem());
        loan.setReader(loanReaderChoice.getSelectionModel().getSelectedItem());
        loan.setDate(loanDatePicker.getValue());
        loan.setState(loanReturnBox.isSelected());

        db.addLoan(loan);
        Reader reader = loan.getReader();
        reader.setLoanCount(reader.getLoanCount()+1);

        if (loan.getState()){
            reader.setReturnCount(reader.getReturnCount()+1);
        }
        db.updateReader(reader);
        loan = db.getLastLoan();

        loanTable.getItems().add(loan);

        loanBookChoice.getSelectionModel().clearSelection();
        loanReaderChoice.getSelectionModel().clearSelection();
        loanReturnBox.setSelected(false);
        loanDatePicker.setValue(null);

        loanTable.refresh();
        readerTable.refresh();
    }

    public void saveLoanClick(ActionEvent event) {
        Loan loan = loans.get(indexLoan);
        loan.setBook(loanBookChoice.getSelectionModel().getSelectedItem());
        loan.setReader(loanReaderChoice.getSelectionModel().getSelectedItem());
        loan.setDate(loanDatePicker.getValue());
        loan.setState(loanReturnBox.isSelected());
        db.updateLoan(loan);

        loanBookChoice.getSelectionModel().clearSelection();
        loanReaderChoice.getSelectionModel().clearSelection();
        loanReturnBox.setSelected(false);
        loanDatePicker.setValue(null);

    }

    public void deleteLoanClick(ActionEvent event) {
        Loan loan = loans.get(indexLoan);
        db.deleteLoan(loan);
        loans.remove(loan);

        loanBookChoice.getSelectionModel().clearSelection();
        loanReaderChoice.getSelectionModel().clearSelection();
        loanReturnBox.setSelected(false);
        loanDatePicker.setValue(null);

    }


}
