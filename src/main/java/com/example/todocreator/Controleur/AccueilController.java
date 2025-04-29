package com.example.todocreator.Controleur;

import com.example.todocreator.DAO.DaoFactory;
import com.example.todocreator.DAO.JsonDAO;
import com.example.todocreator.MainApplication;
import com.example.todocreator.Modele.Tache;
import com.example.todocreator.Modele.Utilisateur;
import com.example.todocreator.Vue.FenetreDialogVue;
import com.example.todocreator.Vue.FenetrePrincipaleVue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.stage.Stage;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Classe de contrôleur de la page d'accueil
 * **/
public class AccueilController {

    // On initialise le DAO pour communiquer avec les fichiers JSON
    private JsonDAO jsonDAO = new JsonDAO(new DaoFactory());

    // On stocke l'utilisateur actuel pour afficher les informations qui lui sont liées
    public static Utilisateur utilisateur;

    @FXML
    private MenuItem parametreMenuOption;

    @FXML
    private MenuItem deconnexionMenuOption;

    // On crée le menu déroulant du compte
    @FXML
    private SplitMenuButton compteSplitMenuButton;

    // On initialise la vue de la liste de tâches principales (volet de gauche)
    @FXML
    private ListView<Tache> tachesPrincipalesListView;

    // Bouton pour ajouter une tâche
    @FXML
    private Button ajouterTacheButton;


    @FXML
    public void onParametreClick() {

        System.out.println("Parametres");
    }

    @FXML
    public void onDeconnexionClick() {

        AccueilController.utilisateur = null;

        GestionScenes.chargerScene(FenetrePrincipaleVue.CONNEXION, FenetrePrincipaleVue.stage);
    }

    @FXML
    public void onAjouterTacheButton() {

        GestionScenes.chargerScene(FenetreDialogVue.AJOUTERTACHE, FenetreDialogVue.stage);

        FenetreDialogVue.afficher();
    }

    /**
     * Méthode exécutée au chargement du fichier FXML de la page
     * **/
    @FXML
    public void initialize() {

        // Récupération des tâches de l'utilisateur
        ArrayList<Tache> listeTaches = jsonDAO.getAll(utilisateur);

        // Conversion de l'arrayList en ObservabeList
        ObservableList<Tache> observableListeTaches = FXCollections.observableArrayList(listeTaches);

        // Incorporation des tâches dans la vue de la liste
        tachesPrincipalesListView.setItems(observableListeTaches);

        // Création des cellules personnalisées pour l'affichage des tâches
        tachesPrincipalesListView.setCellFactory(listView -> new TacheCelluleController());

        // Définition du texte du menu déroulant de compte
        compteSplitMenuButton.setText(utilisateur.identifiant);

        System.out.println("Page d'accueil chargée");
    }


}


