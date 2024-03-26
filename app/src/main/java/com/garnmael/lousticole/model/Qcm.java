package com.garnmael.lousticole.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * repprésente la notion d'une seule question de type QCM
 */
@Entity(tableName = "qcm")
public class Qcm implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private long id;

    //corresponds à la matière sur laquelle porte la question
    private String tag;

    private String question;

    private String reponse1;

    private String reponse2;

    private String reponseUtilisateur;

    private String reponseCorrect;

    protected Qcm(Parcel in) {
        id = in.readLong();
        tag = in.readString();
        question = in.readString();
        reponse1 = in.readString();
        reponse2 = in.readString();
        reponseUtilisateur = in.readString();
        reponseCorrect = in.readString();
    }

    public static final Creator<Qcm> CREATOR = new Creator<Qcm>() {
        @Override
        public Qcm createFromParcel(Parcel in) {
            return new Qcm(in);
        }

        @Override
        public Qcm[] newArray(int size) {
            return new Qcm[size];
        }
    };

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getReponse1() {
        return reponse1;
    }

    public void setReponse1(String reponse1) {
        this.reponse1 = reponse1;
    }

    public String getReponse2() {
        return reponse2;
    }

    public void setReponse2(String reponse2) {
        this.reponse2 = reponse2;
    }

    public String getReponseUtilisateur() {
        return reponseUtilisateur;
    }

    public void setReponseUtilisateur(String reponseUtilisateur) {
        this.reponseUtilisateur = reponseUtilisateur;
        System.out.println(reponseUtilisateur + " different de " + this.reponseCorrect);
    }

    public String getReponseCorrect() {
        return reponseCorrect;
    }

    public void setReponseCorrect(String reponseCorrect) {
        this.reponseCorrect = reponseCorrect;
    }

    public Qcm(String tag, String question, String reponse1, String reponse2, String reponseCorrect) {
        this.tag = tag;
        this.question = question;
        this.reponse1 = reponse1;
        this.reponse2 = reponse2;
        this.reponseCorrect = reponseCorrect;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }



    public String getTag() {
        return tag;
    }

    public String getQuestion() {
        return question;
    }

    public String getCorrectReponse() {
        return this.reponseCorrect;
    }

    public boolean hasUserGoodResponse() {
        if (this.reponseUtilisateur != null && !this.reponseUtilisateur.isEmpty()) {
            return this.reponseCorrect.compareTo(this.reponseUtilisateur) ==0;
        } else {
            return false;
        }
    }

    public List<String> getShuffleList() {
        List<String> reponseList = new ArrayList<>();
        reponseList.add(reponse1);
        reponseList.add(reponse2);
        reponseList.add(reponseCorrect);
        Collections.shuffle(reponseList);
        return reponseList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(tag);
        parcel.writeString(question);
        parcel.writeString(reponse1);
        parcel.writeString(reponse2);
        parcel.writeString(reponseUtilisateur);
        parcel.writeString(reponseCorrect);
    }
}
