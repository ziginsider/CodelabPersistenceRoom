package io.github.ziginsider.codelabpersistenceroom.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by zigin on 01.12.2017.
 */

@Entity
public class Book {
    @PrimaryKey
    @NonNull
    public String id;

    public String title;
}
