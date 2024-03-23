package com.garnmael.lousticole;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.garnmael.lousticole.db.DatabaseClient;
import com.garnmael.lousticole.model.Note;
import com.garnmael.lousticole.model.User;

import java.util.List;

public class NoteActivity extends AppCompatActivity {
    public static final String USER_KEY = "user_key";

    private DatabaseClient mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.grades);

        Button retour = findViewById(R.id.grades_retour_button);
        User user = getIntent().getParcelableExtra(USER_KEY);
        mDb = DatabaseClient.getInstance(getApplicationContext());

        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NoteActivity.this, DashboardActivity.class);
                intent.putExtra(DashboardActivity.USER_KEY, user);
                startActivity(intent);
            }
        });
        try {
            updateNotesContent(user.getId());
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Aucun id, recupération des notes impossible", Toast.LENGTH_LONG).show();
        }
    }

    private void updateNotesContent(long userId) {
        ///////////////////////
        // Classe asynchrone permettant de récupérer des taches et de mettre à jour le listView de l'activité

        class GetNotes extends AsyncTask<Void, Void, List<Note>> {

            @Override
            protected List<Note> doInBackground(Void... voids) {
                return mDb.getAppDatabase()
                        .noteDao()
                        .getNotesFromUser(userId);
            }

            @Override
            protected void onPostExecute(List<Note> notes) {
                super.onPostExecute(notes);
                LinearLayout notesContainer = findViewById(R.id.grades_notes_container);
                if (notes == null || notes.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Aucune notes récupérées", Toast.LENGTH_LONG).show();
                    TextView message = new TextView(getApplicationContext());
                    message.setText("Vous n'avez aucune notes pour le moment");
                    notesContainer.addView(message);
                } else {
                    Toast.makeText(getApplicationContext(), "Les notes ont bien été récupérées", Toast.LENGTH_LONG).show();
                    for (int i=0; i<notes.size(); i++) {
                        String text = notes.get(i).getExercice() + " - " + notes.get(i).getDate() + " : " + notes.get(i).getNote() + "/10";
                        TextView message = new TextView(getApplicationContext());
                        message.setText(text);
                        notesContainer.addView(message);
                    }
                }
            }
        }
        //////////////////////////
        // IMPORTANT bien penser à executer la demande asynchrone
        // Création d'un objet de type GetTasks et execution de la demande asynchrone
        GetNotes gn = new GetNotes();
        gn.execute();
    }

}
