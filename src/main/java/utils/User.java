package utils;

import java.util.ArrayList;

public class User {
    public String userID;

    public ArrayList<DiscordFileMessage> files;

    public User(String userID) {
        this.userID = userID;
        this.files = new ArrayList<>();
    }

    public void addDiscordFileMessage(DiscordFileMessage message) {
        files.add(message);
    }
}
