package com.example.todocreator.DAO;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;

/**
 * Classe contenant l'architecture de liaison des objets au stockage des données
 * **/
public class DaoFactory {

    public Gson gson;
    public String dossierSauvegarde;
    public String fichierTaches;
    public String fichierComptes;

    // Constructeur
    public DaoFactory() {

        this.gson = new GsonBuilder()
                .serializeNulls()
                .setPrettyPrinting()
                .create();
        this.dossierSauvegarde = "./TODOCreator/";
        this.fichierTaches = "./TODOCreator/taches.json";
        this.fichierComptes = "./TODOCreator/comptes.json";
    }



    /**
     * Méthode permettant d'écrire des données dans un fichier JSON
     * @param objet Les données à écrire dans le fichier
     * @param path Le chemin d'accès du fichier
     * **/
    public void daoWriter(Object objet, String path) {

        try {

            // On instancie le writer
            Writer writer = new FileWriter(path);

            // On enregistre la liste de tâches dans le fichier JSON
            this.gson.toJson(objet, writer);

            // Nettoyage et fermeture du writer
            writer.flush();
            writer.close();

        } catch (Exception e) {

            System.out.println(e.getMessage());
        }

    }


}
