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
import com.garnmael.lousticole.model.Note;
import com.garnmael.lousticole.model.TableOperation;
import com.garnmael.lousticole.model.User;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ResultActivity extends AppCompatActivity {
    public static final String RESULT_TABLE_KEY = "table_key";
    public static final String CONTEXT_KEY = "context_key";
    private DatabaseClient mDb;

    public static final String USER_KEY = "user_key";


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
                            TableOperation tableOperation = resultIntent.getParcelableExtra(RESULT_TABLE_KEY);

                            //mise à jour de l'interface
                            updateUserInterface(tableOperation.checkAnswers(), tableOperation);
                        }
                    }

                    //cas dans lequel l'utilisateur à gagné
                    else if (result.getResultCode() == RESULT_OK) {

                        //récuperation de la table de multiplication
                        Intent resultIntent = result.getData();
                        TableOperation tableOperation = resultIntent.getParcelableExtra(RESULT_TABLE_KEY);

                        //mise à jour de l'interface
                        updateUserInterface(tableOperation.checkAnswers(), tableOperation);
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
        Button retourButton = findViewById(R.id.result_retour_button);
        String context = getIntent().getStringExtra(CONTEXT_KEY);
        User user = getIntent().getParcelableExtra(USER_KEY);


        //on initialise tableMultiplication à partir de l'intent
        TableOperation tableOperation = getIntent().getParcelableExtra(RESULT_TABLE_KEY);

        //indique si l'utilisateur à réussi l'exercice
        boolean success = tableOperation.checkAnswers();

                //On mets à jour les données de l'interface
        updateUserInterface(success, tableOperation);


        if (context.compareTo("note") == 0) {
            long note = tableOperation.getNbOperation() - tableOperation.getNbErrors();
            saveNote(note, user.getId());
            correctionButton.setEnabled(false);
        }

        //mise à jour de l'action du premier bouton : choisir une autre table
        otherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResultActivity.this, MathActivity.class);
                intent.putExtra(MathActivity.USER_KEY, user);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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

        retourButton.setOnClickListener(new View.OnClickListener() {
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
     * @param tableOperation la table de mutliplication de l'exercice
     */
    private void updateUserInterface(boolean success, TableOperation tableOperation) {

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
        String text = tableOperation.getNbOperation() - tableOperation.getNbErrors() + "/" + tableOperation.getNbOperation();
        noteText.setText(text);
    }
    private void saveNote(long userNote, long userId) {
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
                Note note = new Note("mathématiques", userNote, userId, dateStr);
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
