package sender;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.receive.ReadonlyAttachment;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Sender {

	private WebhookClient client;

	public Sender(String url) {
		this.client = WebhookClient.withUrl(url);
	}

	public String send(String path) throws ExecutionException, InterruptedException {

		File file = new File(path);

		long msgID = client.send(file, ".gitignore").get().getId();
		ReadonlyAttachment attachment = client.get(msgID).get().getAttachments().get(0);
		String url = attachment.getUrl();
		client.close();

		return url;
	}

	public ArrayList<String> send(List<byte[]> bytes) throws ExecutionException, InterruptedException {

		int c = 0;
		ArrayList<Long> msgIDs = new ArrayList<>();
		ArrayList<String> urls = new ArrayList<>();
		for(byte[] b : bytes) {
			msgIDs.add(client.send(b, String.valueOf(c++)).get().getId());
		}

		for(Long l : msgIDs) {
			ReadonlyAttachment attachment = client.get(l).get().getAttachments().get(0);
			urls.add(attachment.getUrl());
		}

		client.close();

		return urls;
	}
}
