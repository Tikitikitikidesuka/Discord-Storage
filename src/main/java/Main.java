import config.Config;
import config.ConfigLoader;
import config.Constants;
import fileops.FileSlicer;
import fileops.FileStitcher;
import fileops.JsonReader;
import fileops.JsonWriter;
import messages.Downloader;
import messages.Sender;
import messages.DiscordFileMessage;
import users.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.function.Predicate;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {
		Scanner scanner = new Scanner(System.in);
		Config config = ConfigLoader.loadConfig(Constants.RESOURCES_PATH + "/config.yaml");
		Sender sender = new Sender(config.getSender().getUrls());
		User luis = new User("luis");

		//DiscordFileMessage dfm1 = sender.send(FileSlicer.sliceFile(Config.PATH + "beetle-tank.png"), "beetle-tank.png");
		DiscordFileMessage dfm2 = sender.send(FileSlicer.sliceFile(Constants.RESOURCES_PATH + "/movie.mp4"), "movie.mp4");

        //luis.addDiscordFileMessage(dfm1);
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

		Downloader downloader = new Downloader();
		FileStitcher.stitchFile(downloader.downloadFile(result.getUrls(), config.getDownloader().getThreads()), result.getFilename());

		sender.close();
    }
}