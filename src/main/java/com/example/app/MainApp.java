package com.example.app;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.HashMap;

public class MainApp extends Application {

    protected static final String CREDENTIALS_FILE = "./src/main/resources/credentials.txt";

    private HashMap<String, Stage> scene_manager = new HashMap<>();
    private Login loginManager;
    private SignUp signUpManager;
    private DragAndDrop dragAndDropManager;

    @Override
    public void start(Stage primaryStage) {
        this.signUpManager = new SignUp();
        this.dragAndDropManager = new DragAndDrop();

        primaryStage.setTitle("Login Page");

        this.scene_manager.put(primaryStage.getTitle(), primaryStage);

        //Create Grid
        GridPane root = new GridPane();
        root.setAlignment(Pos.CENTER);
        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(10, 10, 10, 10));

        // Create UI components
        Label titleLabel = new Label("Login");
        titleLabel.setStyle("-fx-font-size: 24; -fx-font-weight: bold;");
        root.add(titleLabel, 0, 0);

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        root.add(usernameField, 0, 1);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        root.add(passwordField, 0, 2);

        Button loginButton = new Button("Login");
        loginButton.setId("loginButton");
        loginButton.setOnAction(e -> this.authenticateAndOpenDragAndDrop(usernameField.getText(), passwordField.getText(), root, this.scene_manager));
        root.add(loginButton, 0, 3);

        Button signUpButton = new Button("Sign Up");
        signUpButton.setId("signUpButton");
        signUpButton.setOnAction(e -> signUpManager.openSignUpScene(this.scene_manager));
        root.add(signUpButton ,1, 6);

        /*VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20, 50, 50, 50));
        vbox.getChildren().addAll(titleLabel, usernameField, passwordField, loginButton, signUpButton);
        vbox.setAlignment(Pos.CENTER);

        StackPane root = new StackPane();
        root.getChildren().add(vbox);*/

        Scene scene = new Scene(root, 400, 275);

        // Apply CSS for styling
        scene.getStylesheets().add(getClass().getResource("stylesLogin.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void authenticateAndOpenDragAndDrop(String username, String password,
                                               GridPane grid, HashMap<String, Stage> scene_manager) {
        this.loginManager = new Login();
        if (this.loginManager.authenticateUser(username, password)) {
            this.dragAndDropManager.openDragAndDropScene(scene_manager);
        } else {
            // Display authentication failure message or take appropriate action
            System.out.println("Authentication failed. Incorrect username or password.");
            final Text actiontarget = new Text();
            grid.add(actiontarget, 0, 4);
            actiontarget.setFill(Color.FIREBRICK);
            actiontarget.setText("Incorrect username or password.");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
