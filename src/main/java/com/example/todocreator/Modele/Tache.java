package com.example.todocreator.Modele;

import java.util.ArrayList;

/**
 * Classe permettant de stocker les informations d'une tâche
 * **/
public class Tache {

    // Attributs
    public int identifiant;
    public String titre;
    public String description;
    public String statut;
    public String priorite;
    public long echeance;
    public long notification;
    public transient Utilisateur auteur;

    // Constructeur
    public Tache(int identifiant, String titre, String description, String statut, String priorite, long echeance, long notification, Utilisateur auteur) {

        this.identifiant = identifiant;
        this.titre = titre;
        this.description = description;
        this.statut = statut;
        this.priorite = priorite;
        this.echeance = echeance;
        this.notification = notification;
        this.auteur = auteur;
    }

    /**
     * Méthode permettant de modifier une tâche en incorporant les données d'une autre tâche
     * @param tache La tâche dont les données vont remplacer celles déjà existantes
     * **/
    public void modifierTache(Tache tache) {

        this.titre = tache.titre;
        this.description = tache.description;
        this.statut = tache.statut;
        this.priorite = tache.priorite;
        this.echeance = tache.echeance;
        this.notification = tache.notification;
    }

}
