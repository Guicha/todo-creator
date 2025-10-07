package com.example.todocreator.Vue;

import com.example.todocreator.MainApplication;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class FenetreDialogVue {

    public static Stage stage = new Stage();

    public static final String AJOUTERTACHE = "ajouter-tache";
    public static final String EDITERTACHE = "editer-tache";

    public static void afficher() {

        if (!FenetreDialogVue.stage.isShowing()) {

            FenetreDialogVue.stage.setResizable(false);
            FenetreDialogVue.stage.centerOnScreen();
            FenetreDialogVue.stage.show();
        }

    }
}
