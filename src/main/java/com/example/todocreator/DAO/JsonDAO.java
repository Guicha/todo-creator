package com.example.todocreator.DAO;

import com.example.todocreator.Modele.Exceptions.ChampsManquantsException;
import com.example.todocreator.Modele.Exceptions.CompteExistantException;
import com.example.todocreator.Modele.Exceptions.CompteInexistantException;
import com.example.todocreator.Modele.Exceptions.MdpIncorrectException;
import com.example.todocreator.Modele.Priorite;
import com.example.todocreator.Modele.Statut;
import com.example.todocreator.Modele.Tache;
import com.example.todocreator.Modele.Utilisateur;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.util.ArrayList;

/**
 * Classe contenant les méthodes permettant les interactions entre les objets Tache et les fichiers de sauvegarde
 * **/
public class JsonDAO {

    public DaoFactory dao;

    // Constructeur
    public JsonDAO(DaoFactory dao) {

        this.dao = dao;
    }

    /**
     * Méthode permettant d'initialiser le dossier et les fichiers de stockage des données
     * Cette méthode est appelée au démarrage du programme afin de vérifier la présence de tous les éléments nécessaires
     * @param utilisateur L'utilisateur a initialisé
     * **/
    public void initUtilisateur(Utilisateur utilisateur) {

        File dossierSauvegarde = new File(dao.dossierSauvegarde);
        File fichierSauvegarde = new File(utilisateur.chemin);

        try {

            // On vérifie si le dossier existe déjà, sinon on le crée
            if (!dossierSauvegarde.exists()) {

                dossierSauvegarde.mkdirs();
            }

            // On vérifie si les fichiers JSON de sauvegarde existent déjà, sinon on les crée
            if (!fichierSauvegarde.exists()) {

                fichierSauvegarde.createNewFile();
            }


        } catch (Exception e) {

            System.out.println(e.getMessage());
        }

    }

    /**
     * Méthode permettant à une utilisateur de s'inscrire
     * @param identifiant L'identifiant de l'utilisateur
     * @param mdp Le mot de passe de l'utilisateur
     * **/
    public Utilisateur inscription(String identifiant, String mdp) throws CompteExistantException, ChampsManquantsException {

        // On instancie l'objet du nouvel utilisateur potentiel
        Utilisateur utilisateur = new Utilisateur(identifiant, mdp);

        // On met en place le chemin d'accès hypothétique de l'utilisateur qui souhaite s'inscrire
        File fichierSauvegarde = new File(utilisateur.chemin);

        // On vérifie si l'identifiant est valide
        if (identifiant.isEmpty()) {

            throw new ChampsManquantsException();
        }

        // On vérifie si le compte existe déjà
        if (fichierSauvegarde.exists()) {

            throw new CompteExistantException();
        }

        // On crée le fichier de sauvegarde de l'utilisateur
        this.initUtilisateur(utilisateur);

        // On écrit l'utilisateur dans le fichier JSON
        dao.daoWriter(utilisateur, utilisateur.chemin);

        return utilisateur;
    }

    /**
     * Méthode permettant à un utilisateur de se connecter à l'aide de son identifiant et son mot de passe
     * @param identifiant L'identifiant de l'utilisateur
     * @param mdp Le mot de passe de l'utilisateur
     * @return L'objet de l'utilisateur connecté
     * **/
    public Utilisateur connexion(String identifiant, String mdp) throws CompteInexistantException, MdpIncorrectException {

        // On déclare l'objet utilisateur
        Utilisateur utilisateur = null;

        // On met en place le chemin d'accès hypothétique de l'utilisateur qui souhaite se connecter
        File fichierSauvegarde = new File(String.format("./TODOCreator/%s.json", identifiant));

        // On vérifie si le fichier existe, aka si le compte existe
        if (!fichierSauvegarde.exists()) {

            throw new CompteInexistantException();
        }

        try {

            // Création du flux de données du fichier de sauvegarde
            FileInputStream fis = new FileInputStream(fichierSauvegarde);

            // Création du lecteur de JSON
            JsonReader jsonReader = new JsonReader(new InputStreamReader(fis));

            // On désérialise le fichier de sauvegarde dans un objet
            JsonObject objJson = JsonParser.parseReader(jsonReader).getAsJsonObject();

            // On vérifie si les mots de passe correspondent
            if (!objJson.get("mdp").getAsString().equals(mdp)) {

                throw new MdpIncorrectException();
            }

            // On instancie l'objet de l'utilisateur connecté
            utilisateur = new Utilisateur(identifiant, mdp);

            // On récupère toutes les tâches de l'utilisateur
            utilisateur.listeTaches = this.getAll(utilisateur);

        } catch (MdpIncorrectException mdpe) {

            throw new MdpIncorrectException();

        } catch (Exception e) {

            System.out.println(e.getMessage());
        }

        return utilisateur;
    }

