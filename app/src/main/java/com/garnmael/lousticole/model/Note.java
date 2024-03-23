package com.garnmael.lousticole.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "note")
public class Note implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String exercice;

    private long note;

    private long idUser;

    private String date;

    public Note(String exercice, long note, long idUser, String date) {
        this.exercice = exercice;
        this.note = note;
        this.idUser = idUser;
        this.date = date;
    }

    protected Note(Parcel in) {
        id = in.readLong();
        exercice = in.readString();
        note = in.readLong();
        idUser = in.readLong();
        date = in.readString();
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    public String getExercice() {
        return exercice;
    }

    public void setExercice(String exercice) {
        this.exercice = exercice;
    }

    public long getNote() {
        return note;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setNote(long note) {
        this.note = note;
    }


    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(exercice);
        parcel.writeLong(note);
        parcel.writeLong(idUser);
        parcel.writeString(date);
    }
}
