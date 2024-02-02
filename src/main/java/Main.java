import fileops.FileSlicer;
import sender.Sender;
import utils.DiscordFileMessage;
import utils.JsonWriter;
import utils.User;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {
		Sender sender = new Sender("https://discord.com/api/webhooks/1203027726647427133/dxTtB9Z9K5vDn-zKhNmlzg66YAUZ44oDbVhaazC7DZpP6iI1iRfqawf14_R0xVmlokGM");
		DiscordFileMessage dfm = sender.send(FileSlicer.sliceFile("./src/main/resources/aldeaoindemoniado.png"), "aldeaoindemoniado.png");

        User luis = new User("luis");
        luis.addDiscordFileMessage(dfm);
        JsonWriter.createJson(luis);
    }
}