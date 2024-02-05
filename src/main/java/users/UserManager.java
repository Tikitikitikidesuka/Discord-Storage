package users;

import exceptions.UserNotFoundException;
import fileops.FileSlicer;
import fileops.FileStitcher;
import fileops.JsonReader;
import fileops.JsonWriter;
import messages.Downloader;
import messages.Sender;
import messages.DiscordFileMessage;

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

	public void addUser(User user) {
		File file = JsonWriter.writeJson(user);
		try {
			List<byte[]> fileBytes = FileSlicer.sliceFile(file.getPath());
			DiscordFileMessage discordFileMessage = sender.send(fileBytes, file.getName());
			users.put(user.getUserID(), discordFileMessage);
		} catch (IOException | InterruptedException | ExecutionException e) {throw new RuntimeException(e);}
	}

	public void removeUser(String userID) throws UserNotFoundException, ExecutionException, InterruptedException {
		if(!users.containsKey(userID))
			throw new UserNotFoundException();

		DiscordFileMessage discordFileMessage = users.get(userID);
		for(Long id : discordFileMessage.getMessageIDs()) {
			sender.deleteMessage(id);
		}
	}

	public void updateUser(User user) throws UserNotFoundException, ExecutionException, InterruptedException {
		if(!users.containsKey(user.getUserID()))
			throw new UserNotFoundException();

		removeUser(user.getUserID());
		addUser(user);
	}

	public User getUser(String userID) throws UserNotFoundException, ExecutionException, InterruptedException, IOException {
		if(!users.containsKey(userID))
			throw new UserNotFoundException();

		DiscordFileMessage discordFileMessage = users.get(userID);
		ArrayList<byte[]> bytes = downloader.downloadFile(discordFileMessage.getUrls());
		File file = FileStitcher.stitchFile(bytes, discordFileMessage.getFilename());
		return JsonReader.readJson(file.getPath());
	}
}
