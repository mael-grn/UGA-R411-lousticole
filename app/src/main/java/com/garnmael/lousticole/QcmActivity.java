package com.garnmael.lousticole;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.garnmael.lousticole.model.ExerciceQcm;
import com.garnmael.lousticole.model.User;

import java.util.List;

public class QcmActivity extends AppCompatActivity {

    public static final String USER_KEY = "user_key";

    public static final String QCM_KEY = "qcm_key";

    public static final String CONTEXT_KEY = "context_key";

    public static final String SUBJECT_KEY = "matiere_key";

    //corresponds à la question actuelle du QCM
    private int currentQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qcm);

        currentQuestion = 0;

        User user = getIntent().getParcelableExtra(USER_KEY);
        ExerciceQcm exerciceQcm = getIntent().getParcelableExtra(QCM_KEY);

        //mise à jours des premières questions
        updateView(exerciceQcm);

        Button validateButton = findViewById(R.id.qcm_validate_button);
        Button backButton = findViewById(R.id.qcm_quit_button);
        RadioGroup responseRadioGroup = findViewById(R.id.qcm_reponses_radiogroup);

        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //si l'utilisateur n'a coché une reponse, on l'avertis
                int checkedButton = responseRadioGroup.getCheckedRadioButtonId();
                if (checkedButton > 0) {
                    //tant que on a pas fait le nombre max de question, on continu
                    //si ne nombre maximal est atteint, on termine l'activité
                    if (currentQuestion < 9) {
                        updateReponse(exerciceQcm);
                        currentQuestion++;
                        updateView(exerciceQcm);
                    } else {
                        String context = getIntent().getStringExtra(CONTEXT_KEY);
                        String subject = getIntent().getStringExtra(SUBJECT_KEY);

                        Intent intent=new Intent();

                        if (subject.compareTo("f")==0) {
                            intent.putExtra(FrancaisActivity.QCM_KEY, exerciceQcm);
                            intent.putExtra(FrancaisActivity.CONTEXT_KEY, context);
                        } else if (subject.compareTo("h")==0) {
                            intent.putExtra(HistoireActivity.QCM_KEY, exerciceQcm);
                            intent.putExtra(HistoireActivity.CONTEXT_KEY, context);
                        }
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Il faut sélectionner une réponse", Toast.LENGTH_LONG);
                }
            }
        });
    }

    /**
     * permet de mettre à jours la réponse de l'utilisateur dans le modele
     * @param exerciceQcm le modele dont on souhaite mettre à jours la réponse de l'utilisateur
     */
    private void updateReponse(ExerciceQcm exerciceQcm) {
        RadioGroup reponseRadioGroup = findViewById(R.id.qcm_reponses_radiogroup);
        RadioButton checkedReponse = findViewById(reponseRadioGroup.getCheckedRadioButtonId());
        String reponseUtilisateur = checkedReponse.getText().toString();
        exerciceQcm.getQcm(currentQuestion).setReponseUtilisateur(reponseUtilisateur);
    }

    /**
     * mets à jours le contenu de la page en fonction de la question actuelle
     * @param exerciceQcm la liste de question
     */
    private void updateView(ExerciceQcm exerciceQcm) {
        TextView titleTextView = findViewById(R.id.qcm_title_textview);
        TextView questionTextView = findViewById(R.id.qcm_question_textview);
        RadioButton radioButton1 = findViewById(R.id.qcm_reponse1_radiobutton);
        RadioButton radioButton2 = findViewById(R.id.qcm_reponse2_radiobutton);
        RadioButton radioButton3 = findViewById(R.id.qcm_reponse3_radiobutton);

        titleTextView.setText("Question " + (currentQuestion+1) + "/10");
        questionTextView.setText(exerciceQcm.getQcm(currentQuestion).getQuestion());
        List<String> shuffleQuestion = exerciceQcm.getQcm(currentQuestion).getShuffleList();
        radioButton1.setText(shuffleQuestion.get(0));
        radioButton2.setText(shuffleQuestion.get(1));
        radioButton3.setText(shuffleQuestion.get(2));
    }

}
