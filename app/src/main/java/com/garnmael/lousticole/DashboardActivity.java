package com.garnmael.lousticole;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.garnmael.lousticole.model.User;

public class DashboardActivity extends AppCompatActivity {

    public static final String USER_KEY = "user_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        User user = getIntent().getParcelableExtra(USER_KEY);

        //les elements de l'interface
        TextView welcomeText = findViewById(R.id.dashboard_welcome_textView);
        Button gradesButton = findViewById(R.id.dashboard_grades_button);
        Button mathButton = findViewById(R.id.dashboard_math_button);
        Button francaisButton = findViewById(R.id.dashboard_francais_button);
        Button histoireButton = findViewById(R.id.dashboard_histoire_button);
        Button logoutButton = findViewById(R.id.dashboard_settings_button);

        //si l'utilisateur est annonyme, desactivation de certaines fonctionnalitées
        if (user.isAnonyme()) {
            gradesButton.setEnabled(false);
            logoutButton.setText("Se connecter");
            welcomeText.setText("Dashboard (anonyme)");
        } else {
            welcomeText.setText("Dashboard de " + user.getPrenom());
        }

        //action du bouton de deconnexion
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, ProfileSelectionActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //action du bouton pour afficher les notes
        gradesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, NoteActivity.class);
                intent.putExtra(NoteActivity.USER_KEY, user);
                startActivity(intent);
            }
        });

        //action de click sur le bouton d'exercices de math
        mathButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, MathActivity.class);
                intent.putExtra(MathActivity.USER_KEY, user);
                startActivity(intent);
            }
        });

        //action de click sur le bouton d'exercice de francais
        francaisButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, FrancaisActivity.class);
                intent.putExtra(FrancaisActivity.USER_KEY, user);
                startActivity(intent);
            }
        });

        //action de click sur le bouton d'exercice d'histoire
        histoireButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, HistoireActivity.class);
                intent.putExtra(HistoireActivity.USER_KEY, user);
                startActivity(intent);
            }
        });
    }
}
