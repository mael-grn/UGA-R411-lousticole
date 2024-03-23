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
import com.garnmael.lousticole.model.User;

import java.util.List;

public class SelectUserActivity extends AppCompatActivity {

    private DatabaseClient mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_user);

        Button retourButton = findViewById(R.id.select_user_retour_button);

        mDb = DatabaseClient.getInstance(getApplicationContext());

        retourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                setResult(RESULT_CANCELED,intent);
                finish();
            }
        });

        getUsers();
    }

    private void getUsers() {
        ///////////////////////
        // Classe asynchrone permettant de récupérer des taches et de mettre à jour le listView de l'activité

        class GetUsers extends AsyncTask<Void, Void, List<User>> {

            @Override
            protected List<User> doInBackground(Void... voids) {
                return mDb.getAppDatabase()
                        .userDao()
                        .getAll();
            }

            @Override
            protected void onPostExecute(List<User> users) {
                super.onPostExecute(users);

                //linear layout qui va contenir les boutons des utilisateurs
                LinearLayout container = findViewById(R.id.select_user_container_linearLayout);

                //on supprime le premier element, qui est un utilisateur par défaut pour un raison inexpliquable
                users.remove(0);

                //pour chaques utilisateur existant, un créée un bouton
                //ce bouton à un onclick listener, qui va retourner l'utilisateur à la fin de l'activité
                for (int i=0; i< users.size(); i++) {
                    System.out.println(users.get(i).getNom());
                    Button button = new Button(getApplicationContext());
                    String text = users.get(i).getPrenom() + " " + users.get(i).getNom();
                    button.setText(text);
                    button.setWidth(300);
                    int finalI = i;
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Intent intent=new Intent();
                            intent.putExtra(ProfileSelectionActivity.USER_KEY, users.get(finalI));
                            setResult(RESULT_OK,intent);
                            finish();

                        }
                    });
                    container.addView(button);
                }
                if (users.isEmpty()) {
                    TextView textView = new TextView(getApplicationContext());
                    textView.setText("Aucun utilisateur");
                    container.addView(textView);
                }
            }
        }
        //////////////////////////
        // IMPORTANT bien penser à executer la demande asynchrone
        // Création d'un objet de type GetTasks et execution de la demande asynchrone
        GetUsers gu = new GetUsers();
        gu.execute();
    }
}
