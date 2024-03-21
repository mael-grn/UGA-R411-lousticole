package com.garnmael.lousticole;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.garnmael.lousticole.model.TableMultiplication;

public class ResultActivity extends AppCompatActivity {
    public static final String RESULT_TABLE_KEY = "table_key";

    /**
     * Permet de lancer une nouvelle page de table de mutliplication, pour une correction
     */
    private ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_CANCELED) {

                        //cas dans lequel l'utilisateur à appuyé sur le bouton retour de la barre de navigation
                        if (result.getData() == null) {
                            CharSequence text = "Vous avez quitté sans valider, les réponses de sont pas sauvegardées";
                            int duration = Toast.LENGTH_SHORT;
                            Toast.makeText(ResultActivity.this, text, duration).show();
                        }

                        //cas dans lequel l'utilisateur à perdu
                        else {
                            //affichage d'un message toast approprié
                            CharSequence text = "PERDU";
                            int duration = Toast.LENGTH_SHORT;
                            Toast.makeText(ResultActivity.this, text, duration).show();

                            //récuperation de la table de multiplication
                            Intent resultIntent = result.getData();
                            TableMultiplication tableMultiplication = resultIntent.getParcelableExtra(RESULT_TABLE_KEY);

                            //mise à jour de l'interface
                            updateUserInterface(tableMultiplication.checkAnswers(), tableMultiplication);
                        }
                    }

                    //cas dans lequel l'utilisateur à gagné
                    else if (result.getResultCode() == RESULT_OK) {
                        //affichage d'un message toast approprié
                        CharSequence text = "GAGNE";
                        int duration = Toast.LENGTH_SHORT;
                        Toast.makeText(ResultActivity.this, text, duration).show();

                        //récuperation de la table de multiplication
                        Intent resultIntent = result.getData();
                        TableMultiplication tableMultiplication = resultIntent.getParcelableExtra(RESULT_TABLE_KEY);

                        //mise à jour de l'interface
                        updateUserInterface(tableMultiplication.checkAnswers(), tableMultiplication);
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // On charge le XML pour créer l'arbre graphique
        setContentView(R.layout.result);

        //on initialise tableMultiplication à partir de l'intent
        TableMultiplication tableMultiplication = getIntent().getParcelableExtra(RESULT_TABLE_KEY);
        //indique si l'utilisateur à réussi l'exercice
        boolean success = tableMultiplication.checkAnswers();

                //On mets à jour les données de l'interface
        updateUserInterface(success, tableMultiplication);
    }

    /**
     * permet de mettre à jour les données sur l'interface utilisateur
     * @param success indique si l'utilisateur à réussi l'exercice
     * @param tableMultiplication la table de mutliplication de l'exercice
     */
    private void updateUserInterface(boolean success, TableMultiplication tableMultiplication) {

        //recupération des elements de l'interface
        TextView resultText = findViewById(R.id.result_first_textView);
        TextView errorText = findViewById(R.id.result_second_textView);
        Button firstButton = findViewById(R.id.result_first_button);
        Button secondButton = findViewById(R.id.result_second_button);

        //si l'utilisateur à réussi
        if (success) {

            //mise à jour des message de l'UI
            resultText.setText("Félicitation !!");
            errorText.setText("");
            firstButton.setText("Choisir une autre table");
            secondButton.setText("Choisir un autre exercice");

            //mise à jour de l'action du premier bouton : choisir une autre table
            firstButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ResultActivity.this, MathActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            });

            //mise à jour de l'action du deuxieme bouton : choisir un autre exercice
            firstButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ResultActivity.this, MathActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            });
        }
        //si l'utilisateur n'a pas réussi l'exercice
        else {

            //mise à jour des message de l'UI
            resultText.setText("Erreur(s) !!");
            String errorMessage = "Nombre d'erreur(s) : " + tableMultiplication.getNbErrors();
            errorText.setText(errorMessage);
            firstButton.setText("Corriger mes réponses");
            secondButton.setText("Choisir une autre table");

            //mise à jour de l'action du premier bouton : corriger
            firstButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ResultActivity.this, TableMultiplicationActivity.class);
                    intent.putExtra(TableMultiplicationActivity.TABLE_KEY, tableMultiplication);
                    intent.putExtra(TableMultiplicationActivity.CONTEXT_KEY, "correction");

                    activityResultLauncher.launch(intent);
                }
            });

            //mise à jour de l'action du deuxieme bouton : choisir une autre table
            secondButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ResultActivity.this, MathActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            });
        }
    }
}
