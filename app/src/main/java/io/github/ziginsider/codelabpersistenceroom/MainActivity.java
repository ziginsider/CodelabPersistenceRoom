package io.github.ziginsider.codelabpersistenceroom;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import io.github.ziginsider.codelabpersistenceroom.db.AppDatabase;
import io.github.ziginsider.codelabpersistenceroom.db.User;
import io.github.ziginsider.codelabpersistenceroom.db.utils.DatabaseInitializer;

public class MainActivity extends AppCompatActivity {

    private AppDatabase mDp;

    private TextView mYoungUsersTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mYoungUsersTextView = (TextView) findViewById(R.id.young_users_tv);

        mDp = AppDatabase.getInMemoryDatabase(getApplicationContext());

        populateDb();

        fetchData();
    }

    @Override
    protected void onDestroy() {
        AppDatabase.destroyInstance();
        super.onDestroy();
    }

    private void populateDb() {
        DatabaseInitializer.populateSync(mDp);
    }

    private void fetchData() {
        StringBuilder sb = new StringBuilder();
        List<User> youngUsers = mDp.userModel().findUsersYangerThan(35);
        for (User youngUser : youngUsers) {
            sb.append(String.format(Locale.US,
                    "%s, %s (%d)\n", youngUser.lastName, youngUser.name, youngUser.age));
        }
        mYoungUsersTextView.setText(sb);
    }
}
