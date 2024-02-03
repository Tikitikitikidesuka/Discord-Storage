package utils;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {

	private String userID;

    private ArrayList<DiscordFileMessage> files;

    public User(String userID) {
        this.userID = userID;
        this.files = new ArrayList<>();
    }

	//Needed for the JsonReader
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

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("\"userID\": " + userID + ",\"files\": [");
		for(DiscordFileMessage dfm : files)
			sb.append("{\n" + dfm.toString() + "},");
		sb.append("]");
		return sb.toString();
	}
}
