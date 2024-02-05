package com.example.app;

import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class Login implements Manager {
    private Stage loginStage;

    public Login (Stage loginStage) {
        this.loginStage = loginStage;
    }

    public boolean authenticateUser(String enteredUsername, String enteredPassword) {
        try (BufferedReader reader = new BufferedReader(new FileReader(MainApp.CREDENTIALS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String storedUsername = parts[0].trim();
                    String storedPassword = parts[1].trim();

                    String[] hashCredentials = Utils.passwordHash(enteredUsername, enteredPassword);

                    if (hashCredentials[0].equals(storedUsername) && hashCredentials[1].equals(storedPassword)) {
                        return true; // Authentication successful
                    }
                }
            }
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return false; // Authentication failed
    }

    @Override
    public void showScene() {
        this.loginStage.show();
    }

    @Override
    public void closeScene() {
        this.loginStage.close();
    }
}
