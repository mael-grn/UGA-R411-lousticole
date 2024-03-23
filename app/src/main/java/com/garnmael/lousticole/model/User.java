package com.garnmael.lousticole.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Classe permettant de reppresenter une entitée utilisateur.
 * Agit egalement comme une passerelle vers la db
 */

@Entity(tableName = "user")
public class User implements Parcelable {
    private String nom;
    private String prenom;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    private Boolean isAnonyme;


    /**
     * constructeur de la class user
     * si le prenom ou le nom est vide, alors l'utilisateur est anonyme
     */
    public User(String nom, String prenom) {
        if (nom.isEmpty() || prenom.isEmpty()) {
            this.nom = "";
            this.prenom = "";
            this.isAnonyme = true;
        } else {
            this.nom = nom;
            this.prenom = prenom;
            this.isAnonyme = false;
        }
    }

    /**
     * Constructeur de la class user pour un utilisateur anonyme
     */
    public User() {
        this.nom = "";
        this.prenom = "";
        this.isAnonyme = true;
    }


    protected User(Parcel in) {
        nom = in.readString();
        prenom = in.readString();
        id = in.readLong();
        byte tmpIsAnonyme = in.readByte();
        isAnonyme = tmpIsAnonyme == 0 ? null : tmpIsAnonyme == 1;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public Boolean isAnonyme() {
        return isAnonyme;
    }

    public void setAnonyme(Boolean anonyme) {
        isAnonyme = anonyme;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    /**
     * setteur du nom. Si le nom et prenom de sont plus vide, l'utilisateur n'est plus anonyme
     * @param nom
     */
    public void setNom(String nom) {
        this.nom = nom;
        if (!nom.isEmpty() && !prenom.isEmpty()) {
            this.isAnonyme = false;
        }
    }

    public String getPrenom() {
        return prenom;
    }

    /**
     * setteur du prenom. Si le nom et prenom de sont plus vide, l'utilisateur n'est plus anonyme
     * @param prenom
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
        if (!nom.isEmpty() && !prenom.isEmpty()) {
            this.isAnonyme = false;
        }
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(nom);
        parcel.writeString(prenom);
        parcel.writeLong(id);
        parcel.writeByte((byte) (isAnonyme == null ? 0 : isAnonyme ? 1 : 2));
    }
}
