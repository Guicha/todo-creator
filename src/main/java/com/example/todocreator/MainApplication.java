package com.example.todocreator;

import com.example.todocreator.Controleur.GestionScenes;
import com.example.todocreator.Vue.FenetreDialogVue;
import com.example.todocreator.Vue.FenetrePrincipaleVue;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;

public class MainApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        FenetreDialogVue.stage.initOwner(FenetrePrincipaleVue.stage);

        GestionScenes.chargerScene(FenetrePrincipaleVue.CONNEXION, FenetrePrincipaleVue.stage);

        stage = FenetrePrincipaleVue.stage;

        stage.setTitle("TODOCreator");
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {

        launch();
    }
}
