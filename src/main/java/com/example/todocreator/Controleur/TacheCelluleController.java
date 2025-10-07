package com.example.todocreator.Controleur;

import java.io.IOException;

import com.example.todocreator.MainApplication;
import com.example.todocreator.Modele.Priorite;
import com.example.todocreator.Modele.Statut;
import com.example.todocreator.Modele.Tache;
import com.example.todocreator.Vue.FenetreDialogVue;
import com.sun.tools.javac.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class TacheCelluleController extends ListCell<Tache> {

    private Tache tache;

    @FXML
    private BorderPane borderPane;  // Conteneur racine défini dans le FXML

    @FXML
    private Label titreLabel;       // Pour afficher le titre de la tâche

    @FXML
    private Label statutLabel;

    @FXML
    private Rectangle statutRectangle;

    @FXML
    private Label prioriteLabel;

    @FXML
    private Label echeanceLabel;

    @FXML
    private Text descriptionText; // Pour afficher la description de la tâche

    public TacheCelluleController() {

        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("tache-cellule.fxml"));
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace(); // Vérifiez la console au démarrage pour toute erreur de chargement
        }
    }

    @Override
    protected void updateItem(Tache tache, boolean empty) {

        super.updateItem(tache, empty);

        this.tache = tache;

        if (empty || tache == null) {

            setText(null);
            setGraphic(null);

        } else {

            titreLabel.setText(tache.titre);
            statutLabel.setText(tache.statut);
            prioriteLabel.setText("Priorite : " + tache.priorite);
            echeanceLabel.setText("Echeance : " + new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(new java.util.Date (tache.echeance*1000)));
            descriptionText.setText(tache.description);

            if (tache.statut.equals(Statut.ENCOURS)) {

                statutRectangle.setFill(Color.valueOf("BBA137"));

            } else if (tache.statut.equals(Statut.AFAIRE)) {

                statutRectangle.setFill(Color.valueOf("AEBB95"));

            } else if (tache.statut.equals(Statut.TERMINE)) {

                statutRectangle.setFill(Color.valueOf("4DBB45"));
            }

            if (tache.priorite.equals(Priorite.NORMAL)) {

                prioriteLabel.setTextFill(Color.BLACK);

            } else if (tache.priorite.equals(Priorite.IMPORTANT)) {

                prioriteLabel.setTextFill(Color.ORANGE);

            } else if (tache.priorite.equals(Priorite.URGENT)) {

                prioriteLabel.setTextFill(Color.RED);
            }

            if (tache.echeance < System.currentTimeMillis()/1000 && !tache.statut.equals(Statut.TERMINE)) {

                echeanceLabel.setTextFill(Color.RED);

            } else {

                echeanceLabel.setTextFill(Color.BLACK);
            }

            setText(null);
            setGraphic(borderPane);
        }
    }

    @FXML
    protected void onConfirmerTacheButton() {

        Tache tacheModifiee = null;

        if (tache.statut.equals(Statut.AFAIRE)) {

            tacheModifiee = new Tache(tache.identifiant, tache.titre, tache.description, Statut.ENCOURS, tache.priorite, tache.echeance, tache.notification, tache.auteur);

        } else if (tache.statut.equals(Statut.ENCOURS)) {

            tacheModifiee = new Tache(tache.identifiant, tache.titre, tache.description, Statut.TERMINE, tache.priorite, tache.echeance, tache.notification, tache.auteur);

        } else if (tache.statut.equals(Statut.TERMINE)) {

            tacheModifiee = new Tache(tache.identifiant, tache.titre, tache.description, Statut.ENCOURS, tache.priorite, tache.echeance, tache.notification, tache.auteur);
        }

        if (tacheModifiee != null) {

            MainApplication.jsonDAO.editerTache(tacheModifiee);

        }

    }

    @FXML
    protected void onEditerTacheButton() {

        EditerTacheController.utilisateur = AccueilController.utilisateur;
        EditerTacheController.tache = tache;

        GestionScenes.chargerScene(FenetreDialogVue.EDITERTACHE, FenetreDialogVue.stage);

        FenetreDialogVue.afficher();
    }

    @FXML
    protected void onSupprimerTacheButton() {

        MainApplication.jsonDAO.supprimerTache(tache);

    }
}
