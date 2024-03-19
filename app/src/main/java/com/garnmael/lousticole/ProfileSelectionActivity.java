package com.garnmael.lousticole;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.garnmael.lousticole.model.User;

public class ProfileSelectionActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.profil_selection);

        Button validerButton = findViewById(R.id.profile_selection_valider_button);

        validerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendForm();
            }
        });
    }

    /**
     * permet de verifier les champs saisis par l'utilisateur, et de lancer l'activitée appropriée
     */
    private void sendForm() {
        EditText prenomEditText = findViewById(R.id.profile_selection_prenom_editText);
        EditText nomEditText = findViewById(R.id.profile_selection_nom_editText);

        String prenom = prenomEditText.getText().toString();
        String nom = nomEditText.getText().toString();

        User user = new User(nom, prenom);

        if (user.alreadyExistInDb()) {
            prenomEditText.setText("erreur");
            nomEditText.setText("");
        } else {
            Intent intent = new Intent(ProfileSelectionActivity.this, DashboardActivity.class);
            intent.putExtra(DashboardActivity.USER_KEY, user);
            startActivity(intent);
        }
    }
}
