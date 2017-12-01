package io.github.ziginsider.codelabpersistenceroom.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

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

    @Insert(onConflict = IGNORE)
    void insertUser(User user);

    @Delete
    void deleteUser(User user);

    @Query("delete from user where name like :badName OR lastName like :badName")
    void deleteUsesrsByName(String badName);

    @Insert(onConflict = IGNORE)
    void insertOrReplaceUsers(User... users);

    @Delete
    void deleteUsers(User user1, User user2);

    @Query("SELECT * FROM User WHERE age < :age")
    List<User> findUsersYangerThan(int age);

    @Query("DELETE FROM User")
    void deleteAll();
}
