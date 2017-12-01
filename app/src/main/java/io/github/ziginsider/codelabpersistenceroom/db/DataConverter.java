package io.github.ziginsider.codelabpersistenceroom.db;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Created by zigin on 02.12.2017.
 */

public class DataConverter {
    @TypeConverter
    public static Date toDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long toTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
