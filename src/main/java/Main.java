import config.Config;
import fileops.FileSlicer;
import fileops.FileStitcher;
import fileops.JsonReader;
import fileops.JsonWriter;
import sender.Downloader;
import sender.Sender;
import utils.DiscordFileMessage;
import utils.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.function.Predicate;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {
		Scanner scanner = new Scanner(System.in);
		Sender sender = new Sender("https://discord.com/api/webhooks/1203027726647427133/dxTtB9Z9K5vDn-zKhNmlzg66YAUZ44oDbVhaazC7DZpP6iI1iRfqawf14_R0xVmlokGM");
		User luis = new User("luis");

		DiscordFileMessage dfm1 = sender.send(FileSlicer.sliceFile(Config.PATH + "beetle-tank.png"), "beetle-tank.png");
		DiscordFileMessage dfm2 = sender.send(FileSlicer.sliceFile(Config.PATH + "aldeaoindemoniado.png"), "aldeaoindemoniado.png");

        luis.addDiscordFileMessage(dfm1);
		luis.addDiscordFileMessage(dfm2);
		JsonWriter.writeJson(luis);

        User luisCopia = JsonReader.readJson("luis.json");

		ArrayList<DiscordFileMessage> dfms = luisCopia.getFiles();

		for(DiscordFileMessage d : dfms) {
			System.out.println(d.getFilename());
		}

		System.out.println("Which file from the list do you want to save");
		String query = scanner.next();

		Predicate<DiscordFileMessage> search = (DiscordFileMessage d) -> (d.getFilename().contains(query));
		DiscordFileMessage result = dfms.stream().filter(search).toList().get(0); //Fix!!!

		FileStitcher.stitchFile(Downloader.downloadFile(result.getUrls()), result.getFilename());

		sender.close();
    }
}