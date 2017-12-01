package io.github.ziginsider.codelabpersistenceroom.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by zigin on 01.12.2017.
 */

@Dao
public interface UserDao {

    @Query("select * from user")
    List<User> loadAllUser();

    @Query("select * from user where id = :id")
    User loadUserById(int id);

    @Query("select * from user where name = :firstName and lastName = :lastName")
    List<User> findUserByNameAndLastName(String firstName, String lastName);

    @Insert(onConflict = Ignore)
    void insertUser(User user);


    @Query()
    @Query() }
