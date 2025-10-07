package com.example.todocreator.Controleur;

import com.example.todocreator.MainApplication;
import com.example.todocreator.Modele.Statut;
import com.example.todocreator.Modele.Tache;
import com.example.todocreator.Modele.Utilisateur;
import com.example.todocreator.Vue.FenetreDialogVue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EditerTacheController {

    public static Utilisateur utilisateur;

    public static Tache tache;

    @FXML
    private TextField titreField;

    @FXML
    private TextArea descriptionField;

    @FXML
    private ComboBox<String> prioriteComboBox;

    @FXML
    private DatePicker echeanceDatePicker;

    @FXML
    private ComboBox<String> heureComboBox;

    @FXML
    private ComboBox<String> minutesComboBox;

    @FXML
    private ComboBox<String> notificationComboBox;

    @FXML
    private Text informationText;

    @FXML
    public void onModifierButton() {

        if (titreField.getText().isEmpty() || prioriteComboBox.getValue() == null || echeanceDatePicker.getValue() == null || heureComboBox.getValue() == null || minutesComboBox.getValue() == null || notificationComboBox.getValue() == null) {

            informationText.setText("Veuillez remplir tous les champs obligatoires");

        } else {

            // On construit le timestamp de l'échéance
            long echeanceTimestamp = 0;
            long notificationTimestamp = 0;

            try {

                String echeanceComplete = String.format("%s %s:%s", echeanceDatePicker.getValue(), heureComboBox.getValue(), minutesComboBox.getValue());
                echeanceTimestamp = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm").parse(echeanceComplete).getTime()/1000;

            } catch (Exception e) {

                System.out.println(e.getMessage());
            }

            // On calcule la notification
            if (notificationComboBox.getValue().equals("Une heure avant")) {

                notificationTimestamp = 3600;

            } else if (notificationComboBox.getValue().equals("Un jour avant")) {

                notificationTimestamp = 86400;

            } else if (notificationComboBox.getValue().equals("Une semaine avant")) {

                notificationTimestamp = 604800;

            } else if (notificationComboBox.getValue().equals("Un mois avant")) {

                notificationTimestamp = 2592000;
            }

            Tache tacheModifiee = new Tache(tache.identifiant, titreField.getText(), descriptionField.getText(), tache.statut, prioriteComboBox.getValue(), echeanceTimestamp, notificationTimestamp, utilisateur);

            MainApplication.jsonDAO.editerTache(tacheModifiee);

            FenetreDialogVue.stage.close();

        }
    }

    @FXML
    public void initialize() {

        String[] prioriteOptions = {"Normal", "Important", "Urgent"};
        String[] notificationOptions = {"Une heure avant", "Un jour avant", "Une semaine avant", "Un mois avant"};
        String[] heureOptions = {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"};
        String[] minutesOptions = {"00", "10", "20", "30", "40", "50"};

        prioriteComboBox.setItems(FXCollections.observableArrayList(prioriteOptions));
        notificationComboBox.setItems(FXCollections.observableArrayList(notificationOptions));
        heureComboBox.setItems(FXCollections.observableArrayList(heureOptions));
        minutesComboBox.setItems(FXCollections.observableArrayList(minutesOptions));

        titreField.setText(tache.titre);

        descriptionField.setText(tache.description);

        prioriteComboBox.setValue(tache.priorite);

        String date = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date (tache.echeance*1000));
        String heure = new java.text.SimpleDateFormat("HH").format(new java.util.Date (tache.echeance*1000));
        String minutes = new java.text.SimpleDateFormat("mm").format(new java.util.Date (tache.echeance*1000));

        echeanceDatePicker.setValue(LocalDate.parse(date));

        heureComboBox.setValue(heure);

        minutesComboBox.setValue(minutes);

        if (tache.notification == 3600)  {

            notificationComboBox.setValue(("Une heure avant"));

        } else if (tache.notification == 86400) {

            notificationComboBox.setValue(("Un jour avant"));

        } else if (tache.notification == 604800) {

            notificationComboBox.setValue(("Une semaine avant"));

        } else if (tache.notification == 2592000) {

            notificationComboBox.setValue(("Un mois avant"));
        }
    }
}
