package utils;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;

public class DiscordFileMessage implements Serializable {

	private String filename;

	private ArrayList<String> urls;

	public DiscordFileMessage(String filename, ArrayList<String> urls) {
		this.filename = filename;
		this.urls = urls;
	}

	public DiscordFileMessage() {}

	public String getFilename() {
		return filename;
	}

	public ArrayList<String> getUrls() {
		return urls;
	}
}
