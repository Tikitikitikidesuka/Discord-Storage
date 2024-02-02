import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.receive.ReadonlyAttachment;
import sender.Sender;

import java.io.File;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

		Sender sender = new Sender("https://discord.com/api/webhooks/1203027726647427133/dxTtB9Z9K5vDn-zKhNmlzg66YAUZ44oDbVhaazC7DZpP6iI1iRfqawf14_R0xVmlokGM");
		sender.send(".gitignore");
    }
}