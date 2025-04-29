package com.example.todocreator.Modele.Exceptions;

public class CompteExistantException extends Exception {

    public CompteExistantException() {

        super("Ce compte est déjà existant");
    }
}
