package sender;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.receive.ReadonlyAttachment;
import utils.DiscordFileMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class Sender {
	private ArrayList<WebhookClient> clients;
	private int maxConcurrentSends = 10; // Adjust this to your desired concurrency level

	public Sender(ArrayList<String> webhookUrls) {
		this.clients = new ArrayList<>();

		for (String webhookUrl : webhookUrls) {
			this.clients.add(WebhookClient.withUrl(webhookUrl));
		}
	}

	public DiscordFileMessage send(List<byte[]> bytes, String filename) throws InterruptedException, ExecutionException {
		ExecutorService executor = Executors.newFixedThreadPool(maxConcurrentSends);
		List<CompletableFuture<Long>> futures = new ArrayList<>();

		for (int i = 0; i < bytes.size(); i++) {
			final int index = i; // Capture index to use within lambda
			byte[] b = bytes.get(i);
			CompletableFuture<Long> future = CompletableFuture.supplyAsync(() -> {
				try {
					return client.send(b, String.valueOf(index)).get().getId();
				} catch (InterruptedException | ExecutionException e) {
					throw new RuntimeException(e);
				}
			}, executor);
			futures.add(future);
		}

		CompletableFuture<Void> allSends = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
		CompletableFuture<List<Long>> allMsgIds = allSends.thenApply(v -> futures.stream()
                .map(CompletableFuture::join) // This blocks until the future is complete, but all are done here
                .collect(Collectors.toList()));

		// Wait for all sends to complete and collect message IDs
		List<Long> msgIDs = allMsgIds.get();

		// Fetch URLs based on message IDs
		ArrayList<String> urls = new ArrayList<>();
		for (Long l : msgIDs) {
			ReadonlyAttachment attachment = client.get(l).get().getAttachments().get(0);
			urls.add(attachment.getUrl());
		}

		// Shutdown the executor
		executor.shutdown();
		try {
			if (!executor.awaitTermination(800, TimeUnit.MILLISECONDS)) {
				executor.shutdownNow();
			}
		} catch (InterruptedException e) {
			executor.shutdownNow();
		}

		return new DiscordFileMessage(filename, urls);
	}

	public void close() {
		client.close();
	}
}