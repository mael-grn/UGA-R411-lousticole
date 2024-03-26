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
import com.garnmael.lousticole.model.Qcm;
import com.garnmael.lousticole.model.User;

import java.util.Collections;
import java.util.List;

public class HistoireActivity extends AppCompatActivity {

    public static final String USER_KEY = "user_key";

    public static final String QCM_KEY = "qcm_key";

    public static final String CONTEXT_KEY = "context_key";

    private DatabaseClient mDb;

    /**
     * Appelé au resultat de l'activité Qcm, de type francais.
     * En fonction du résultat de l'utilisateur, le redirige vers la page de resultat avec un message
     * approprié
     */
    private ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {

                //resultat de l'activité de multiplication.
                //selon le resultat, on decidera d'envoyer l'utilisateur vers la page de resultat
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_CANCELED) {
                        //si l'utilisateur à quitté avec la navigation
                        if (result.getData() == null) {
                            Toast.makeText(getApplicationContext(), "Vous avez quitté sans valider, les réponses de sont pas sauvegardées", Toast.LENGTH_LONG).show();
                        }
                        //si l'utilisateur a gagné, on le redirige vers la page de resultat
                    } else if (result.getResultCode() == RESULT_OK) {
                        Intent resultIntent = result.getData();

                        ExerciceQcm exerciceQcm = resultIntent.getParcelableExtra(QCM_KEY);
                        String context = resultIntent.getStringExtra(CONTEXT_KEY);
                        User user = getIntent().getParcelableExtra(USER_KEY);
                        Intent intent = new Intent(HistoireActivity.this, ResultActivity.class);
                        intent.putExtra(ResultActivity.RESULT_KEY, exerciceQcm);
                        intent.putExtra(ResultActivity.CONTEXT_KEY, context);
                        intent.putExtra(ResultActivity.USER_KEY, user);
                        intent.putExtra(ResultActivity.SUBJECT_KEY, "h");
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        startActivity(intent);
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qcm_launcher);
        mDb = DatabaseClient.getInstance(getApplicationContext());
        User user = getIntent().getParcelableExtra(USER_KEY);

        //elements d'interface
        Button trainingButton = findViewById(R.id.francais_launcher_entrainement_button);
        Button gradedButton = findViewById(R.id.francais_launcher_note_button);
        Button helpButton = findViewById(R.id.francais_launcher_help_button);
        Button backButton = findViewById(R.id.francais_launcher_retour_button);
        TextView titleTextView = findViewById(R.id.francais_launcher_welcome_textView);
        titleTextView.setText("histoire");

        if (user.isAnonyme()) {
            gradedButton.setEnabled(false);
        }

        //click sur le bouton d'entrainement
        trainingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getQcm("training");
            }
        });

        //click sur le bouton d'exercice noté
        gradedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getQcm("graded");
            }
        });

        //click sur le bouton retours
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HistoireActivity.this, DashboardActivity.class);
                intent.putExtra(DashboardActivity.USER_KEY, user);
                startActivity(intent);
            }
        });

        //clique sur le bouton aide
        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HistoireActivity.this, HelpActivity.class);
                intent.putExtra(HelpActivity.USER_KEY, user);
                intent.putExtra(HelpActivity.SUBJECT_KEY, "h");
                startActivity(intent);
            }
        });
    }


    /**
     * Permet de recuperer les QCMs dans la base de données pour la matière francais, puis lance la
     * page d'exercice
     * @param context corresponds au contexte de l'exercice. Peut etre "graded" ou "training"
     */
    private void getQcm(String context) {
        ///////////////////////
        // Classe asynchrone permettant de récupérer des taches et de mettre à jour le listView de l'activité

        class GetQcm extends AsyncTask<Void, Void, List<Qcm>> {

            @Override
            protected List<Qcm> doInBackground(Void... voids) {
                return mDb.getAppDatabase()
                        .qcmDao()
                        .getQcmFromTag("histoire");
            }

            @Override
            protected void onPostExecute(List<Qcm> qcmList) {
                super.onPostExecute(qcmList);
                System.out.println(qcmList.size()+"af,aelkgfnklazflkaegklaeznglkagjazngleanglkjegkjle");
                Collections.shuffle(qcmList);
                ExerciceQcm exerciceQcm = new ExerciceQcm(qcmList, "histoire");
                User user = getIntent().getParcelableExtra(USER_KEY);

                Intent intent = new Intent(HistoireActivity.this, QcmActivity.class);
                intent.putExtra(QcmActivity.QCM_KEY, exerciceQcm);
                intent.putExtra(QcmActivity.USER_KEY, user);
                intent.putExtra(QcmActivity.CONTEXT_KEY, context);
                intent.putExtra(QcmActivity.SUBJECT_KEY, "h");
                activityResultLauncher.launch(intent);
            }
        }
        //////////////////////////
        // IMPORTANT bien penser à executer la demande asynchrone
        // Création d'un objet de type GetTasks et execution de la demande asynchrone
        GetQcm gq = new GetQcm();
        gq.execute();
    }
}
