package messages;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Downloader {
	public ArrayList<byte[]> downloadFile(ArrayList<String> urls) throws InterruptedException, ExecutionException {
		return downloadFile(urls, Runtime.getRuntime().availableProcessors());
	}

	public ArrayList<byte[]> downloadFile(ArrayList<String> urls, int threads) throws InterruptedException, ExecutionException {
		ExecutorService executor = Executors.newFixedThreadPool(threads);
		List<Future<byte[]>> futures = new ArrayList<>();

		for (String urlString : urls) {
			// Submit download tasks and store Future objects
			futures.add(executor.submit(() -> downloadFromURL(urlString)));
		}

		ArrayList<byte[]> bytes = new ArrayList<>();
		for (Future<byte[]> future : futures) {
			// Wait for each download to complete and add to the result list
			bytes.add(future.get());
		}

		executor.shutdown(); // Shutdown the executor service

		return bytes;
	}

	private byte[] downloadFromURL(String urlString) throws IOException {
		URLConnection urlConnection = new URL(urlString).openConnection();
		try (InputStream in = urlConnection.getInputStream()) {
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			int nRead;
			byte[] data = new byte[1024];
			while ((nRead = in.read(data, 0, data.length)) != -1) {
				buffer.write(data, 0, nRead);
			}
			buffer.flush();
			return buffer.toByteArray();
		}
	}
}