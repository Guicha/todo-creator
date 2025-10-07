package com.example.todocreator.Controleur;

import com.example.todocreator.Modele.*;
import com.example.todocreator.Vue.FenetreDialogVue;
import com.example.todocreator.Vue.FenetrePrincipaleVue;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.skin.DatePickerSkin;
import javafx.scene.layout.AnchorPane;

import java.util.Date;
import java.util.Timer;

/**
 * Classe de contrôleur de la page d'accueil
 * **/
public class AccueilController {

    // On stocke l'utilisateur actuel pour afficher les informations qui lui sont liées
    public static Utilisateur utilisateur;

    private static String triActuelListePrincipale;
    private static String filtreActuelListePrincipale;
    private static String prioriteActuelleListePrincipale;

    private static String triActuelListeTerminees;
    private static String prioriteActuelleListeTerminees;

    @FXML
    private MenuItem parametreMenuOption;

    @FXML
    private MenuItem deconnexionMenuOption;

    // On crée le menu déroulant du compte
    @FXML
    private SplitMenuButton compteSplitMenuButton;




    // On initialise la liste de taches affichées dans la ListView
    public static ObservableList<Tache> observableListeTachesPrincipale;

    // On initialise la vue de la liste de tâches principales (volet de gauche)
    @FXML
    public ListView<Tache> tachesPrincipalesListView;

    // ComboBox pour filtrer la liste principale
    @FXML
    private ComboBox<String> filtreTachesPrincipalesComboBox;

    // ComboBox pour trier la liste principale
    @FXML
    private ComboBox<String> triTachesPrincipalesComboBox;

    // ComboBox pour les priorités de la liste principale
    @FXML
    private ComboBox<String> prioriteTachesPrincipalesComboBox;



    @FXML
    public static ObservableList<Tache> observableListeTachesTerminees;

    @FXML
    public ListView<Tache> tachesTermineesListView;

    @FXML
    private ComboBox<String> triTachesTermineesComboBox;

    @FXML
    private ComboBox<String> prioriteTachesTermineesComboBox;




    @FXML
    private AnchorPane calendrierAnchorPane;

    @FXML
    public DatePicker calendrierPermanentDatePicker;

    public DatePickerSkin calendrierPermanentDatePickerSkin;

    public Node calendrierContenu;



    public static ObservableList<PieChart.Data> pieChartData;

    @FXML
    private PieChart statsPieChart;

    public static double statsProgressBarData;

    public static DoubleProperty progressBarUpdater = new SimpleDoubleProperty(statsProgressBarData);

    @FXML
    private ProgressBar statsProgressBar;

    public static double statsProgressIndicatorData;

    public static DoubleProperty progressIndicatorUpdater = new SimpleDoubleProperty(statsProgressIndicatorData);

    @FXML
    private ProgressIndicator statsProgressIndicator;




    //public static ObservableList<Tache> observableListeNotifications;

    //public static Timer timerNotification = new Timer();

    //@FXML
    //public ListView<Tache> notificationsListView;




    @FXML
    public void onParametreClick() {

        System.out.println("Paramètres");
    }

    @FXML
    public void onDeconnexionClick() {

        AccueilController.utilisateur = null;

        GestionScenes.chargerScene(FenetrePrincipaleVue.CONNEXION, FenetrePrincipaleVue.stage);
    }

    @FXML
    public void onAjouterTacheButton() {

        AjouterTacheController.utilisateur = AccueilController.utilisateur;

        GestionScenes.chargerScene(FenetreDialogVue.AJOUTERTACHE, FenetreDialogVue.stage);

        FenetreDialogVue.afficher();
    }




    @FXML
    public void onFiltreTachesPrincipalesComboBox() {

        filtreActuelListePrincipale = filtreTachesPrincipalesComboBox.getValue();
        updateListePrincipale();
    }

    @FXML
    public void onTriTachesPrincipalesComboBox() {

        triActuelListePrincipale = triTachesPrincipalesComboBox.getValue();
        updateListePrincipale();
    }

    @FXML
    public void onPrioriteTachesPrincipalesComboBox() {

        prioriteActuelleListePrincipale = prioriteTachesPrincipalesComboBox.getValue();
        updateListePrincipale();
    }

