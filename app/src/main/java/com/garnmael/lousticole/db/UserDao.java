package com.garnmael.lousticole.db;



import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.garnmael.lousticole.model.User;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM User")
    List<User> getAll();

    @Query("SELECT * FROM User WHERE nom = :nomParam AND prenom = :prenomParam")
    User getUser(String nomParam, String prenomParam);

    @Insert
    long insert(User user);

    @Insert
    long[] insertAll(User... users);

    @Delete
    void delete(User user);

    @Update
    void update(User user);

}