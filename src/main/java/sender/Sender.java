package sender;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.receive.ReadonlyAttachment;
import java.io.File;
import java.util.concurrent.ExecutionException;

public class Sender {

	private WebhookClient client;

	public Sender(String url) {
		this.client = WebhookClient.withUrl(url);
	}

	public void send(String path) throws ExecutionException, InterruptedException {

		File file = new File(path);

		long msgID = client.send(file, ".gitignore").get().getId();
		ReadonlyAttachment attachment = client.get(msgID).get().getAttachments().get(0);
		System.out.println(attachment.getUrl());

		client.close();
	}
}