    public static void updateListePrincipale() {

        // Rechargement de la liste de tâches sans les tâches terminées
        AccueilController.observableListeTachesPrincipale.clear();
        AccueilController.observableListeTachesPrincipale.addAll(utilisateur.listeTaches);
        observableListeTachesPrincipale.removeIf(tache -> tache.statut.equals(Statut.TERMINE));

        // Application des filtres de statuts
        if (filtreActuelListePrincipale.equals("Toutes")) {

            // On ne fait rien (la liste est déjà rechargée)

        } else if (filtreActuelListePrincipale.equals(Statut.AFAIRE)) {

            Filtres.filtreAfaire(observableListeTachesPrincipale);

        } else if (filtreActuelListePrincipale.equals(Statut.ENCOURS)) {

            Filtres.filtreEncours(observableListeTachesPrincipale);
        }

        // Application des filtres de priorités
        if (prioriteActuelleListePrincipale.equals("Toutes")) {

            // On ne fait rien (la liste est déjà rechargée)

        } else if (prioriteActuelleListePrincipale.equals(Priorite.NORMAL)) {

            Filtres.filtreNormal(observableListeTachesPrincipale);

        } else if (prioriteActuelleListePrincipale.equals(Priorite.IMPORTANT)) {

            Filtres.filtreImportant(observableListeTachesPrincipale);

        } else if (prioriteActuelleListePrincipale.equals(Priorite.URGENT)) {

            Filtres.filtreUrgent(observableListeTachesPrincipale);
        }

        // Application du tri d'échéance
        if (triActuelListePrincipale.equals("Echeance (croissant)")) {

            Tris.echeanceCroissant(observableListeTachesPrincipale);

        } else if (triActuelListePrincipale.equals("Echeance (décroissant)")) {

            Tris.echeanceDecroissant(observableListeTachesPrincipale);
        }


    }




    @FXML
    public void onTriTachesTermineesComboBox() {

        triActuelListeTerminees = triTachesTermineesComboBox.getValue();

        updateListeTerminees();
    }

    @FXML
    public void onPrioriteTachesTermineesComboBox() {

        prioriteActuelleListeTerminees = prioriteTachesTermineesComboBox.getValue();

        updateListeTerminees();
    }

    public static void updateListeTerminees() {

        // Rechargement de la liste de tâches sans les tâches terminées
        AccueilController.observableListeTachesTerminees.clear();
        AccueilController.observableListeTachesTerminees.addAll(utilisateur.listeTaches);
        observableListeTachesTerminees.removeIf(tache -> !tache.statut.equals(Statut.TERMINE));

        // Application des filtres de priorités
        if (prioriteActuelleListeTerminees.equals("Toutes")) {

            // On ne fait rien (la liste est déjà rechargée)

        } else if (prioriteActuelleListeTerminees.equals(Priorite.NORMAL)) {

            Filtres.filtreNormal(observableListeTachesTerminees);

        } else if (prioriteActuelleListeTerminees.equals(Priorite.IMPORTANT)) {

            Filtres.filtreImportant(observableListeTachesTerminees);

        } else if (prioriteActuelleListeTerminees.equals(Priorite.URGENT)) {

            Filtres.filtreUrgent(observableListeTachesTerminees);
        }

        // Application du tri d'échéance
        if (triActuelListeTerminees.equals("Echeance (croissant)")) {

            Tris.echeanceCroissant(observableListeTachesTerminees);

        } else if (triActuelListeTerminees.equals("Echeance (décroissant)")) {

            Tris.echeanceDecroissant(observableListeTachesTerminees);
        }
    }




    public static void updateStats() {

        // Mise à jour du camembert
        PieChart.Data tachesAfaire = new PieChart.Data("A faire", 0);
        PieChart.Data tachesEncours = new PieChart.Data("En cours", 0);
        PieChart.Data tachesTerminees = new PieChart.Data("Terminée", 0);

        for (Tache tache : utilisateur.listeTaches) {

            if (tache.statut.equals(Statut.AFAIRE)) {

                tachesAfaire.setPieValue(tachesAfaire.getPieValue()+1);

            } else if (tache.statut.equals(Statut.ENCOURS)) {

                tachesEncours.setPieValue(tachesEncours.getPieValue()+1);

            } else if (tache.statut.equals(Statut.TERMINE)) {

                tachesTerminees.setPieValue(tachesTerminees.getPieValue()+1);
            }
        }

        pieChartData.clear();
        pieChartData.add(tachesAfaire);
        pieChartData.add(tachesEncours);
        pieChartData.add(tachesTerminees);

        // Mise à jour de la barre de progression
        progressBarUpdater.set(tachesTerminees.getPieValue() / (tachesAfaire.getPieValue() + tachesEncours.getPieValue() + tachesTerminees.getPieValue()));

        // Mise à jour de l'indicateur de progression
        progressIndicatorUpdater.set(tachesTerminees.getPieValue() / (tachesAfaire.getPieValue() + tachesEncours.getPieValue() + tachesTerminees.getPieValue()));

    }


    /*public static void updateNotifications() {

        observableListeNotifications.clear();
        observableListeNotifications.addAll(utilisateur.listeNotifications);

        // Configuration du déclenchement des notifications
        for (Tache tache : utilisateur.listeTaches) {

            if (!tache.statut.equals(Statut.TERMINE) && tache.echeance - tache.notification <= (System.currentTimeMillis()/1000)) {

                for (Tache notif : utilisateur.listeNotifications) {

                    if (notif.identifiant == tache.identifiant) {

                        utilisateur.listeNotifications.remove(notif);
                        break;
                    }
                }

                timerNotification.schedule(new AjoutNotification(tache), tache.echeance-tache.notification);
            }
        }

    }*/


