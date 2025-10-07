package com.example.todocreator.Modele;

import com.example.todocreator.Controleur.AccueilController;
import javafx.collections.ObservableList;

public class Filtres {

    public static void filtreAfaire(ObservableList<Tache> liste) {

        liste.removeIf(tache -> !tache.statut.equals("A faire"));

    }

    public static void filtreEncours(ObservableList<Tache> liste) {

        liste.removeIf(tache -> !tache.statut.equals("En cours"));

    }

    public static void filtreNormal(ObservableList<Tache> liste) {

        liste.removeIf(tache -> !tache.priorite.equals("Normal"));
    }

    public static void filtreImportant(ObservableList<Tache> liste) {

        liste.removeIf(tache -> !tache.priorite.equals("Important"));
    }

    public static void filtreUrgent(ObservableList<Tache> liste) {

        liste.removeIf(tache -> !tache.priorite.equals("Urgent"));
    }
}
