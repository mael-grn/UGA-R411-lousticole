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

    public static final String CONTEXT_KEY = "context_key";
    public static final String USER_KEY = "user_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);

        LinearLayout container = findViewById(R.id.help_container_layout);
        Button retour = findViewById(R.id.help_retour_button);

        TextView content = new TextView(getApplicationContext());

        String context = getIntent().getStringExtra(CONTEXT_KEY);
        User user = getIntent().getParcelableExtra(USER_KEY);

        if (context.compareTo("math") == 0) {
            content.setText("Sur la page mathématiques, vous pouvez vous entrainer, ou faire un exercice noté. Dans les deux cas, vous devrez remplir une table de multiplication dont vous aurez au préalable choisi le multipliant. Pour les exercices notés, ceux ci sont eccessibles seulement pour les utilisateurs connectés, qui pourront retrouver leurs notes dans l'onglet note sur le dashboard.");
            container.addView(content);
            retour.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(HelpActivity.this, MathActivity.class);
                    intent.putExtra(MathActivity.USER_KEY, user);
                    startActivity(intent);
                }
            });
        }


    }
}
