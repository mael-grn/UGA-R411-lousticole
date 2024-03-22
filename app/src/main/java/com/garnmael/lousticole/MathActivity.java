package com.garnmael.lousticole;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.garnmael.lousticole.model.TableMultiplication;
import com.garnmael.lousticole.model.User;

public class MathActivity extends AppCompatActivity {

    public static final String RESULT_TABLE_KEY = "result_table_key";

    public static final String RESULT_NOTE_KEY = "result_note_key";

    public static final String USER_KEY = "user_key";

    private String context;

    //la table de multiplication selectionnée
    private int tableSelected;

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
                            CharSequence text = "Vous avez quitté sans valider, les réponses de sont pas sauvegardées";
                            int duration = Toast.LENGTH_SHORT;
                            Toast.makeText(MathActivity.this, text, duration).show();
                            //si l'utilisateur à perdu
                        } else {
                            CharSequence text = "PERDU";
                            int duration = Toast.LENGTH_SHORT;
                            Toast.makeText(MathActivity.this, text, duration).show();

                            Intent resultIntent = result.getData();
                            TableMultiplication tableMult = resultIntent.getParcelableExtra(RESULT_TABLE_KEY);

                            //on le redirige vers la page appriprié
                            Intent intent = new Intent(MathActivity.this, ResultActivity.class);
                            intent.putExtra(ResultActivity.RESULT_TABLE_KEY, tableMult);
                            intent.putExtra(ResultActivity.CONTEXT_KEY, context);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                            startActivity(intent);
                        }
                        //si l'utilisateur a gagné, on le redirige vers la page de resultat
                    } else if (result.getResultCode() == RESULT_OK) {
                        CharSequence text = "GAGNE";
                        int duration = Toast.LENGTH_SHORT;
                        Toast.makeText(MathActivity.this, text, duration).show();

                        Intent resultIntent = result.getData();
                        TableMultiplication tableMult = resultIntent.getParcelableExtra(RESULT_TABLE_KEY);

                        Intent intent = new Intent(MathActivity.this, ResultActivity.class);
                        intent.putExtra(ResultActivity.RESULT_TABLE_KEY, tableMult);
                        intent.putExtra(ResultActivity.CONTEXT_KEY, context);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        startActivity(intent);
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // On charge le XML pour créer l'arbre graphique
        setContentView(R.layout.table_multiplication_launcher);

        NumberPicker numberPicker = findViewById(R.id.tabMultLaunch_number_picker);
        Button entrainementButton = findViewById(R.id.tabMultLaunch_train_button);
        Button exerciceButton = findViewById(R.id.tabMultLaunch_graded_button);
        Button retourButton = findViewById(R.id.tabMultLaunch_back_button);


        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(9);

        User user = getIntent().getParcelableExtra(USER_KEY);

        if (user.isAnonyme()) {
            exerciceButton.setEnabled(false);
        }

        retourButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MathActivity.this, DashboardActivity.class);
            intent.putExtra(DashboardActivity.USER_KEY, user);
            startActivity(intent);
        }
        });


        //lancement de l'activité de multiplication
        entrainementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context = "entrainement";
                tableSelected = numberPicker.getValue();
                Intent intent = new Intent(MathActivity.this, TableMultiplicationActivity.class);
                TableMultiplication tableMultiplication = new TableMultiplication(tableSelected);
                intent.putExtra(TableMultiplicationActivity.TABLE_KEY, tableMultiplication);
                intent.putExtra(TableMultiplicationActivity.CONTEXT_KEY, context);
                activityResultLauncher.launch(intent);
            }
        });

        //lancement de l'activité de multiplication dans le cas ou l'exercice est noté
        exerciceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context = "note";
                tableSelected = numberPicker.getValue();
                Intent intent = new Intent(MathActivity.this, TableMultiplicationActivity.class);
                TableMultiplication tableMultiplication = new TableMultiplication(tableSelected);
                intent.putExtra(TableMultiplicationActivity.TABLE_KEY, tableMultiplication);
                User user = getIntent().getParcelableExtra(USER_KEY);
                intent.putExtra(TableMultiplicationActivity.CONTEXT_KEY, context);
                intent.putExtra(TableMultiplicationActivity.USER_KEY, user);
                activityResultLauncher.launch(intent);
            }
        });
    }
}
