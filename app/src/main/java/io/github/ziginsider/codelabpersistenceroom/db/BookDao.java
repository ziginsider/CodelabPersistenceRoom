package io.github.ziginsider.codelabpersistenceroom.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by zigin on 02.12.2017.
 */

@Dao
public interface BookDao {

    @Query("SELECT * FROM Book WHERE id = :id")
    Book loadBookById(int id);

    @Query("SELECT * FROM Book " +
            "")
    LiveData<List<Book>> findBooksBorrowedByName(String userName);

}
