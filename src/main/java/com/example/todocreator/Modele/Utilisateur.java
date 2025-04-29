package com.example.todocreator.Modele;

import java.util.ArrayList;

/**
 * Classe contenant les informations de l'utilisateur
 * **/
public class Utilisateur {

    public String identifiant;
    private String mdp;
    public String chemin;
    public ArrayList<Tache> listeTaches;

    // Constructeur
    public Utilisateur(String identifiant, String mdp) {

        this.identifiant = identifiant;
        this.mdp = mdp;
        this.chemin = String.format("./TODOCreator/%s.json", this.identifiant);
        this.listeTaches = new ArrayList<>();
    }


}
