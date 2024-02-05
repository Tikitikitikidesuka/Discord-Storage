package com.example.app;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.HashMap;

public class MainPage implements Manager {
    private Stage mainPageStage;
    public void openMainPageScene(HashMap<String, Manager> scene_manager) {
        this.mainPageStage = new Stage();
        this.mainPageStage.setTitle("Main Page");
        Label mainPageLabel = new Label("Choose one of the following options");

        Button viewFilesButton = new Button("See your uploaded files");
        viewFilesButton.setOnAction(e -> System.out.println("Hola")/*openViewFilesScene()*/);

        Button uploadButton = new Button("Upload a file");
        uploadButton.setOnAction(e -> {
            DragAndDrop dragAndDrop = (DragAndDrop) scene_manager.get("Drag And Drop Page");
            dragAndDrop.openDragAndDropScene(scene_manager);
        });

        VBox mainPageLayout = new VBox(20);
        mainPageLayout.getChildren().addAll(mainPageLabel, viewFilesButton, uploadButton);


        Scene mainPageScene = new Scene(mainPageLayout, 400, 300);
        this.mainPageStage.setScene(mainPageScene);

        Login login = (Login) scene_manager.get("Login Page");
        login.closeScene();

        //cambiar
        this.showScene();
    }

    @Override
    public void showScene() {
        this.mainPageStage.show();
    }

    @Override
    public void closeScene() {
        this.mainPageStage.close();
    }
}
