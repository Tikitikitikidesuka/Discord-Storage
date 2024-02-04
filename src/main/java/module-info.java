module Discord.Storage {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires discord.webhooks;

    opens config;
    opens fileops;
    opens sender;
    opens utils;
    opens com.example.app;
}