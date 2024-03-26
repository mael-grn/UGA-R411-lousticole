package com.garnmael.lousticole;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.garnmael.lousticole.model.User;

public class HelpActivity extends AppCompatActivity {

    public static final String SUBJECT_KEY = "context_key";
    public static final String USER_KEY = "user_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);

        //elements de l'interface
        LinearLayout container = findViewById(R.id.help_container_layout);
        Button backButton = findViewById(R.id.help_retour_button);
        TextView content = new TextView(getApplicationContext());

        String subject = getIntent().getStringExtra(SUBJECT_KEY);
        User user = getIntent().getParcelableExtra(USER_KEY);

        //si la page d'aide correspond à la matière mathematiques
        if (subject.compareTo("m") == 0) {
            content.setText("C'est un jeu où tu choisis une table de multiplication, d'addition ou de soustraction. Tu peux t'entraîner ou faire un exercice noté. Si tu veux une note, tu dois te connecter avant de commencer. À la fin, tu peux voir ta note sur la page \"note\". Amuse-toi bien !");
            container.addView(content);
            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(HelpActivity.this, MathActivity.class);
                    intent.putExtra(MathActivity.USER_KEY, user);
                    startActivity(intent);
                }
            });
            //si la page d'aide correspond à la matière francais
        } else if (subject.compareTo("f") == 0) {
            content.setText("C'est comme un petit jeu où tu réponds à des questions sur le français. Tu as deux options : faire un entraînement pour t'améliorer ou faire un QCM noté. Pour avoir une note, tu dois te connecter avant de commencer le QCM. Ensuite, tu pourras voir ta note sur la page \"note\" après avoir terminé. Amuse-toi bien à jouer avec les mots !");
            container.addView(content);
            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(HelpActivity.this, FrancaisActivity.class);
                    intent.putExtra(MathActivity.USER_KEY, user);
                    startActivity(intent);
                }
            });
        }


    }
}
