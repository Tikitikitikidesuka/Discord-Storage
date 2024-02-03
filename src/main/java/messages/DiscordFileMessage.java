package messages;

import java.io.Serializable;
import java.util.ArrayList;

public class DiscordFileMessage implements Serializable {

	private String filename;

	private ArrayList<String> urls;

	public DiscordFileMessage(String filename, ArrayList<String> urls) {
		this.filename = filename;
		this.urls = urls;
	}

	//Needed for the JsonReader
	public DiscordFileMessage() {}

	public String getFilename() {
		return filename;
	}

	public ArrayList<String> getUrls() {
		return urls;
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
