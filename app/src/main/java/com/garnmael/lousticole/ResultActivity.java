package com.garnmael.lousticole;

import android.content.Intent;
import android.os.AsyncTask;
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

import com.garnmael.lousticole.db.DatabaseClient;
import com.garnmael.lousticole.model.ExerciceQcm;
import com.garnmael.lousticole.model.Note;
import com.garnmael.lousticole.model.TableOperation;
import com.garnmael.lousticole.model.User;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ResultActivity extends AppCompatActivity {
    //corresponds à un objet de type exercice QCM ou TableOperation, selon la matière
    public static final String RESULT_KEY = "result_key";

    public static final String USER_KEY = "user_key";
    public static final String CONTEXT_KEY = "context_key";
    public static final String SUBJECT_KEY = "matiere_key";
    private DatabaseClient mDb;


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
                            CharSequence text = "Reponses non sauvegardées";
                            int duration = Toast.LENGTH_SHORT;
                            Toast.makeText(ResultActivity.this, text, duration).show();
                        }

                        //cas dans lequel l'utilisateur à perdu
                        else {

                            //récuperation de la table de multiplication
                            Intent resultIntent = result.getData();
                            TableOperation tableOperation = resultIntent.getParcelableExtra(RESULT_KEY);

                            //mise à jour de l'interface
                            updateUserInterface(tableOperation.checkAnswers(), tableOperation.getNbOperation() - tableOperation.getNbErrors() + "/" + tableOperation.getNbOperation());
                        }
                    }

                    //cas dans lequel l'utilisateur à gagné
                    else if (result.getResultCode() == RESULT_OK) {

                        //récuperation de la table de multiplication
                        Intent resultIntent = result.getData();
                        TableOperation tableOperation = resultIntent.getParcelableExtra(RESULT_KEY);

                        //mise à jour de l'interface
                        updateUserInterface(tableOperation.checkAnswers(), tableOperation.getNbOperation() - tableOperation.getNbErrors() + "/" + tableOperation.getNbOperation());
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDb = DatabaseClient.getInstance(getApplicationContext());

        // On charge le XML pour créer l'arbre graphique
        setContentView(R.layout.result);
        Button correctionButton = findViewById(R.id.result_correction_button);
        Button otherButton = findViewById(R.id.result_other_button);
        Button backButton = findViewById(R.id.result_retour_button);
        String context = getIntent().getStringExtra(CONTEXT_KEY);
        String subject = getIntent().getStringExtra(SUBJECT_KEY);
        User user = getIntent().getParcelableExtra(USER_KEY);




        //cas dans lequel le resultat est celui d'un exercice de math
        if (subject.compareTo("m")==0) {

            //on initialise tableMultiplication à partir de l'intent
            TableOperation tableOperation = getIntent().getParcelableExtra(RESULT_KEY);

            //indique si l'utilisateur à réussi l'exercice
            boolean success = tableOperation.checkAnswers();

            //On mets à jour les données de l'interface
            updateUserInterface(success, tableOperation.getNbOperation() - tableOperation.getNbErrors() + "/" + tableOperation.getNbOperation());

            if (context.compareTo("graded") == 0) {
                long note = tableOperation.getNbOperation() - tableOperation.getNbErrors();
                saveNote(note, user.getId(), "Mathématiques");
                correctionButton.setEnabled(false);
            }
            //mise à jour de l'action du premier bouton : choisir une autre table
            otherButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ResultActivity.this, MathActivity.class);
                    intent.putExtra(MathActivity.USER_KEY, user);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    finish();
                    startActivity(intent);
                }
            });
            //mise à jour de l'action du premier bouton : corriger
            correctionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ResultActivity.this, TableOperationActivity.class);
                    intent.putExtra(TableOperationActivity.TABLE_KEY, tableOperation);
                    intent.putExtra(TableOperationActivity.CONTEXT_KEY, "correction");
                    intent.putExtra(TableOperationActivity.USER_KEY, user);

                    activityResultLauncher.launch(intent);
                }
            });
        } else if (subject.compareTo("f")== 0) {
            correctionButton.setEnabled(false);

            ExerciceQcm exerciceQcm = getIntent().getParcelableExtra(RESULT_KEY);

            updateUserInterface(exerciceQcm.getNote()>=7, exerciceQcm.getNote()+"/10");

            if (context.compareTo("graded") == 0) {

                saveNote(exerciceQcm.getNote(), user.getId(), "Français");
                correctionButton.setEnabled(false);
            }

            //mise à jour de l'action du premier bouton : choisir un autre QCM
            otherButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ResultActivity.this, FrancaisActivity.class);
                    intent.putExtra(HistoireActivity.USER_KEY, user);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            });
        } else if (subject.compareTo("h")== 0) {
            correctionButton.setEnabled(false);

            ExerciceQcm exerciceQcm = getIntent().getParcelableExtra(RESULT_KEY);

            updateUserInterface(exerciceQcm.getNote()>=7, exerciceQcm.getNote()+"/10");

            if (context.compareTo("graded") == 0) {

                saveNote(exerciceQcm.getNote(), user.getId(), "Histoire");
                correctionButton.setEnabled(false);
            }

            //mise à jour de l'action du premier bouton : choisir un autre QCM
            otherButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ResultActivity.this, HistoireActivity.class);
                    intent.putExtra(HistoireActivity.USER_KEY, user);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            });
        }

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResultActivity.this, DashboardActivity.class);
                intent.putExtra(DashboardActivity.USER_KEY, user);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    /**
     * permet de mettre à jour les données sur l'interface utilisateur
     * @param success indique si l'utilisateur à réussi l'exercice
     * @param message le message principal à afficher sur l'accueil
     */
    private void updateUserInterface(boolean success, String message) {

        //recupération des elements de l'interface
        TextView resultText = findViewById(R.id.result_first_textView);
        TextView noteText = findViewById(R.id.result_second_textView);

        //si l'utilisateur à réussi
        if (success) {
            //mise à jour des message de l'UI
            resultText.setText("Félicitation !!");
        }
        //si l'utilisateur n'a pas réussi l'exercice
        else {
            //mise à jour des message de l'UI
            resultText.setText("Erreur(s) !!");
        }
        String text = message;
        noteText.setText(text);
    }


    /**
     * permets de sauvegarder une note dans la base de données
     * @param userNote la note de l'utilisateur
     * @param userId l'id de l'utilisateur dont on souhaite sauvegarder la note
     * @param subject la matière de la note
     */
    private void saveNote(long userNote, long userId, String subject) {
        /**
         * Création d'une classe asynchrone pour sauvegarder l'utilsiateur
         */
        class SaveNote extends AsyncTask<Void, Void, Note> {
            @Override
            protected Note doInBackground(Void... voids) {
                // Obtenir une instance de Calendar pour la date actuelle
                Calendar calendar = Calendar.getInstance();

                // Formater la date
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                String dateStr = dateFormat.format(calendar.getTime());
                //creer un utilisateur
                Note note = new Note(subject, userNote, userId, dateStr);
                // adding to database
                long id = mDb.getAppDatabase()
                        .noteDao()
                        .insert(note);
                // mettre à jour l'id de l'utilisateur
                // Nécessaire si on souhaite avoir accès à l'id plus tard dans l'activité
                note.setId(id);
                return note;
            }
            @Override
            protected void onPostExecute(Note note) {
                super.onPostExecute(note);

                Toast.makeText(getApplicationContext(), "Note sauvegardée", Toast.LENGTH_LONG).show();
            }
        }

        //////////////////////////
        // IMPORTANT bien penser à executer la demande asynchrone
        SaveNote sn = new SaveNote();
        sn.execute();
    }
}
