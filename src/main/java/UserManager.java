import exceptions.UserNotFoundException;
import fileops.FileSlicer;
import fileops.FileStitcher;
import fileops.JsonReader;
import fileops.JsonWriter;
import kotlin.Pair;
import sender.Downloader;
import sender.Sender;
import utils.DiscordFileMessage;
import utils.User;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class UserManager {

	private final HashMap<String, DiscordFileMessage> users;
	private final Sender sender;
	private final Downloader downloader;

	public UserManager(String managerUrl) {
		this.users = new HashMap<>();
		this.sender = new Sender(managerUrl);
		this.downloader = new Downloader();
	}

	private void writeUser(User user) {
		File file = JsonWriter.writeJson(user);
		try {
			List<byte[]> fileBytes = FileSlicer.sliceFile(file.getPath());
			DiscordFileMessage discordFileMessage = sender.send(fileBytes, file.getName());
			users.put(user.getUserID(), discordFileMessage);
		} catch (IOException | InterruptedException | ExecutionException e) {throw new RuntimeException(e);}
	}

	private User getUser(String userID) throws UserNotFoundException, ExecutionException, InterruptedException, IOException {
		if(!users.containsKey(userID))
			throw new UserNotFoundException();

		DiscordFileMessage discordFileMessage = users.get(userID);
		ArrayList<byte[]> bytes = downloader.downloadFile(discordFileMessage.getUrls());
		File file = FileStitcher.stitchFile(bytes, discordFileMessage.getFilename());
		return JsonReader.readJson(file.getPath());
	}

	public static void main(String[] args) throws UserNotFoundException, IOException, ExecutionException, InterruptedException {
		UserManager um = new UserManager("https://discord.com/api/webhooks/1203330038003662898/5XP7QRHtLx2nUa3-K1DDVIc9OfJ5sCsbgAsFRYpRhaRP0VLzYr6otfCWSG0st31Dk2u6");
		User eusebio = new User("Eusebio");

		um.writeUser(eusebio);
		User u2 = um.getUser(eusebio.getUserID());

		System.out.println(eusebio);
		System.out.println(u2);
	}
}
