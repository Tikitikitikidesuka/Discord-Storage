package com.example.app;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

public class MainApp extends Application {

    private static final String CREDENTIALS_FILE = "./src/main/resources/credentials.txt";

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Login Page");

        // Create UI components
        Label titleLabel = new Label("Login");
        titleLabel.setStyle("-fx-font-size: 24; -fx-font-weight: bold;");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        Button loginButton = new Button("Login");
        loginButton.setId("loginButton");
        loginButton.setOnAction(e -> authenticateAndOpenDragAndDrop(usernameField.getText(), passwordField.getText()));

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20, 50, 50, 50));
        vbox.getChildren().addAll(titleLabel, usernameField, passwordField, loginButton);
        vbox.setAlignment(Pos.CENTER);

        StackPane root = new StackPane();
        root.getChildren().add(vbox);

        Scene scene = new Scene(root, 300, 250);

        // Apply CSS for styling
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void authenticateAndOpenDragAndDrop(String username, String password) {
        if (authenticateUser(username, password)) {
            openDragAndDropScene();
        } else {
            // Display authentication failure message or take appropriate action
            System.out.println("Authentication failed. Incorrect username or password.");
        }
    }

    private void openDragAndDropScene() {
        Stage dragAndDropStage = new Stage();
        dragAndDropStage.setTitle("Drag and Drop Files");

        StackPane dragAndDropLayout = new StackPane();
        Label dropLabel = new Label("Drag and drop files here");
        dragAndDropLayout.getChildren().add(dropLabel);

        setDragAndDrop(dragAndDropLayout, dropLabel);

        Scene dragAndDropScene = new Scene(dragAndDropLayout, 400, 300);
        dragAndDropStage.setScene(dragAndDropScene);

        dragAndDropStage.show();
    }

    private void setDragAndDrop(StackPane root, Label dropLabel) {
        root.setOnDragOver(event -> {
            if (event.getGestureSource() != root && event.getDragboard().hasFiles()) {
                event.acceptTransferModes(javafx.scene.input.TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });

        root.setOnDragDropped(event -> {
            boolean success = false;
            if (event.getGestureSource() != root && event.getDragboard().hasFiles()) {
                success = true;
                dropLabel.setText("Files dropped!");
                event.getDragboard().getFiles().forEach(file ->
                        System.out.println("Dropped file: " + file.getAbsolutePath()));
            }
            event.setDropCompleted(success);
            event.consume();
        });
    }

    private boolean authenticateUser(String enteredUsername, String enteredPassword) {
        try (BufferedReader reader = new BufferedReader(new FileReader(CREDENTIALS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String storedUsername = parts[0].trim();
                    String storedPassword = parts[1].trim();
                    if (enteredUsername.equals(storedUsername) && enteredPassword.equals(storedPassword)) {
                        return true; // Authentication successful
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false; // Authentication failed
    }

    public static void main(String[] args) {
        launch(args);
    }
}
