package com.example.app;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.HashMap;

public class DragAndDrop {
    public void openDragAndDropScene(HashMap<String, Stage> scene_manager) {
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
}
