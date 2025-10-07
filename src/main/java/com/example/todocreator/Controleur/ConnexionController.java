package com.example.todocreator.Controleur;

import com.example.todocreator.DAO.*;
import com.example.todocreator.MainApplication;
import com.example.todocreator.Modele.Utilisateur;
import com.example.todocreator.Vue.FenetrePrincipaleVue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class ConnexionController {

    @FXML
    private TextField identifiantField;

    @FXML
    private PasswordField mdpField;

    @FXML
    private Label infoLabel;

    @FXML
    protected void onConnexionButton() {

        try {

            infoLabel.setText("");

            Utilisateur utilisateur = MainApplication.jsonDAO.connexion(identifiantField.getText(), mdpField.getText());

            System.out.println("Connexion : " + utilisateur.identifiant);

            AccueilController.utilisateur = utilisateur;

            GestionScenes.chargerScene(FenetrePrincipaleVue.ACCUEIL, FenetrePrincipaleVue.stage);

        } catch (Exception e) {

            infoLabel.setText(e.getMessage());
        }
    }

    @FXML
    protected void onInscriptionButton() {

        try {

            infoLabel.setText("");

            Utilisateur utilisateur = MainApplication.jsonDAO.inscription(identifiantField.getText(), mdpField.getText());

            System.out.println("Inscription : " + utilisateur.identifiant);

            AccueilController.utilisateur = utilisateur;

            GestionScenes.chargerScene(FenetrePrincipaleVue.ACCUEIL, FenetrePrincipaleVue.stage);

        } catch (Exception e) {

            infoLabel.setText(e.getMessage());
        }

    }


}
