package com.garnmael.lousticole.db;



import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.garnmael.lousticole.model.Qcm;
import com.garnmael.lousticole.model.User;

import java.util.List;

@Dao
public interface QcmDao {

    @Query("SELECT * FROM Qcm")
    List<Qcm> getAll();

    @Query("SELECT * FROM Qcm WHERE tag = :unTag")
    List<Qcm> getQcmFromTag(String unTag);

    @Insert
    long insert(Qcm qcm);

    @Insert
    long[] insertAll(Qcm... qcm);

    @Delete
    void delete(Qcm qcm);

    @Update
    void update(Qcm qcm);

}