    /**
     * Méthode exécutée au chargement du fichier FXML de la page
     * **/
    @FXML
    public void initialize() {

        // Options de tri pour la liste de tâches
        String[] triTachesOptions = {"Echeance (croissant)", "Echeance (décroissant)"};

        // Options de filtre pour la liste de tâches
        String[] filtreTachesOptions = {"Toutes", "A faire", "En cours"};

        // Options de priorités pour la liste de tâches
        String[] prioriteTachesOptions = {"Toutes", "Normal", "Important", "Urgent"};

        // Initialisation du filtre de statut de base
        filtreActuelListePrincipale = "Toutes";

        // Initialisation de la priorite de base
        prioriteActuelleListePrincipale = "Toutes";
        prioriteActuelleListeTerminees = "Toutes";

        // Initialisation de l'échéance de base
        triActuelListePrincipale = "Echeance (croissant)";
        triActuelListeTerminees = "Echeance (décroissant)";

        // Remplissage des options de tri dans la ComboBox de tri de la liste principale
        triTachesPrincipalesComboBox.setItems(FXCollections.observableArrayList(triTachesOptions));
        triTachesTermineesComboBox.setItems(FXCollections.observableArrayList(triTachesOptions));

        // Remplissage des options de filtrage dans la ComboBox de filtre de la liste principale
        filtreTachesPrincipalesComboBox.setItems(FXCollections.observableArrayList(filtreTachesOptions));

        // Remplissage des options de filtrage dans la ComboBox de filtre de la liste principale
        prioriteTachesPrincipalesComboBox.setItems(FXCollections.observableArrayList(prioriteTachesOptions));
        prioriteTachesTermineesComboBox.setItems(FXCollections.observableArrayList(prioriteTachesOptions));

        // Récupération de la liste de tâches de l'utilisateur
        observableListeTachesPrincipale = FXCollections.observableArrayList(utilisateur.listeTaches);
        observableListeTachesPrincipale.removeIf(tache -> tache.statut.equals(Statut.TERMINE));

        observableListeTachesTerminees = FXCollections.observableArrayList(utilisateur.listeTaches);
        observableListeTachesTerminees.removeIf(tache -> !tache.statut.equals(Statut.TERMINE));

        // Tri de la liste de tâches par échéance
        Tris.echeanceCroissant(observableListeTachesPrincipale);
        Tris.echeanceDecroissant(observableListeTachesTerminees);

        // Incorporation des tâches dans la vue de la liste
        tachesPrincipalesListView.setItems(observableListeTachesPrincipale);
        tachesTermineesListView.setItems(observableListeTachesTerminees);

        // Création des cellules personnalisées pour l'affichage des tâches
        tachesPrincipalesListView.setCellFactory(listView -> new TacheCelluleController());
        tachesTermineesListView.setCellFactory(listView -> new TacheCelluleController());

        // Chargement du calendrier
        // Personnalisation des cellules du calendrier : toutes les cellules non vides seront en rouge
        calendrierPermanentDatePicker.setDayCellFactory(new JourCelluleController());

        calendrierPermanentDatePickerSkin = new DatePickerSkin(calendrierPermanentDatePicker);
        calendrierContenu = calendrierPermanentDatePickerSkin.getPopupContent();

        AnchorPane.setLeftAnchor(calendrierContenu, 45.0); // définit la distance par rapport au bord gauche
        AnchorPane.setTopAnchor(calendrierContenu, 26.0);
        calendrierAnchorPane.getChildren().add(calendrierContenu);

        // Initialisation des données du camembert de stats
        pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("A faire", 0),
                new PieChart.Data("En cours", 0),
                new PieChart.Data("Terminée", 0)
        );
        statsPieChart.setData(pieChartData);

        // Initialisation des données de la progress bar des stats
        statsProgressBar.progressProperty().bind(progressBarUpdater);

        // Initialisation des données du progress indicator des stats
        statsProgressIndicator.progressProperty().bind(progressIndicatorUpdater);

        // Mise à jour des stats
        updateStats();

        // Incorporation de la liste de notifications dans l'objet listView
        /*observableListeNotifications = FXCollections.observableArrayList(utilisateur.listeNotifications);

        notificationsListView.setItems(observableListeNotifications);
        notificationsListView.setCellFactory(listView -> new NotificationCelluleController());

        // Configuration du déclenchement des notifications
        for (Tache tache : utilisateur.listeTaches) {

            if (!tache.statut.equals(Statut.TERMINE) && tache.echeance - tache.notification <= (System.currentTimeMillis()/1000)) {

                Date dateNotif = new Date((tache.echeance - tache.notification)*1000);

                System.out.println(dateNotif);

                timerNotification.schedule(new AjoutNotification(tache), dateNotif);
            }
        }*/

        // Définition du texte du menu déroulant de compte
        compteSplitMenuButton.setText(utilisateur.identifiant);

        System.out.println("Page d'accueil chargée");
    }


}


