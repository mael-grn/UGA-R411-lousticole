package com.garnmael.lousticole.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

/**
 * Classe permettant de reppresenter une entitée utilisateur.
 * Agit egalement comme une passerelle vers la db
 */
public class User implements Parcelable {
    private String nom;
    private String prenom;

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

    public Boolean getAnonyme() {
        return isAnonyme;
    }

    public void setAnonyme(Boolean anonyme) {
        isAnonyme = anonyme;
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

    /**
     * permets de mettre à jour les informations en base de données
     */
    public void pushDb() {
        //@todo mettre à jour les informations en base de données ici
    }

    /**
     * permets de mettre à jour l'entité en recuperant les informations depuis la base de données
     */
    public void pullDb() {
        //@todo mettre ici la recuperation des données
    }

    /**
     * verifie si l'utilisateur existe déjà en bd
     * @return
     */
    public boolean alreadyExistInDb() {
        //@todo methode de verification en bd
        return false;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(nom);
        parcel.writeString(prenom);
        parcel.writeByte((byte) (isAnonyme == null ? 0 : isAnonyme ? 1 : 2));
    }
}
