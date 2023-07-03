package com.example.finalproject.controllers;

public class SessionController {
    private static SessionController instance;

    private final String UID;

    private SessionController(String UID) {
        this.UID = UID;
    }

    public String getId() {
        return UID;
    }

    public static void setInstance(String userUID) {
        if (instance != null)
            return;

        instance = new SessionController(userUID);
    }

    public static SessionController getInstance() {
        return instance;
    }
}
