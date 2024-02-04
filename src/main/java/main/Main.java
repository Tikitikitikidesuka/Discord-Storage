package main;

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
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.function.Predicate;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {
		Scanner scanner = new Scanner(System.in);
		Sender sender = new Sender(
				new ArrayList<>(Arrays.asList(
						"https://discord.com/api/webhooks/1203324098999091231/kmFIvDrf6K5KEKPV_SEHrIiZaUzJR4MQYKLds6hS3dKZsUAH2SSCADvVlOkqqLwbcmgX",
						"https://discord.com/api/webhooks/1203324274803347456/Oe3-hVLOlF49d4kSvW6sFwL_KQ3ILky5bFnHMF7sQEbY-pYWPmSW9MT2ydzCtM8QzwEu",
						"https://discord.com/api/webhooks/1203324378901774377/4k8tyO-3MBQ-usjQopyltNEpzZReh1T3bOSzgwJicNtk2fQID8g3_uUU-Q80KS2H5IAS",
						"https://discord.com/api/webhooks/1203324382987165696/vpKdSVXXiuxv0UnjhrdEOzvG8Exro8y1E_wAEcaCqpQC9zX2VJMa9w3kbp226lZ5vABD",
						"https://discord.com/api/webhooks/1203324393389035543/5m6okw9L_beJLpOIUW8O7DVHYFoBqvGFspBcSDLn6wgUDwPbnJk9Jqyrw5s641LxHZ_R",
						"https://discord.com/api/webhooks/1203324397079896125/npRFpJI6FS2uZUQBVMciqhUg12AHceoEpvX864bD-zs58G6B5NEJfJCzfbFhK_Fobpwb",
						"https://discord.com/api/webhooks/1203325273907531856/NNAsjU-Ya_X-n6srdAhDIw_cTQu_GulbUhDQiR0RHuov9E251lX8qAYhj154hUyu4u4I",
						"https://discord.com/api/webhooks/1203325383085396078/a_zXGhP9RLrkLdtxZGfsfj2vXl909pVIMOFh0gB31tDDnxTaq6bz4zxIDaA7Xz7JeUib",
						"https://discord.com/api/webhooks/1203325456091185172/fgvktgSCChglJWnk5EpB8_O8g-O7FDBphgEf0Ej5f9VZ_4r-zlD7k3NM6nJJ4YjxjVKD",
						"https://discord.com/api/webhooks/1203325533803380827/igDGi3EZnIS829jy9_dkyQ68DKIeYqFZo8vFFuf8mmu4DBsmw8mUJJegUbmuLANGzP7E"
				)));
		User luis = new User("luis");

		//DiscordFileMessage dfm1 = sender.send(FileSlicer.sliceFile(Config.PATH + "beetle-tank.png"), "beetle-tank.png");
		DiscordFileMessage dfm2 = sender.send(FileSlicer.sliceFile(Config.PATH + "movie.mp4"), "movie.mp4");

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
		FileStitcher.stitchFile(downloader.downloadFile(result.getUrls()), result.getFilename());

		sender.close();
    }
}