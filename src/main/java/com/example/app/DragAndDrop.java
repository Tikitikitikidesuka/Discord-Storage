package com.example.app;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.HashMap;

public class DragAndDrop implements Manager {
    private Stage dragAndDropStage;
    public void openDragAndDropScene(HashMap<String, Manager> scene_manager) {
        this.dragAndDropStage = new Stage();
        this.dragAndDropStage.setTitle("Drag and Drop Files");

        StackPane dragAndDropLayout = new StackPane();
        Label dropLabel = new Label("Drag and drop files here");
        dragAndDropLayout.getChildren().add(dropLabel);

        setDragAndDrop(dragAndDropLayout, dropLabel);

        Scene dragAndDropScene = new Scene(dragAndDropLayout, 400, 300);
        this.dragAndDropStage.setScene(dragAndDropScene);

        MainPage mainPage = (MainPage) scene_manager.get("Main Page");
        mainPage.closeScene();

        this.showScene();
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

    @Override
    public void showScene() {
        this.dragAndDropStage.show();
    }

    @Override
    public void closeScene() {
        this.dragAndDropStage.close();
    }
}
