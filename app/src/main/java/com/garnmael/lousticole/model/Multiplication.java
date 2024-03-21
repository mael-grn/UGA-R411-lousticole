package com.garnmael.lousticole.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Multiplication implements Parcelable {
    private int nombre1;
    private int nombre2;

    private Integer reponseUtilisateur;

    public Multiplication(int nombre1, int nombre2) {
        this.nombre1 = nombre1;
        this.nombre2 = nombre2;
    }

    protected Multiplication(Parcel in) {
        nombre1 = in.readInt();
        nombre2 = in.readInt();
        if (in.readByte() == 0) {
            reponseUtilisateur = null;
        } else {
            reponseUtilisateur = in.readInt();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(nombre1);
        dest.writeInt(nombre2);
        if (reponseUtilisateur == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(reponseUtilisateur);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Multiplication> CREATOR = new Creator<Multiplication>() {
        @Override
        public Multiplication createFromParcel(Parcel in) {
            return new Multiplication(in);
        }

        @Override
        public Multiplication[] newArray(int size) {
            return new Multiplication[size];
        }
    };

    /**
     * retourne la question correspondant à la multiplication
     * @return
     */
    public String getQuestion() {
        return nombre1 + " x " + nombre2 + " = ";
    }

    /**
     * retourne si la réponse est correcte
     * @return
     */
    public boolean checkResponse() {
        if (reponseUtilisateur != null && nombre2*nombre1 == reponseUtilisateur) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * permet de saisir la réponse de l'utilisateur
     * @param reponse
     */
    public void repondre(int reponse) {
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



}
