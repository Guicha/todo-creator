package com.example.todocreator.Controleur;

import com.example.todocreator.Modele.Statut;
import com.example.todocreator.Modele.Tache;
import com.example.todocreator.Controleur.AccueilController;
import com.example.todocreator.Modele.Utilisateur;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.util.Callback;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;

public class JourCelluleController implements Callback<DatePicker, DateCell> {

    @Override
    public DateCell call(DatePicker datePicker) {

        return new DateCell() {

            @Override
            public void updateItem(LocalDate item, boolean empty) {

                super.updateItem(item, empty);
                // Efface le style par défaut des cellules
                setStyle("");

                for (Tache tache : AccueilController.utilisateur.listeTaches) {

                    if (!empty && item != null) {

                        String dateTache = new java.text.SimpleDateFormat("d/M/yyyy").format(new java.util.Date (tache.echeance*1000));
                        String dateCase = String.format("%d/%d/%d", item.getDayOfMonth(), item.getMonthValue(), item.getYear());

                        if (dateTache.equals(dateCase) && !tache.statut.equals(Statut.TERMINE)) {

                            setStyle("-fx-background-color: #ff8585;");

                        }
                    }
                }

            }
        };
    }
}
