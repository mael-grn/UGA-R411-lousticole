package com.garnmael.lousticole.db;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.garnmael.lousticole.model.Note;
import com.garnmael.lousticole.model.Qcm;
import com.garnmael.lousticole.model.User;

@Database(entities = {User.class, Note.class, Qcm.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();
    public abstract NoteDao noteDao();

    public abstract QcmDao qcmDao();
}