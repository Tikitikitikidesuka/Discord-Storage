package sender;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.receive.ReadonlyAttachment;
import utils.DiscordFileMessage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Sender {

	private WebhookClient client;

	public Sender(String url) {
		this.client = WebhookClient.withUrl(url);
	}

	public DiscordFileMessage send(List<byte[]> bytes, String filename) throws ExecutionException, InterruptedException {

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
		return new DiscordFileMessage(filename, urls);
	}

	public void close() {
		client.close();
	}
}
