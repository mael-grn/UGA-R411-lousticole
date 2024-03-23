package com.garnmael.lousticole.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class TableOperation implements Parcelable{

    private ArrayList<Operation> operationList;

    //l'opérande qui sera répété dans les opérations
    private int operande;

    private char operateur;

    public TableOperation(ArrayList<Operation> operationList, char operateur) {
        this.operationList = operationList;
        this.operateur = operateur;
    }

    public TableOperation(int operande, char operateur) {
        this.operationList = new ArrayList<>();
        this.operande = operande;
        this.operateur = operateur;
        this.generateCompleteTable();
    }

    public char getOperateur() {
        return operateur;
    }

    public void setOperateur(char operateur) {
        this.operateur = operateur;
    }

    protected TableOperation(Parcel in) {
        operationList = in.createTypedArrayList(Operation.CREATOR);
        operande = in.readInt();
        operateur = (char) in.readInt();
    }

    public static final Creator<TableOperation> CREATOR = new Creator<TableOperation>() {
        @Override
        public TableOperation createFromParcel(Parcel in) {
            return new TableOperation(in);
        }

        @Override
        public TableOperation[] newArray(int size) {
            return new TableOperation[size];
        }
    };

    /**
     * permet de rajouter une multiplication
     * @param operation
     */
    public void addOperation(Operation operation) {
        operationList.add(operation);
    }

    /**
     *
     * @return le nombre multipliant de la table
     */
    public int getNombreTable() {
        return this.operande;
    }

    /**
     * verifie les réponses de l'utilisateur aux questions
     * @return
     */
    public boolean checkAnswers() {
        for (Operation op : operationList) {
            if (!op.checkResponse()) {
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
        for (Operation op : operationList) {
            if (!op.checkResponse()) {
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
    public Operation getOperation(int valeur) {
        return operationList.get(valeur);
    }

    /**
     * retourne le nombre de multiplications
     * @return
     */
    public int getNbOperation() {
        return operationList.size();
    }

    /**
     * permet de génerer automatiquement une table de 1 à 10
     */
    public void generateCompleteTable() {
        for (int i=1; i<=10; i++) {
            this.addOperation(new Operation(i, operande, this.operateur));
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeTypedList(operationList);
        parcel.writeInt(operande);
        parcel.writeInt((int) operateur);
    }
}
