package com.example.todocreator.Controleur;

import com.example.todocreator.MainApplication;
import com.example.todocreator.Vue.FenetreDialogVue;
import com.example.todocreator.Vue.FenetrePrincipaleVue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GestionScenes {

    public static void chargerScene(String nomScene, Stage stage) {

        Scene scene = null;
        int width = 0;
        int height = 0;

        if (nomScene.equals(FenetrePrincipaleVue.ACCUEIL)) {

            width = 1065;
            height = 700;

        } else if (nomScene.equals(FenetrePrincipaleVue.CONNEXION)) {

            width = 730;
            height = 244;

        } else if (nomScene.equals(FenetreDialogVue.AJOUTERTACHE)) {

            width = 900;
            height = 400;
        }

        try {

            scene = new Scene(chargerFXML(nomScene).load(), width, height);

        } catch (Exception e) {

            System.out.println(e.getMessage());
        }

        stage.setScene(scene);
        stage.centerOnScreen();
    }

    public static FXMLLoader chargerFXML(String nomScene) {

        String cheminScene = String.format("%s.fxml", nomScene);
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource(cheminScene));

        return fxmlLoader;
    }
}
