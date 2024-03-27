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

import com.garnmael.lousticole.model.TableOperation;
import com.garnmael.lousticole.model.User;

import java.util.ArrayList;


public class TableOperationActivity extends AppCompatActivity{

    public static final String TABLE_KEY = "table_key";

    public static final String CONTEXT_KEY = "context_key";

    public static final String USER_KEY = "user_key";

    public static final String STATE_TABLE = "state_table";


    private ArrayList<LinearLayout> operationsList = new ArrayList<>();

    private TableOperation tableOperation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        // On charge le XML pour créer l'arbre graphique
        setContentView(R.layout.table_operation);
        Button validateButton = findViewById(R.id.tabMult_validate_button);

        //La table de multiplication peut etre utilisée selon différents contextes (normal, correction, entrainement) context permet de recupérer le contexte d'utilisation
        String context = getIntent().getStringExtra(CONTEXT_KEY);

        if (savedInstanceState != null) {
            // Restore value of members from saved state
            tableOperation = savedInstanceState.getParcelable(STATE_TABLE);
        } else {
            tableOperation = getIntent().getParcelableExtra(TABLE_KEY);
        }


        //On recupere notre table de multiplication, on mets à jour notre liste de multiplication et on l'affiche
        updatePageContent(tableOperation);
        drawContent();

        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUserResponses(tableOperation);

                //traitement des réponses possibles selon le contexte d'utilisation
                if(context.compareTo("training") == 0) {
                    Intent intent=new Intent();
                    intent.putExtra(MathActivity.RESULT_TABLE_KEY, tableOperation);
                    if (tableOperation.checkAnswers()) {
                        setResult(RESULT_OK,intent);
                    } else {
                        setResult(RESULT_CANCELED,intent);
                    }
                } else if (context.compareTo("correction")==0) {
                    Intent intent=new Intent();
                    intent.putExtra(ResultActivity.RESULT_KEY, tableOperation);

                    if (tableOperation.checkAnswers()) {
                        setResult(RESULT_OK,intent);
                    } else {
                        setResult(RESULT_CANCELED,intent);
                    }
                } else if (context.compareTo("graded")==0) {
                    Intent intent=new Intent();
                    User user = getIntent().getParcelableExtra(USER_KEY);
                    intent.putExtra(MathActivity.RESULT_TABLE_KEY, tableOperation);
                    if (tableOperation.checkAnswers()) {
                        setResult(RESULT_OK,intent);
                    } else {
                        setResult(RESULT_CANCELED,intent);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Une erreur s'est produite : le context d'utilisation est pas ou mal définit", Toast.LENGTH_LONG).show();
                    Intent intent=new Intent();
                    intent.putExtra(MathActivity.RESULT_TABLE_KEY, tableOperation);
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
        container.removeAllViews();
        for (LinearLayout multiplication : operationsList) {
            container.addView(multiplication);
        }
    }

    /**
     * Permet de mettre à jour la liste de question, et de champs de saisie de réponse en fonction de la table mis en paramètre.
     * Ces question et champs sont récupérés à partir de activity_question_answer.
     * @param table la table de multiplication sur laquelle on se basera pour mettre à jour le contenu de la page
     */
    private void updatePageContent(TableOperation table) {
        for (int i = 0; i<table.getNbOperation(); i++) {
            LinearLayout container = (LinearLayout) getLayoutInflater().inflate(R.layout.activity_question_answer, null);
            TextView question = container.findViewById(R.id.question_sample);
            EditText reponse = container.findViewById(R.id.answer_sample);

            question.setText(table.getOperation(i).getQuestion());
            if (table.getOperation(i).hasUserResponded()) {
                //pour une raison totalement inconnue, le setText ne fonctionne pas UNIQUEMENT dans le cas d'un "onsavedinstance".
                //ce code fonctionne très bien quand on quitte l'activité et qu'on renviens dessus (cas d'une correction par exemple)
                //utilisation d'un setInt pour la démo, pour montrer que la sauvegarde des réponse fonctionne au passage en paysage (qui lui fonctionne très bien par contre)
                reponse.setText(table.getOperation(i).getReponseUtilisateur().toString());
                reponse.setHint(table.getOperation(i).getReponseUtilisateur().toString());
            }
            operationsList.add(container);
        }
    }

    /**
     * Permet de mettre à jour les réponses de l'utilisateur dans le modèle en fonction de ce qu'il a saisie
     * @param table correspond à la table à modifier
     */
    private void updateUserResponses(TableOperation table) {
        for (int i = 0; i< operationsList.size(); i++) {
            EditText reponseEdit = operationsList.get(i).findViewById(R.id.answer_sample);
            String reponse = String.valueOf(reponseEdit.getText());
            if (!reponse.isEmpty()) {
                table.getOperation(i).repondre(Integer.parseInt(reponse));
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        updateUserResponses(tableOperation);
        // Save the user's current game state
        savedInstanceState.putParcelable(STATE_TABLE, tableOperation);

        // Always call the superclass so it can save
        // the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

}
