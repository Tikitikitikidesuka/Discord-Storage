package com.example.app;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SignUp implements Manager {
    private Stage signUpStage;

    public void openSignUpScene(HashMap<String, Manager> scene_manager) {
        signUpStage = new Stage();
        signUpStage.setTitle("Sign Up");

        TextField newUsernameField = new TextField();
        newUsernameField.setPromptText("New Username");

        PasswordField newPasswordField = new PasswordField();
        newPasswordField.setPromptText("New Password");

        Button signUpSubmitButton = new Button("Sign Up");
        signUpSubmitButton.setOnAction(e -> signUpUser(newUsernameField.getText(),
                newPasswordField.getText(), scene_manager));

        StackPane signUpLayout = new StackPane();
        signUpLayout.getChildren().addAll(newUsernameField, newPasswordField, signUpSubmitButton);

        Scene signUpScene = new Scene(signUpLayout, 300, 200);
        signUpStage.setScene(signUpScene);

        Login login = (Login) scene_manager.get("Login Page");
        login.closeScene();

        this.showScene();
    }

    private void signUpUser(String newUsername, String newPassword, HashMap<String, Manager> scene_manager) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(MainApp.CREDENTIALS_FILE, true))) {
            String[] hash = Utils.passwordHash(newUsername, newPassword);
            writer.newLine();
            writer.write(hash[0] + ":" + hash[1]);

            MainPage mainPage = (MainPage) scene_manager.get("Main Page");
            mainPage.openMainPageScene(scene_manager);
            this.closeScene();
        } catch (IOException | NoSuchAlgorithmException e ) {
            e.printStackTrace();
        }
    }

    public void showScene() {
        this.signUpStage.show();
    }

    public void closeScene() {
        this.signUpStage.close();
    }





}
