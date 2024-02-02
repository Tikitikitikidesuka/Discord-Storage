import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.receive.ReadonlyAttachment;
import java.io.File;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // Using the builder
        WebhookClient client = WebhookClient.withUrl("https://discord.com/api/webhooks/1203027726647427133/dxTtB9Z9K5vDn-zKhNmlzg66YAUZ44oDbVhaazC7DZpP6iI1iRfqawf14_R0xVmlokGM"); // or withId(id, token)
        File file = new File(".gitignore");

        long long1 = client.send(file, ".gitignore").get().getId();

        ReadonlyAttachment i = client.get(long1).get().getAttachments().get(0);

        System.out.println(i.getUrl());

        client.close();
    }
}