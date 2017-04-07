package model;

import java.util.List;

/**
 * Created by User on 001 01.04.17.
 */
public class Reader {
    private int readerID;
    private String firstName;
    private String secondName;
    private String patronymic;
    private int loanCount;
    private int returnCount;

    public int getReaderID() {
        return readerID;
    }

    public void setReaderID(int readerID) {
        this.readerID = readerID;
    }

    public int getLoanCount() {
        return loanCount;
    }

    public void setLoanCount(int loanCount) {
        this.loanCount = loanCount;
    }

    public int getReturnCount() {
        return returnCount;
    }

    public void setReturnCount(int returnCount) {
        this.returnCount = returnCount;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Reader reader = (Reader) o;

        if (readerID != reader.readerID) return false;
        if (!firstName.equals(reader.firstName)) return false;
        if (!secondName.equals(reader.secondName)) return false;
        return patronymic.equals(reader.patronymic);
    }

    @Override
    public int hashCode() {
        int result = readerID;
        result = 31 * result + firstName.hashCode();
        result = 31 * result + secondName.hashCode();
        result = 31 * result + patronymic.hashCode();
        return result;
    }

    @Override
    public String toString() {
        String result;
        result = secondName + " " + firstName.substring(0,1) + ".";
        if (patronymic != null){
            result+= patronymic.substring(0,1) + ".";
        }
        return result;
    }
}
