package com.garnmael.lousticole.db;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.garnmael.lousticole.model.Qcm;

import java.util.ArrayList;
import java.util.List;

public class DataBaseInitializer {

    private List<Qcm> qcmList;

    private DatabaseClient mDb;

    public DataBaseInitializer(Context context) {
        mDb = DatabaseClient.getInstance(context);
        qcmList = new ArrayList<>();
        qcmList.add(new Qcm("francais", "Quel mot signifie le contraire de \"chaud\" ?", "Petit", "Rapide", "Froid"));
        qcmList.add(new Qcm("francais", "Quel est le pluriel de \"chien\" ?", "Chienes", "Chienne", "Chiens"));
        qcmList.add(new Qcm("francais", "Combien de lettres comporte l'alphabet français ?", "24", "28", "26"));
        qcmList.add(new Qcm("francais", "Quel est le féminin de \"chat\" ?", "Chienne", "Chattesse", "Chatte"));
        qcmList.add(new Qcm("francais", "Quel temps est utilisé pour une action qui se déroule actuellement ?", "Passé", "Futur", "Présent"));
        qcmList.add(new Qcm("francais", "Quelle est la fonction du sujet dans une phrase ?", "Donne le temps", "Donne le lieu", "Donne l'être ou la chose dont on parle"));
        qcmList.add(new Qcm("francais", "Quel est le pluriel de \"cheval\" ?", "Chevaux", "Chevales", "Chevaux"));
        qcmList.add(new Qcm("francais", "Quel est le féminin de \"acteur\" ?", "Actrice", "Acteuse", "Actrice"));
        qcmList.add(new Qcm("francais", "Quel est l'antonyme de \"joyeux\" ?", "Triste", "Ennuyé", "Triste"));
        qcmList.add(new Qcm("francais", "Quel est le synonyme de \"enfant\" ?", "Bambin", "Adolescent", "Bambin"));
        qcmList.add(new Qcm("francais", "Quel est le nom de la personne qui écrit des livres ?", "Auteur", "Compositeur", "Auteur"));
        qcmList.add(new Qcm("francais", "Quel est le pluriel de \"oiseau\" ?", "Oiseaux", "Oiselles", "Oiseaux"));
        qcmList.add(new Qcm("francais", "Quel est le mot qui signifie le contraire de \"grand\" ?", "Petit", "Énorme", "Petit"));
        qcmList.add(new Qcm("francais", "Quel est le nom du lieu où l'on cultive des légumes ?", "Champ", "Forêt", "Champ"));
        qcmList.add(new Qcm("francais", "Quel est le synonyme de \"heureux\" ?", "Content", "Malheureux", "Content"));
        qcmList.add(new Qcm("francais", "Quel est le verbe qui signifie \"arrêter de bouger\" ?", "Dormir", "Reposer", "Reposer"));
        qcmList.add(new Qcm("francais", "Quel est le pluriel de \"animal\" ?", "Animaux", "Animailes", "Animaux"));
        qcmList.add(new Qcm("francais", "Quel est le nom de l'endroit où l'on garde les livres à la bibliothèque ?", "Rayon", "Étagère", "Rayon"));
        qcmList.add(new Qcm("francais", "Quel est le féminin de \"garçon\" ?", "Fille", "Garcette", "Fille"));
        qcmList.add(new Qcm("francais", "Quel est le mot qui signifie le contraire de \"triste\" ?", "Joyeux", "Ennuyé", "Joyeux"));
        qcmList.add(new Qcm("francais", "Quel est le synonyme de \"apprendre\" ?", "Comprendre", "Étudier", "Étudier"));
        qcmList.add(new Qcm("francais", "Quel est le nom de la personne qui enseigne à l'école ?", "Professeur", "Instituteur", "Professeur"));
        qcmList.add(new Qcm("francais", "Quel est le mot qui signifie le contraire de \"devant\" ?", "Derrière", "À côté", "Derrière"));
        qcmList.add(new Qcm("francais", "Quel est le nom de la personne qui joue dans un film ?", "Acteur", "Cinéaste", "Acteur"));
        qcmList.add(new Qcm("francais", "Quel est le pluriel de \"table\" ?", "Tables", "Tableaux", "Tables"));

        qcmList.add(new Qcm("histoire", "A quelle date à débuté la révolution Française ?", "1553", "2019", "1789"));
        qcmList.add(new Qcm("histoire", "Qui était le roi de France lors de la Révolution française ?", "Louis XIV", "Napoléon Bonaparte", "Louis XVI"));
        qcmList.add(new Qcm("histoire", "Quel événement a marqué la fin de la Seconde Guerre mondiale en Europe ?", "La Bataille de Stalingrad", "Le Débarquement de Normandie", "La Capitulation de l'Allemagne nazie"));
        qcmList.add(new Qcm("histoire", "Quelle civilisation a construit les pyramides de Gizeh ?", "Les Romains", "Les Grecs", "Les Égyptiens"));
        qcmList.add(new Qcm("histoire", "Qui a découvert l'Amérique en 1492 ?", "Christophe Colomb", "Vasco de Gama", "Marco Polo"));
        qcmList.add(new Qcm("histoire", "Quelle est la capitale de la France ?", "Londres", "Rome", "Paris"));
        qcmList.add(new Qcm("histoire", "Quel est le nom du premier homme à avoir marché sur la Lune ?", "Neil Armstrong", "Buzz Aldrin", "Yuri Gagarin"));
        qcmList.add(new Qcm("histoire", "Quel était le nom du bateau sur lequel Christophe Colomb a navigué vers le Nouveau Monde ?", "Le Santa Maria", "L'Endeavour", "La Pinta"));
        qcmList.add(new Qcm("histoire", "Quel empereur romain a ordonné la construction du Colisée ?", "Auguste", "Néron", "Vespasien"));
        qcmList.add(new Qcm("histoire", "Quelle est la période historique souvent appelée 'Âge de Pierre' ?", "Le Paléolithique", "Le Néolithique", "L'Antiquité"));
        qcmList.add(new Qcm("histoire", "Quel événement marque le début de la Première Guerre mondiale ?", "L'attentat de Sarajevo", "Le traité de Versailles", "La révolution russe"));
        qcmList.add(new Qcm("histoire", "Qui a écrit 'Roméo et Juliette' ?", "William Shakespeare", "Victor Hugo", "Molière"));
        qcmList.add(new Qcm("histoire", "Quel était le nom de la célèbre reine d'Égypte, connue pour sa beauté et son intelligence ?", "Cléopâtre", "Néfertiti", "Isis"));
        qcmList.add(new Qcm("histoire", "Quel était le nom de la première civilisation grecque ?", "La civilisation minoenne", "La civilisation mycénienne", "La civilisation égéenne"));
        qcmList.add(new Qcm("histoire", "Qui était le premier président des États-Unis ?", "Thomas Jefferson", "George Washington", "Abraham Lincoln"));
        qcmList.add(new Qcm("histoire", "Quelle est la capitale de l'Espagne ?", "Madrid", "Barcelone", "Lisbonne"));
        qcmList.add(new Qcm("histoire", "Quel événement a marqué la fin de la Guerre froide ?", "La chute du mur de Berlin", "La Révolution cubaine", "L'indépendance de l'Inde"));
        qcmList.add(new Qcm("histoire", "Quelle était la capitale de l'Empire romain ?", "Athènes", "Rome", "Alexandrie"));
        qcmList.add(new Qcm("histoire", "Qui était le premier homme à avoir réalisé un tour du monde ?", "Ferdinand Magellan", "Vasco de Gama", "James Cook"));
        qcmList.add(new Qcm("histoire", "Quel était le nom du navire sur lequel Charles Darwin a navigué lors de son voyage autour du monde ?", "L'Endeavour", "Le Beagle", "Le Golden Hind"));
        qcmList.add(new Qcm("histoire", "Qui a peint 'La Joconde' ?", "Vincent van Gogh", "Leonardo da Vinci", "Pablo Picasso"));

    }

    public void initializeQcm() {
        class SaveQcm extends AsyncTask<Void, Void, Qcm> {
            @Override
            protected Qcm doInBackground(Void... voids) {

                for (Qcm qcm : qcmList) {
                    // adding to database
                    long id = mDb.getAppDatabase()
                            .qcmDao()
                            .insert(qcm);
                    // mettre à jour l'id de l'utilisateur
                    // Nécessaire si on souhaite avoir accès à l'id plus tard dans l'activité
                    qcm.setId(id);
                }

                return qcmList.get(0);
            }
            @Override
            protected void onPostExecute(Qcm qcm) {
                super.onPostExecute(qcm);
                Log.i("TAG", "Base de données initialisée avec les questions de qcm");
            }
        }

        //////////////////////////
        // IMPORTANT bien penser à executer la demande asynchrone
        SaveQcm su = new SaveQcm();
        su.execute();
    }


}