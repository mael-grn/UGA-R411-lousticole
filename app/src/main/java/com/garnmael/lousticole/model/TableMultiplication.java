package com.garnmael.lousticole.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class TableMultiplication implements Parcelable{

    private ArrayList<Multiplication> multiplicationList;

    private int nombre;

    public TableMultiplication(ArrayList<Multiplication> multiplicationList) {
        this.multiplicationList = multiplicationList;
    }

    public TableMultiplication(int nombre) {
        this.multiplicationList = new ArrayList<>();
        this.nombre = nombre;
        this.generateCompleteTable();
    }

    /**
     * permet de rajouter une multiplication
     * @param multiplication
     */
    public void addMultiplication(Multiplication multiplication) {
        multiplicationList.add(multiplication);
    }

    /**
     *
     * @return le nombre multipliant de la table
     */
    public int getNombreTable() {
        return this.nombre;
    }

    /**
     * verifie les réponses de l'utilisateur aux questions
     * @return
     */
    public boolean checkAnswers() {
        for (Multiplication mult : multiplicationList) {
            if (!mult.checkResponse()) {
                return false;
            }
        }
        return true;
    }

    /**
     * donne le nombre d'erreur
     * @return
     */
    public int getNbErrors() {
        int nbError = 0;
        for (Multiplication mult : multiplicationList) {
            if (!mult.checkResponse()) {
                nbError++;
            }
        }
        return nbError;
    }

    /**
     * permet de récuperer une multiplication
     * @param valeur
     * @return
     */
    public Multiplication getMultiplication(int valeur) {
        return multiplicationList.get(valeur);
    }

    /**
     * retourne le nombre de multiplications
     * @return
     */
    public int getNbMultiplications() {
        return multiplicationList.size();
    }

    /**
     * permet de génerer automatiquement une table de 1 à 10
     */
    public void generateCompleteTable() {
        for (int i=1; i<=10; i++) {
            this.addMultiplication(new Multiplication(i, nombre));
        }
    }

    protected TableMultiplication(Parcel in) {
        multiplicationList = in.createTypedArrayList(Multiplication.CREATOR);
        nombre = in.readInt();
    }

    public static final Creator<TableMultiplication> CREATOR = new Creator<TableMultiplication>() {
        @Override
        public TableMultiplication createFromParcel(Parcel in) {
            return new TableMultiplication(in);
        }

        @Override
        public TableMultiplication[] newArray(int size) {
            return new TableMultiplication[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(multiplicationList);
        parcel.writeInt(nombre);
    }
}
