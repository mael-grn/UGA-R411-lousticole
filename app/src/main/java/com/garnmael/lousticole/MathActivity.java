package com.garnmael.lousticole;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.garnmael.lousticole.model.TableOperation;
import com.garnmael.lousticole.model.User;

public class MathActivity extends AppCompatActivity {

    public static final String RESULT_TABLE_KEY = "result_table_key";

    public static final String USER_KEY = "user_key";

    private String context;

    //la table de multiplication selectionnée
    private int tableSelected;

    //l'operateur selectionné par l'eleve. + par défaut
    private char operateurSelected = '+';

    /**
     * résultat de l'activié table d'opération.
     * En fonction de la réussite ou non de l'eleve et du contexte, redirige vers la page de
     * resultat avec un message approprié
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
                            CharSequence text = "Réponses non sauvegardées";
                            int duration = Toast.LENGTH_SHORT;
                            Toast.makeText(MathActivity.this, text, duration).show();
                            //si l'utilisateur à perdu
                        } else {
                            User user = getIntent().getParcelableExtra(USER_KEY);

                            Intent resultIntent = result.getData();
                            TableOperation tableMult = resultIntent.getParcelableExtra(RESULT_TABLE_KEY);

                            //on le redirige vers la page appriprié
                            Intent intent = new Intent(MathActivity.this, ResultActivity.class);
                            intent.putExtra(ResultActivity.RESULT_KEY, tableMult);
                            intent.putExtra(ResultActivity.CONTEXT_KEY, context);
                            intent.putExtra(ResultActivity.USER_KEY, user);
                            intent.putExtra(ResultActivity.SUBJECT_KEY, "m");
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                            startActivity(intent);
                        }
                        //si l'utilisateur a gagné, on le redirige vers la page de resultat
                    } else if (result.getResultCode() == RESULT_OK) {
                        User user = getIntent().getParcelableExtra(USER_KEY);

                        Intent resultIntent = result.getData();
                        TableOperation tableMult = resultIntent.getParcelableExtra(RESULT_TABLE_KEY);

                        Intent intent = new Intent(MathActivity.this, ResultActivity.class);
                        intent.putExtra(ResultActivity.RESULT_KEY, tableMult);
                        intent.putExtra(ResultActivity.CONTEXT_KEY, context);
                        intent.putExtra(ResultActivity.USER_KEY, user);
                        intent.putExtra(ResultActivity.SUBJECT_KEY, "m");
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
        setContentView(R.layout.table_operation_launcher);

        NumberPicker numberPicker = findViewById(R.id.tabMultLaunch_number_picker);
        Button trainingButton = findViewById(R.id.tabMultLaunch_train_button);
        Button gradedButton = findViewById(R.id.tabMultLaunch_graded_button);
        Button backButton = findViewById(R.id.tabMultLaunch_back_button);
        Button helpButton = findViewById(R.id.tabMultLaunch_help_button);
        RadioGroup radioGroup = findViewById(R.id.tabMultLaunch_radioGroup);

        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(9);

        User user = getIntent().getParcelableExtra(USER_KEY);

        //desactivation du bouton pour un exercice noté si l'utilisateur n'est pas connecté
        if (user.isAnonyme()) {
            gradedButton.setEnabled(false);
        }

        backButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MathActivity.this, DashboardActivity.class);
            intent.putExtra(DashboardActivity.USER_KEY, user);
            startActivity(intent);
        }
        });


        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MathActivity.this, HelpActivity.class);
                intent.putExtra(HelpActivity.USER_KEY, user);
                intent.putExtra(HelpActivity.SUBJECT_KEY, "m");
                startActivity(intent);
            }
        });

        //lancement de l'activité de multiplication
        trainingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context = "training";
                tableSelected = numberPicker.getValue();
                RadioButton selectedRadioButton = findViewById(radioGroup.getCheckedRadioButtonId());
                if (selectedRadioButton.getText().toString().compareTo("Addition")==0) {
                    operateurSelected = '+';
                } else if (selectedRadioButton.getText().toString().compareTo("Soustraction")==0) {
                    operateurSelected = '-';
                } else if (selectedRadioButton.getText().toString().compareTo("Multiplication")==0) {
                    operateurSelected = '*';
                }
                Intent intent = new Intent(MathActivity.this, TableOperationActivity.class);
                TableOperation tableOperation = new TableOperation(tableSelected, operateurSelected);
                intent.putExtra(TableOperationActivity.TABLE_KEY, tableOperation);
                intent.putExtra(TableOperationActivity.CONTEXT_KEY, context);
                activityResultLauncher.launch(intent);
            }
        });

        //lancement de l'activité de multiplication dans le cas ou l'exercice est noté
        gradedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context = "graded";
                tableSelected = numberPicker.getValue();
                RadioButton selectedRadioButton = findViewById(radioGroup.getCheckedRadioButtonId());
                if (selectedRadioButton.getText().toString().compareTo("Addition")==0) {
                    operateurSelected = '+';
                } else if (selectedRadioButton.getText().toString().compareTo("Soustraction")==0) {
                    operateurSelected = '-';
                } else if (selectedRadioButton.getText().toString().compareTo("Multiplication")==0) {
                    operateurSelected = '*';
                }
                Intent intent = new Intent(MathActivity.this, TableOperationActivity.class);
                TableOperation tableOperation = new TableOperation(tableSelected, operateurSelected);
                intent.putExtra(TableOperationActivity.TABLE_KEY, tableOperation);
                User user = getIntent().getParcelableExtra(USER_KEY);
                intent.putExtra(TableOperationActivity.CONTEXT_KEY, context);
                intent.putExtra(TableOperationActivity.USER_KEY, user);
                activityResultLauncher.launch(intent);
            }
        });
    }
}
