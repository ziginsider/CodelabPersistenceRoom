package io.github.ziginsider.codelabpersistenceroom.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.Update;

import java.util.Date;
import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;
import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by zigin on 02.12.2017.
 */

@Dao
@TypeConverters(DateConverter.class)
public interface BookDao {

    @Query("SELECT * FROM Book WHERE id = :id")
    Book loadBookById(int id);

    @Query("SELECT * FROM Book " +
            "INNER JOIN Loan ON Loan.book_id = Book.id " +
            "INNER JOIN User ON User.id = Loan.user_id " +
            "WHERE User.name LIKE :userName")
    LiveData<List<Book>> findBooksBorrowedByName(String userName);

    @Query("SELECT * FROM Book " +
            "INNER JOIN Loan ON Loan.book_id = Book.id " +
            "INNER JOIN User ON User.id = Loan.user_id " +
            "WHERE User.name LIKE :userName " +
            "AND Loan.endTime > :after ")
    LiveData<List<Book>> findBooksBorrowedByNameAfter(String userName, Date after);

    @Query("SELECT * FROM Book " +
            "INNER JOIN Loan ON Loan.book_id = Book.id " +
            "INNER JOIN User ON User.id = Loan.user_id " +
            "WHERE User.name LIKE :userName")
    List<Book> findBooksBorrowedByNameSync(String userName);

    @Query("SELECT * FROM Book " +
            "INNER JOIN Loan ON Loan.book_id LIKE Book.id " +
            "WHERE Loan.user_id LIKE :userId ")
    LiveData<List<Book>> findBooksBorrowedByUser(String userId);

    @Query("SELECT * FROM Book " +
            "INNER JOIN Loan ON Loan.book_id LIKE Book.id " +
            "WHERE Loan.user_id LIKE :userId " +
            "AND Loan.endTime > :after ")
    LiveData<List<Book>> findBooksBorrowedByUserAfter(String userId, Date after);

    @Query("SELECT * FROM Book " +
            "INNER JOIN Loan ON Loan.book_id LIKE Book.id " +
            "WHERE Loan.user_id LIKE :userId ")
    List<Book> findBooksBorrowedByUserSync(String userId);

    @Query("SELECT * FROM Book")
    LiveData<List<Book>> findAllBooks();

    @Query("SELECT * FROM Book")
    List<Book> findAllBooksSync();

    @Insert(onConflict = IGNORE)
    void insertBook(Book book);

    @Update(onConflict = REPLACE)
    void updateBook(Book book);

    @Query("DELETE FROM Book")
    void deleteAll();
}
