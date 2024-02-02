package sender;

import fileops.DSConfig;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class Downloader {

	public static ArrayList<byte[]> downloadFile(ArrayList<String> urls) throws IOException {
		ArrayList<byte[]> bytes = new ArrayList<>();

		for(String urlString : urls) {
			URLConnection urlConnection = new URL(urlString).openConnection();
			try (InputStream in = urlConnection.getInputStream();) {
				byte[] buffer = in.readNBytes(DSConfig.FILE_SIZE);
				bytes.add(buffer);
			}
		}

		return bytes;
	}

	public static byte[] downloadFile(String url) throws IOException {
		URLConnection urlConnection = new URL(url).openConnection();
		try (InputStream in = urlConnection.getInputStream();) {
			byte[] buffer = in.readNBytes(DSConfig.FILE_SIZE);
			return buffer;
		}
	}
}
