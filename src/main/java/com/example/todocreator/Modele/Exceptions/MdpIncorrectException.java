package com.example.todocreator.Modele.Exceptions;

public class MdpIncorrectException extends Exception {

    public MdpIncorrectException() {

        super("Mot de passe incorrect");
    }
}
