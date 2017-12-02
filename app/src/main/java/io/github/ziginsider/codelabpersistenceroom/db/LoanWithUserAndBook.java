package io.github.ziginsider.codelabpersistenceroom.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.TypeConverters;

import java.util.Date;

/**
 * Created by zigin on 02.12.2017.
 */


public class LoanWithUserAndBook {

    public String id;

    @ColumnInfo(name="title")
    public String bookTitle;

    @ColumnInfo(name="name")
    public String userName;

    @TypeConverters(DateConverter.class)
    public Date startTime;

    @TypeConverters(DateConverter.class)
    public Date endTime;
}
