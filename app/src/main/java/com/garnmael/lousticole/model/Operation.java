package com.garnmael.lousticole.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

/**
 * représente la notion d'une seule opération
 */
public class Operation implements Parcelable {
    private int nombre1;
    private int nombre2;

    private char operateur;

    private Integer reponseUtilisateur;

    public Operation(int nombre1, int nombre2, char operateur) {
        this.nombre1 = nombre1;
        this.nombre2 = nombre2;
        this.operateur = operateur;
    }

    protected Operation(Parcel in) {
        nombre1 = in.readInt();
        nombre2 = in.readInt();
        operateur = (char) in.readInt();
        if (in.readByte() == 0) {
            reponseUtilisateur = null;
        } else {
            reponseUtilisateur = in.readInt();
        }
    }

    public static final Creator<Operation> CREATOR = new Creator<Operation>() {
        @Override
        public Operation createFromParcel(Parcel in) {
            return new Operation(in);
        }

        @Override
        public Operation[] newArray(int size) {
            return new Operation[size];
        }
    };

    /**
     * retourne la question correspondant à la multiplication
     * @return la question
     */
    public String getQuestion() {
        return nombre1 + " " + operateur + " " + nombre2 + " = ";
    }

    /**
     * retourne si la réponse est correcte
     * @return true si l'utilisateur à juste
     */
    public boolean checkResponse() {
        if (this.operateur == '*') {
            return reponseUtilisateur != null && (nombre2 * nombre1 == reponseUtilisateur);
        } else if (this.operateur == '+') {
            return reponseUtilisateur != null && (nombre2 + nombre1 == reponseUtilisateur);
        } else if (this.operateur == '-') {

            return reponseUtilisateur != null && (nombre1-nombre2==reponseUtilisateur);
        } else {
            return false;
        }
    }

    /**
     * permet de saisir la réponse de l'utilisateur
     * @param reponse
     */
    public void repondre(Integer reponse) {
        reponseUtilisateur = reponse;
    }

    /**
     * retourne la réponse de l'utilisateur
     * @return
     */
    public Integer getReponseUtilisateur() {
        return reponseUtilisateur;
    }

    /**
     * indique si l'utilisateur a répondu à la multiplication
     * @return
     */
    public boolean hasUserResponded() {
        return reponseUtilisateur != null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(nombre1);
        parcel.writeInt(nombre2);
        parcel.writeInt((int) operateur);
        if (reponseUtilisateur == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(reponseUtilisateur);
        }
    }
}
