package com.garnmael.lousticole.db;



import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.garnmael.lousticole.model.Note;
import com.garnmael.lousticole.model.User;

import java.util.List;

@Dao
public interface NoteDao {


    @Query("SELECT * FROM Note WHERE idUser = :idUser")
    List<Note> getNotesFromUser(Long idUser);

    @Insert
    long insert(Note note);

    @Insert
    long[] insertAll(Note... notes);

    @Delete
    void delete(Note note);

    @Update
    void update(Note note);

}