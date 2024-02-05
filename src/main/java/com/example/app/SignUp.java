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
        signUpSubmitButton.setOnAction(e -> signUpUser(newUsernameField.getText(), newPasswordField.getText()));

        StackPane signUpLayout = new StackPane();
        signUpLayout.getChildren().addAll(newUsernameField, newPasswordField, signUpSubmitButton);

        Scene signUpScene = new Scene(signUpLayout, 300, 200);
        signUpStage.setScene(signUpScene);

        Login login = (Login) scene_manager.get("Login Page");
        login.closeScene();

        this.showScene();
    }

    private void signUpUser(String newUsername, String newPassword) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(MainApp.CREDENTIALS_FILE, true))) {
            writer.newLine();
            writer.write(newUsername + ":" + newPassword);
            System.out.println("User signed up: " + newUsername);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showScene() {
        this.signUpStage.show();
    }

    public void closeScene() {

    }

    private void openMainPage() {

    }
}
