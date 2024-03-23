package com.garnmael.lousticole;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.garnmael.lousticole.model.Note;
import com.garnmael.lousticole.model.TableMultiplication;
import com.garnmael.lousticole.model.User;

import java.util.ArrayList;


public class TableMultiplicationActivity extends AppCompatActivity{

    public static final String TABLE_KEY = "table_key";

    public static final String CONTEXT_KEY = "context_key";

    public static final String USER_KEY = "user_key";

    private ArrayList<LinearLayout> multiplicationList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // On charge le XML pour créer l'arbre graphique
        setContentView(R.layout.table_multiplication);
        Button validateButton = findViewById(R.id.tabMult_validate_button);

        //La table de multiplication peut etre utilisée selon différents contextes (normal, correction, entrainement) context permet de recupérer le contexte d'utilisation
        String context = getIntent().getStringExtra(CONTEXT_KEY);

        //On recupere notre table de multiplication, on mets à jour notre liste de multiplication et on l'affiche
        TableMultiplication tableMultiplication = getIntent().getParcelableExtra(TABLE_KEY);
        updatePageContent(tableMultiplication);
        drawContent();

        /**
         * Corresponds à l'evenement au clique du bouton valider.
         */
        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUserResponses(tableMultiplication);

                //traitement des réponses possibles selon le contexte d'utilisation
                if(context.compareTo("entrainement") == 0) {
                    Intent intent=new Intent();
                    intent.putExtra(MathActivity.RESULT_TABLE_KEY, tableMultiplication);
                    if (tableMultiplication.checkAnswers()) {
                        setResult(RESULT_OK,intent);
                    } else {
                        setResult(RESULT_CANCELED,intent);
                    }
                } else if (context.compareTo("correction")==0) {
                    Intent intent=new Intent();
                    intent.putExtra(ResultActivity.RESULT_TABLE_KEY, tableMultiplication);

                    if (tableMultiplication.checkAnswers()) {
                        setResult(RESULT_OK,intent);
                    } else {
                        setResult(RESULT_CANCELED,intent);
                    }
                } else if (context.compareTo("note")==0) {
                    Intent intent=new Intent();
                    User user = getIntent().getParcelableExtra(USER_KEY);
                    intent.putExtra(MathActivity.RESULT_TABLE_KEY, tableMultiplication);
                    if (tableMultiplication.checkAnswers()) {
                        setResult(RESULT_OK,intent);
                    } else {
                        setResult(RESULT_CANCELED,intent);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Une erreur s'est produite : le context d'utilisation est pas ou mal définit", Toast.LENGTH_LONG).show();
                    Intent intent=new Intent();
                    intent.putExtra(MathActivity.RESULT_TABLE_KEY, tableMultiplication);
                    setResult(RESULT_CANCELED,intent);
                }

                finish();
            }
        });
    }

    /**
     * Permet de mettre à jour l'affichage de la page en fonction de la liste de questions.
     * Il peut etre utile de d'abord mettre à jour ces derniers avec updatePageContent
     */
    private void drawContent() {
        LinearLayout container = findViewById(R.id.tabMult_table_container);
        for (LinearLayout multiplication : multiplicationList) {
            container.addView(multiplication);
        }
    }

    /**
     * Permet de mettre à jour la liste de question, et de champs de saisie de réponse en fonction de la table mis en paramètre.
     * Ces question et champs sont récupérés à partir de activity_question_answer.
     * @param table la table de multiplication sur laquelle on se basera pour mettre à jour le contenu de la page
     */
    private void updatePageContent(TableMultiplication table) {
        for (int i=0; i<table.getNbMultiplications(); i++) {
            LinearLayout container = (LinearLayout) getLayoutInflater().inflate(R.layout.activity_question_answer, null);
            TextView question = container.findViewById(R.id.question_sample);
            EditText reponse = container.findViewById(R.id.answer_sample);

            question.setText(table.getMultiplication(i).getQuestion());

            if (table.getMultiplication(i).hasUserResponded()) {
                reponse.setText(table.getMultiplication(i).getReponseUtilisateur().toString());
            }
            multiplicationList.add(container);
        }
    }

    /**
     * Permet de mettre à jour les réponses de l'utilisateur dans le modèle en fonction de ce qu'il a saisie
     * @param table correspond à la table à modifier
     */
    private void updateUserResponses(TableMultiplication table) {
        for (int i=0; i<multiplicationList.size(); i++) {
            EditText reponseEdit = multiplicationList.get(i).findViewById(R.id.answer_sample);
            String reponse = String.valueOf(reponseEdit.getText());
            try {
                if (!reponse.isEmpty()) {
                    table.getMultiplication(i).repondre(Integer.parseInt(reponse));
                }
            } catch (Exception e) {
                table.getMultiplication(i).repondre(-1);
            }
        }
    }

}
