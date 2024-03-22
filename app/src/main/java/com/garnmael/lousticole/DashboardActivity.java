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

        TextView welcomeText = findViewById(R.id.dashboard_welcome_textView);
        Button notesButton = findViewById(R.id.dashboard_grades_button);
        Button mathsButton = findViewById(R.id.dashboard_math_button);
        Button francaisButton = findViewById(R.id.dashboard_francais_button);
        Button profilButton = findViewById(R.id.dashboard_settings_button);


        if (user.isAnonyme()) {
            notesButton.setEnabled(false);
            profilButton.setText("Se connecter");
            welcomeText.setText("Bienvenue sur votre tableau de bord, chère anonyme");
        } else {
            welcomeText.setText("Bienvenue sur votre tableau de bord, " + user.getPrenom());
        }

        profilButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, ProfileSelectionActivity.class);
                if (!user.isAnonyme()) {
                    intent.putExtra(DashboardActivity.USER_KEY, user);
                }
                startActivity(intent);
            }
        });

        mathsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, MathActivity.class);
                intent.putExtra(MathActivity.USER_KEY, user);
                startActivity(intent);
            }
        });
    }
}
