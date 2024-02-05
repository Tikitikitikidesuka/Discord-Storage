import config.Config;
import config.ConfigLoader;
import config.Constants;
import exceptions.UserNotFoundException;
import fileops.FileSlicer;
import fileops.JsonReader;
import messages.DiscordFileMessage;
import messages.Sender;
import users.User;
import users.UserManager;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException, UserNotFoundException {
		Scanner scanner = new Scanner(System.in);
		Config config = ConfigLoader.loadConfig(Constants.RESOURCES_PATH + "/config.yaml");
		Sender sender = new Sender(config.getSender().getUrls());
		UserManager userManager = new UserManager(config.getUserManager().getUrl());

		User luis = new User("luis");

		DiscordFileMessage dfm1 = sender.send(FileSlicer.sliceFile(Constants.RESOURCES_PATH + "beetle-tank.png"), "beetle-tank.png");
		//DiscordFileMessage dfm2 = sender.send(FileSlicer.sliceFile(Constants.RESOURCES_PATH + "/movie.mp4"), "movie.mp4");
        luis.addDiscordFileMessage(dfm1);
		//luis.addDiscordFileMessage(dfm2);

		userManager.addUser(luis);

		scanner.next();

		userManager.removeUser(luis.getUserID());

        User luisCopia = JsonReader.readJson("luis.json");

		/*ArrayList<DiscordFileMessage> dfms = luisCopia.getFiles();

		for(DiscordFileMessage d : dfms) {
			System.out.println(d.getFilename());
		}

		System.out.println("Which file from the list do you want to save");
		String query = scanner.next();

		Predicate<DiscordFileMessage> search = (DiscordFileMessage d) -> (d.getFilename().contains(query));
		DiscordFileMessage result = dfms.stream().filter(search).toList().get(0); //Fix!!!

		Downloader downloader = new Downloader();
		FileStitcher.stitchFile(downloader.downloadFile(result.getUrls(), config.getDownloader().getThreads()), result.getFilename());
*/
		sender.close();
    }
}