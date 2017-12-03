package io.github.ziginsider.codelabpersistenceroom.Model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import io.github.ziginsider.codelabpersistenceroom.db.AppDatabase;
import io.github.ziginsider.codelabpersistenceroom.db.Book;
import io.github.ziginsider.codelabpersistenceroom.db.utils.DatabaseInitializer;

/**
 * Created by zigin on 02.12.2017.
 */

public class BooksBorrowedByUserViewModel extends AndroidViewModel {

    public final LiveData<List<Book>> books;

    private AppDatabase mDb;

    public BooksBorrowedByUserViewModel(@NonNull Application application) {
        super(application);
        createDb();

        books = mDb.bookModel().findBooksBorrowedByName("Alexander");
    }

    public void createDb() {
        mDb = AppDatabase.getInMemoryDatabase(this.getApplication());

        DatabaseInitializer.populateAsync(mDb);
    }
}
