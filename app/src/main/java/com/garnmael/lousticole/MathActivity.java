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

public class MathActivity extends AppCompatActivity {

    public static final String RESULT_TABLE_KEY = "result_table_key";
    private int tableSelected;

    private ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_CANCELED) {
                        if (result.getData() == null) {
                            CharSequence text = "Vous avez quitté sans valider, les réponses de sont pas sauvegardées";
                            int duration = Toast.LENGTH_SHORT;
                            Toast.makeText(MathActivity.this, text, duration).show();
                        } else {
                            CharSequence text = "PERDU";
                            int duration = Toast.LENGTH_SHORT;
                            Toast.makeText(MathActivity.this, text, duration).show();

                            Intent resultIntent = result.getData();
                            TableMultiplication tableMult = resultIntent.getParcelableExtra(RESULT_TABLE_KEY);

                            Intent intent = new Intent(MathActivity.this, ResultActivity.class);
                            intent.putExtra(ResultActivity.RESULT_TABLE_KEY, tableMult);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                            startActivity(intent);
                        }
                    } else if (result.getResultCode() == RESULT_OK) {
                        CharSequence text = "GAGNE";
                        int duration = Toast.LENGTH_SHORT;
                        Toast.makeText(MathActivity.this, text, duration).show();

                        Intent resultIntent = result.getData();
                        TableMultiplication tableMult = resultIntent.getParcelableExtra(RESULT_TABLE_KEY);

                        Intent intent = new Intent(MathActivity.this, ResultActivity.class);
                        intent.putExtra(ResultActivity.RESULT_TABLE_KEY, tableMult);
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
        Button validateNumber = findViewById(R.id.tabMultLaunch_train_button);

        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(9);

        validateNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tableSelected = numberPicker.getValue();
                Intent intent = new Intent(MathActivity.this, TableMultiplicationActivity.class);
                TableMultiplication tableMultiplication = new TableMultiplication(tableSelected);
                intent.putExtra(TableMultiplicationActivity.TABLE_KEY, tableMultiplication);
                intent.putExtra(TableMultiplicationActivity.CONTEXT_KEY, "entrainement");
                activityResultLauncher.launch(intent);
            }
        });
    }
}
