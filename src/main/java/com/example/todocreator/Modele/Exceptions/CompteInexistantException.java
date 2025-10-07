package com.example.todocreator.Modele.Exceptions;

public class CompteInexistantException extends Exception {

    public CompteInexistantException() {

        super("Compte est inexistant");
    }
}