    /**
     * Méthode permettant de récupérer l'ensemble des tâches stockées dans le fichier de sauvegarde
     * @return La liste des tâches sauvegardées
     * **/
    public ArrayList<Tache> getAll(Utilisateur utilisateur) {

        // Création de la liste de stockage des tâches
        ArrayList<Tache> listeTaches = new ArrayList<>();

        try {

            // Création du flux de données du fichier de sauvegarde
            FileInputStream fis = new FileInputStream(utilisateur.chemin);

            // Création du lecteur de JSON
            JsonReader jsonReader = new JsonReader(new InputStreamReader(fis));

            // On désérialise le fichier de sauvegarde dans un objet
            JsonObject objJson = JsonParser.parseReader(jsonReader).getAsJsonObject();

            if (objJson == null) {

                return listeTaches;
            }

            JsonArray tabJson = objJson.get("listeTaches").getAsJsonArray();

            // On crée un objet itératif permettant de stocker chaque entrée du fichier
            JsonObject entreeSauvegarde;


            // On remplit la liste de tâches
            for (int i = 0; i< tabJson.size(); i++) {

                entreeSauvegarde = tabJson.get(i).getAsJsonObject();

                Tache tacheAjoutee = new Tache(entreeSauvegarde.get("identifiant").getAsInt(), entreeSauvegarde.get("titre").getAsString(), entreeSauvegarde.get("description").getAsString(), entreeSauvegarde.get("statut").getAsString(), entreeSauvegarde.get("priorite").getAsString(), entreeSauvegarde.get("echeance").getAsLong(), utilisateur);

                listeTaches.add(tacheAjoutee);
            }


        } catch (Exception e) {

            System.out.println(e.getMessage());
        }

        return listeTaches;
    }

    /**
     * Méthode permettant d'enregistrer une liste de tâche dans le fichier de sauvegarde
     * @param tache La tâche à sauvegarder
     * **/
    public void ajouterTache(Tache tache) {

        try {

            // On définit l'identifiant de la tâche
            tache.identifiant = (int) (System.currentTimeMillis() / 1000);

            // On ajoute la tâche à la liste de tâches de l'utilisateur
            tache.auteur.listeTaches.add(tache);

            // On écrit l'objet utilisateur dans le fichier
            dao.daoWriter(tache.auteur, tache.auteur.chemin);

        } catch (Exception e) {

            System.out.println(e.getMessage());
        }

    }

    /**
     * Méthode permettant d'éditer les informations d'une tâche existante
     * @param tache La tâche à modifier
     * **/
    public void editerTache(Tache tache) {

        // On modifie la tâche concernée
        for (Tache tacheSauvegardee : tache.auteur.listeTaches) {

            if (tacheSauvegardee.identifiant == tache.identifiant && tacheSauvegardee.auteur.identifiant.equals(tache.auteur.identifiant)) {

                tacheSauvegardee.modifierTache(tache);
            }
        }

        // On écrit les modifications sur le fichier
        dao.daoWriter(tache.auteur, tache.auteur.chemin);

    }

    /**
     * Méthode permettant de supprimer une tâche
     * @param tache La tâche à supprimer
     * **/
    public void supprimerTache(Tache tache) {

        // On supprime la tâche concernée
        for (Tache tacheSauvegardee : tache.auteur.listeTaches) {

            if (tacheSauvegardee.identifiant == tache.identifiant && tacheSauvegardee.auteur.identifiant.equals(tache.auteur.identifiant)) {

                tache.auteur.listeTaches.remove(tacheSauvegardee);

                break;
            }
        }

        // On écrit les modifications sur le fichier
        dao.daoWriter(tache.auteur, tache.auteur.chemin);
    }

    public static void main(String[] args) {

        JsonDAO jsonDAO = new JsonDAO(new DaoFactory());

        Utilisateur user = null;

        try {

            jsonDAO.inscription("thomas", "feur");

            user = jsonDAO.connexion("thomas", "feur");

        } catch (Exception e) {

            System.out.println(e.getMessage());
        }

        if (user == null) {

            System.exit(0);
        }

        jsonDAO.initUtilisateur(user);

        Tache tache = new Tache(0,"faire pipi", "ENORME CHIASSE", Statut.ENCOURS, Priorite.URGENT, 2, user);

        jsonDAO.ajouterTache(tache);

        /*try {

            jsonDAO.ajouterTache(tache);

        } catch (Exception e) {

            System.out.println(e.getMessage());
        }*/

        //jsonDAO.editerTache(tache);

        //jsonDAO.supprimerTache(tache);
    }

}
