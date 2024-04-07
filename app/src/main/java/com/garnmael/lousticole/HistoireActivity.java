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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qcm_launcher);
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
                Toast.makeText(getApplicationContext(), "Bientôt disponible !", Toast.LENGTH_LONG).show();
            }
        });

        //click sur le bouton d'exercice noté
        gradedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Bientôt disponible !", Toast.LENGTH_LONG).show();
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
}
