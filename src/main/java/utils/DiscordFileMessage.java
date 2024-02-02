package utils;

import java.util.ArrayList;

public class DiscordFileMessage {

	private String filename;
	private ArrayList<String> urls;

	public DiscordFileMessage(String filename, ArrayList<String> urls) {
		this.filename = filename;
		this.urls = urls;
	}
}
