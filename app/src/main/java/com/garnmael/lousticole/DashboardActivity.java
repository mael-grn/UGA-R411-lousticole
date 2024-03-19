package com.garnmael.lousticole;

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
        welcomeText.setText("Bienvenue sur votre tableau de bord, " + user.getPrenom());
    }
}
