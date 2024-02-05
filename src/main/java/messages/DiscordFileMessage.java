package messages;

import java.io.Serializable;
import java.util.ArrayList;

public class DiscordFileMessage implements Serializable {

	private String filename;
	private ArrayList<String> urls;
	private ArrayList<Long> messageIDs;


	public DiscordFileMessage(String filename, ArrayList<String> urls, ArrayList<Long> messageIDs) {
		this.filename = filename;
		this.urls = urls;
		this.messageIDs = messageIDs;
	}

	//Needed for the JsonReader
	public DiscordFileMessage() {}

	public String getFilename() {
		return filename;
	}

	public ArrayList<String> getUrls() {
		return urls;
	}

	public ArrayList<Long> getMessageIDs() {
		return messageIDs;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("\"filename\": " + filename + ",\"urls\": [");
		for(String url : urls)
			sb.append("\"" + url + "\",");
		sb.append("]");
		return sb.toString();
	}
}
