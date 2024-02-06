package messages;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.receive.ReadonlyAttachment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class Sender {

	private final ArrayList<WebhookClient> clients;

	public Sender(Collection<String> webhookUrls) {
		this.clients = new ArrayList<>();
		for (String webhookUrl : webhookUrls) {
			this.clients.add(WebhookClient.withUrl(webhookUrl));
		}
	}

	public Sender(String webhookUrl) {
		this.clients = new ArrayList<>();
		this.clients.add(WebhookClient.withUrl(webhookUrl));
	}

	public DiscordFileMessage send(List<byte[]> bytes, String filename) throws InterruptedException, ExecutionException {
		ExecutorService executor = Executors.newFixedThreadPool(clients.size());
		List<CompletableFuture<Long>> futures = new ArrayList<>();

		// Distribute sends across the clients
		for (int i = 0; i < bytes.size(); i++) {
			final int index = i;
			byte[] b = bytes.get(i);
			WebhookClient client = clients.get(index % clients.size()); // Use modulo to cycle through clients

			CompletableFuture<Long> future = CompletableFuture.supplyAsync(() -> {
				try {
					// Send using the selected client and filename as the index
					return client.send(b, filename + String.valueOf(index)).get().getId();
				} catch (InterruptedException | ExecutionException e) {
					throw new RuntimeException(e);
				}
			}, executor);
			futures.add(future);
		}

		CompletableFuture<Void> allSends = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
		CompletableFuture<List<Long>> allMsgIds = allSends.thenApply(v -> futures.stream()
				.map(CompletableFuture::join)
				.collect(Collectors.toList()));

		// Wait for all sends to complete and collect message IDs
		List<Long> msgIDs = allMsgIds.get();

		// Fetch URLs based on message IDs
		ArrayList<String> urls = new ArrayList<>();
		for (int i = 0; i < msgIDs.size(); ++i) {
			// Assume each client can retrieve any message
			ReadonlyAttachment attachment = clients.get(i % clients.size()).get(msgIDs.get(i)).get().getAttachments().get(0);
			urls.add(attachment.getUrl());
		}

		// Shutdown the executor
		executor.shutdown();
		if (!executor.awaitTermination(800, TimeUnit.MILLISECONDS)) {
			executor.shutdownNow();
		}

		return new DiscordFileMessage(filename, urls, (ArrayList<Long>) msgIDs);
	}

	public void deleteMessage(long messageID) throws InterruptedException, ExecutionException {
		for(WebhookClient client : clients) {
			if(client.get(messageID).get() != null) {
				client.delete(messageID);
				return;
			}
		}
	}

	public void close() {
		clients.forEach(WebhookClient::close);
	}
}