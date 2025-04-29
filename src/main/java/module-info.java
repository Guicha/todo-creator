module com.example.todocreator {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.google.gson;
    requires jdk.jshell;
    requires jdk.compiler;

    opens com.example.todocreator.Controleur to javafx.graphics, javafx.fxml;
    opens com.example.todocreator.DAO to com.google.gson;
    opens com.example.todocreator.Modele to com.google.gson;
    opens com.example.todocreator to javafx.fxml, javafx.graphics;

    exports com.example.todocreator;
    exports com.example.todocreator.Controleur;
    exports com.example.todocreator.DAO;
    exports com.example.todocreator.Modele;
    exports com.example.todocreator.Vue;
}