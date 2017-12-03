package io.github.ziginsider.codelabpersistenceroom.Model;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


import io.github.ziginsider.codelabpersistenceroom.db.AppDatabase;
import io.github.ziginsider.codelabpersistenceroom.db.Book;
import io.github.ziginsider.codelabpersistenceroom.db.LoanWithUserAndBook;
import io.github.ziginsider.codelabpersistenceroom.db.utils.DatabaseInitializer;

/**
 * Created by zigin on 02.12.2017.
 */

public class    BooksBorrowedByUserViewModel extends AndroidViewModel {

    public final LiveData<List<Book>> books;

    private LiveData<String> mLoansResult;

    private AppDatabase mDb;

    public BooksBorrowedByUserViewModel(@NonNull Application application) {
        super(application);
        createDb();

        //3
        //books = mDb.bookModel().findBooksBorrowedByName("Alexander");

        //4
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        Date yesterday = calendar.getTime();
        books = mDb.bookModel().findBooksBorrowedByNameAfter("Alexander", yesterday);
    }

    public void createDb() {
        mDb = AppDatabase.getInMemoryDatabase(this.getApplication());

        DatabaseInitializer.populateAsync(mDb);

        subscribeToDbChanges();
    }

    public LiveData<String> getLoansResult() {
        return mLoansResult;
    }

    private void subscribeToDbChanges() {
        // TODO: Modify this query to show only recent loans from specific user
        LiveData<List<LoanWithUserAndBook>> loans
                = mDb.loanModel().findLoansByNameAfter("Alexander", getYesterdayDate());

        // Instead of exposing the list of Loans, we can apply a transformation and expose Strings.
        mLoansResult = Transformations.map(loans,
                new Function<List<LoanWithUserAndBook>, String>() {
                    @Override
                    public String apply(List<LoanWithUserAndBook> loansWithUserAndBook) {
                        StringBuilder sb = new StringBuilder();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm",
                                Locale.US);

                        for (LoanWithUserAndBook loan : loansWithUserAndBook) {
                            sb.append(String.format("%s\n  (Returned: %s)\n",
                                    loan.bookTitle,
                                    simpleDateFormat.format(loan.endTime)));
                        }
                        return sb.toString();
                    }
                });
    }

    @SuppressWarnings("unused")
    private Date getYesterdayDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        return calendar.getTime();
    }
}
