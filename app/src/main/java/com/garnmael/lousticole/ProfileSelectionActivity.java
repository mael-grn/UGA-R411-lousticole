package com.garnmael.lousticole;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.garnmael.lousticole.db.DatabaseClient;
import com.garnmael.lousticole.model.User;

public class ProfileSelectionActivity extends AppCompatActivity {

    private DatabaseClient mDb;

    public static final String USER_KEY = "user_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.profil_selection);

        mDb = DatabaseClient.getInstance(getApplicationContext());

        Button validerButton = findViewById(R.id.profile_selection_valider_button);
        Button resetButton = findViewById(R.id.profile_selection_reset_button);

        //on recupere l'objet utilisateur. Utile par exemple si le context d'utilisation est un changement de profil
        User user = getIntent().getParcelableExtra(USER_KEY);

        //edit text de saisie utilisateur
        EditText prenomEditText = findViewById(R.id.profile_selection_prenom_editText);
        EditText nomEditText = findViewById(R.id.profile_selection_nom_editText);

        //cas ou l'utilisateur souhaite changer de nom/prenom
        if (user != null) {
            prenomEditText.setText(user.getPrenom());
            nomEditText.setText(user.getNom());
            validerButton.setText("Changer mon prenom / nom");
        }

        //on change le libelle des boutons en fonction de si l'utilisateur à rentré du texte ou non
        prenomEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!nomEditText.getText().toString().isEmpty() && !prenomEditText.getText().toString().isEmpty()) {
                    //si l'utilisateur se connecte, affichage du message approprié en fonction du contexte
                    if (user != null) {
                        validerButton.setText("Se connecter avec un autre compte");
                    } else {
                        validerButton.setText("Se connecter / S'enregistrer");
                    }
                } else {
                    validerButton.setText("Continer en anonyme");
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        //on change le libelle des boutons en fonction de si l'utilisateur à rentré du texte ou non
        nomEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!nomEditText.getText().toString().isEmpty() && !prenomEditText.getText().toString().isEmpty()) {
                    //si l'utilisateur se connecte, affichage du message approprié en fonction du contexte
                    if (user != null) {
                        validerButton.setText("Se connecter avec un autre compte");
                    } else {
                        validerButton.setText("Se connecter / S'enregistrer");
                    }
                } else {
                    validerButton.setText("Continer en anonyme");
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        //lorsque l'utilisateur souhaite en effet changer d'utilisateur
        validerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onValidateButton();
            }
        });

        /**
         * supprimer la base de données pour reinitialiser l'appliation
         */
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getApplicationContext().deleteDatabase("myLousticole");
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });
    }

    /**
     * fait un appel à la base de données pour sauvegarder un utilisateur
     * Si l'utilisateur est anonyme, alors il ne peut pas etre sauvegardé en BD : throw UserIsAnonymousException
     */
    private void saveUser(String nom, String prenom) {
        /**
         * Création d'une classe asynchrone pour sauvegarder l'utilsiateur
         */
        class SaveUser extends AsyncTask<Void, Void, User> {
            @Override
            protected User doInBackground(Void... voids) {
                //creer un utilisateur
                User user = new User(nom, prenom);
                // adding to database
                long id = mDb.getAppDatabase()
                        .userDao()
                        .insert(user);
                // mettre à jour l'id de l'utilisateur
                // Nécessaire si on souhaite avoir accès à l'id plus tard dans l'activité
                user.setId(id);
                return user;
            }
            @Override
            protected void onPostExecute(User user) {
                super.onPostExecute(user);
                // Quand la tache est créée, on arrête l'activité AddTaskActivity (on l'enleve de la pile d'activités)
                setResult(RESULT_OK);
                finish();

                Intent intent = new Intent(ProfileSelectionActivity.this, DashboardActivity.class);
                intent.putExtra(DashboardActivity.USER_KEY, user);
                startActivity(intent);
            }
        }

        //////////////////////////
        // IMPORTANT bien penser à executer la demande asynchrone
        SaveUser su = new SaveUser();
        su.execute();
    }

    /**
     * fait un appel à la base de données pour recuperer un utilisateur
     * Mets à jour la variable current user grace a la methode setUser
     * @param nom le nom de l'utilisateur
     * @param prenom le prenom de l'utilisateur
     */
    private void getUser(String nom, String prenom) {
        ///////////////////////
        // Classe asynchrone permettant de récupérer des taches et de mettre à jour le listView de l'activité

        class GetUser extends AsyncTask<Void, Void, User> {

            @Override
            protected User doInBackground(Void... voids) {
                return mDb.getAppDatabase()
                        .userDao()
                        .getUser(nom, prenom);
            }

            @Override
            protected void onPostExecute(User user) {
                super.onPostExecute(user);
                if (user == null) {
                    Toast.makeText(getApplicationContext(), "L'utilisateur existe pas", Toast.LENGTH_LONG).show();
                    saveUser(nom, prenom);

                } else {
                    Toast.makeText(getApplicationContext(), "L'utilisateur existe déjà : recuperation du compte", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(ProfileSelectionActivity.this, DashboardActivity.class);
                    intent.putExtra(DashboardActivity.USER_KEY, user);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        }
        //////////////////////////
        // IMPORTANT bien penser à executer la demande asynchrone
        // Création d'un objet de type GetTasks et execution de la demande asynchrone
        GetUser gu = new GetUser();
        gu.execute();
    }

    /**
     * permet de verifier les champs saisis par l'utilisateur, et de lancer l'activitée appropriée
     */
    private void onValidateButton() {
        EditText prenomEditText = findViewById(R.id.profile_selection_prenom_editText);
        EditText nomEditText = findViewById(R.id.profile_selection_nom_editText);

        String prenom = prenomEditText.getText().toString();
        String nom = nomEditText.getText().toString();

        if (!nom.isEmpty() && !prenom.isEmpty()) {
            getUser(nom, prenom);
        } else {
            Toast.makeText(getApplicationContext(), "L'utilisateur est anonyme", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(ProfileSelectionActivity.this, DashboardActivity.class);
            intent.putExtra(DashboardActivity.USER_KEY, new User());
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }
}
