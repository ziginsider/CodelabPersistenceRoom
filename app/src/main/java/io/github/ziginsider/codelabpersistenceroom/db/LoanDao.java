package io.github.ziginsider.codelabpersistenceroom.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;

import java.util.Date;
import java.util.List;

/**
 * Created by zigin on 02.12.2017.
 */

@Dao
@TypeConverters(DateConverter.class)
public interface LoanDao {

    @Query("SELECT * FROM Loan")
    LiveData<List<Loan>> findAllLoans();

    @Query("SELECT Loan.id, Book.title, User.name, Loan.startTime, Loan.endTime FROM Loan " +
            "INNER JOIN Book ON Loan.book_id = Book.id " +
            "INNER JOIN User ON User.id = Loan.user_id ")
    LiveData<List<LoanWithUserAndBook>> findAllWithUserAndBook();

    @Query("SELECT Loan.id, Book.title AS title, User.name AS name, Loan.startTime, Loan.endTime " +
            "FROM Book " +
            "INNER JOIN Loan ON Loan.book_id = Book.id " +
            "INNER JOIN User ON User.id = Loan.user_id " +
            "WHERE User.name LIKE :userName " +
            "AND Loan.endTime > :after ")
    LiveData<List<LoanWithUserAndBook>> findLoansByNameAfter(String userName, Date after);

    @Insert()
    void insertLoan(Loan loan);

    @Query("DELETE FROM Loan")
    void deleteAll();
}
