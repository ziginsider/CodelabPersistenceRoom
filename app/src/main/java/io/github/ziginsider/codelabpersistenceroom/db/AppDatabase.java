package io.github.ziginsider.codelabpersistenceroom.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by zigin on 02.12.2017.
 */

@Database(entities = {User.class, Book.class, Loan.class}, version = 1)
public class AppDatabase extends RoomDatabase {

    public static AppDatabase INSTANCE;

    public abstract UserDao userModel();

    public abstract BookDao bookModel();

    public abstract LoanDao loanModel();

    public static AppDatabase getInMemoryDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.inMemoryDatabaseBuilder(context.getApplicationContext(), AppDatabase.class)
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
