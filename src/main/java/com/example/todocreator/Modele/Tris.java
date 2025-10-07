package com.example.todocreator.Modele;

import javafx.collections.ObservableList;

import java.util.Collections;
import java.util.Comparator;

public class Tris {

    public static void echeanceCroissant(ObservableList<Tache> liste) {

        liste.sort(Comparator.comparing(tache -> tache.echeance));

    }

    public static void echeanceDecroissant(ObservableList<Tache> liste) {

        liste.sort(Comparator.comparing(tache -> tache.echeance));
        Collections.reverse(liste);

    }
}
