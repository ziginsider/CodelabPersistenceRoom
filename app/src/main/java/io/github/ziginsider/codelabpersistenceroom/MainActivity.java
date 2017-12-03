package io.github.ziginsider.codelabpersistenceroom;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import io.github.ziginsider.codelabpersistenceroom.Model.BooksBorrowedByUserViewModel;
import io.github.ziginsider.codelabpersistenceroom.db.AppDatabase;
import io.github.ziginsider.codelabpersistenceroom.db.Book;
import io.github.ziginsider.codelabpersistenceroom.db.User;
import io.github.ziginsider.codelabpersistenceroom.db.utils.DatabaseInitializer;

public class MainActivity extends AppCompatActivity {

    private AppDatabase mDb;

    //1
    private TextView mYoungUsersTextView;

    //2
//    private TextView mBooksTextView;
    private Button mBooksButton;

    //3
    private BooksBorrowedByUserViewModel mViewModel;
    @SuppressWarnings("unused")
    private TextView mBooksTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mYoungUsersTextView = (TextView) findViewById(R.id.young_users_tv);

        //2
        mBooksTextView = (TextView) findViewById(R.id.books_tv);
        mBooksButton = (Button) findViewById(R.id.button);

        //2
        mBooksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //2
                //fetchData();

                //3
                onRefreshBtClicked(view);
            }
        });


        //1 and 2
        //mDb = AppDatabase.getInMemoryDatabase(getApplicationContext());

        //populateDb();

        //fetchData();

        //3
        mViewModel = ViewModelProviders.of(this).get(BooksBorrowedByUserViewModel.class);

        SubscribeUiBooks();

    }

    private void SubscribeUiBooks() {

        mViewModel.books.observe(this, new Observer<List<Book>>() {
            @Override
            public void onChanged(@NonNull final List<Book> books) {
                showBooksInUi(books, mBooksTextView);
            }
        });

    }

    @SuppressWarnings("unused")
    private void showBooksInUi(final @NonNull List<Book> books) {
        StringBuilder sb = new StringBuilder();

        for (Book book : books) {
            sb.append(book.title);
            sb.append("\n");

        }
        mBooksTextView.setText(sb.toString());
    }

    public void onRefreshBtClicked(View view) {
        mViewModel.createDb();
    }

    @Override
    protected void onDestroy() {
        AppDatabase.destroyInstance();
        super.onDestroy();
    }

    private void populateDb() {
        DatabaseInitializer.populateAsync(mDb);
    }

    private void fetchData() {
        StringBuilder sb = new StringBuilder();
        List<User> youngUsers = mDb.userModel().findUsersYangerThan(35);
        for (User youngUser : youngUsers) {
            sb.append(String.format(Locale.US,
                    "%s, %s (%d)\n", youngUser.lastName, youngUser.name, youngUser.age));
        }
        mYoungUsersTextView.setText(sb);

        // This activity is executing a query on the main thread, making the UI perform badly.
        List<Book> books = mDb.bookModel().findBooksBorrowedByNameSync("Alexander");
        showListInUI(books, mBooksTextView);
    }

    private static void showListInUI(final @NonNull List<Book> books,
                                     final TextView booksTextView) {
        StringBuilder sb = new StringBuilder();
        for (Book book : books) {
            sb.append(book.title);
            sb.append("\n");
        }
        booksTextView.setText(sb.toString());
    }
}
