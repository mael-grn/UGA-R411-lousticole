package com.garnmael.lousticole.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * repprésente la notion d'exercice portant sur un QCM
 */
public class ExerciceQcm implements Parcelable {

    private List<Qcm> questionList;

    private String tag;

    /**
     * constructeur à partir d'une liste de question de type qcm
     * @param questionList la liste de question
     * @param tag la matière concernée par ces questions
     */
    public ExerciceQcm(List<Qcm> questionList, String tag) {
        this.questionList = questionList;
        this.tag = tag;
    }


    protected ExerciceQcm(Parcel in) {
        questionList = in.createTypedArrayList(Qcm.CREATOR);
        tag = in.readString();
    }

    public static final Creator<ExerciceQcm> CREATOR = new Creator<ExerciceQcm>() {
        @Override
        public ExerciceQcm createFromParcel(Parcel in) {
            return new ExerciceQcm(in);
        }

        @Override
        public ExerciceQcm[] newArray(int size) {
            return new ExerciceQcm[size];
        }
    };

    public Qcm getQcm(int id) {
        return questionList.get(id);
    }
    public void setQuestionList(List<Qcm> questionList) {
        this.questionList = questionList;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    /**
     * renvoi la note finale de l'utilisateur, après avoir complété toutes les questions
     * @return
     */
    public int getNote() {
        int valNote = 0;
        for (Qcm question : questionList) {
            if (question.hasUserGoodResponse()) {
                valNote++;
            }
        }
        return valNote;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeTypedList(questionList);
        parcel.writeString(tag);
    }
}
