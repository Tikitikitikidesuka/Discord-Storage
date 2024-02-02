package utils;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {

	private String userID;

    private ArrayList<DiscordFileMessage> files;

    public User(String userID) {
        this.userID = userID;
        this.files = new ArrayList<>();
    }

	public User() {}

    public void addDiscordFileMessage(DiscordFileMessage message) {
        files.add(message);
    }

	public String getUserID() {
		return userID;
	}

	public ArrayList<DiscordFileMessage> getFiles() {
		return files;
	}
}
