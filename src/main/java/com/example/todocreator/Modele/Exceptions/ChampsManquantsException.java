package com.example.todocreator.Modele.Exceptions;

public class ChampsManquantsException extends Exception {

    public ChampsManquantsException() {

        super("Des champs sont manquants");
    }
}
