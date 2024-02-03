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
						"https://discord.com/api/webhooks/1203027726647427133/dxTtB9Z9K5vDn-zKhNmlzg66YAUZ44oDbVhaazC7DZpP6iI1iRfqawf14_R0xVmlokGM",
						"https://discord.com/api/webhooks/1203108027981635634/CNe2-J12GtGz41YLUIE3qgIlb9e6FY8QRl7DfbMCOTzO6bs7xpCRYRzsuyhUXSMuX_gp",
						"https://discord.com/api/webhooks/1203108032394035200/FVQT18FcgexEWoYjA0_UMwo13r_OddVCGNQ2v7-wMNUwq-vrGUY9s6tKkBFtItjMwvqj",
						"https://discord.com/api/webhooks/1203108035564802078/MHOqA5vYHyZfJoonftzFjVKc__8arjQkeHZSQIWeFXVLR-nByVmLkkqyBCkq7acZ14gg",
						"https://discord.com/api/webhooks/1203108038920249415/w0uuF_4GfQIm9oL84XeS6tk72h72WP5jjuknOI5rKMcRmAlBaG4py3JY9A1mVyEvCFOE",
						"https://discord.com/api/webhooks/1203108042296524820/-z8I_cr6emRw7z3_kHtBtvTgE1PMZeIJv1uWjNRvmodpSv-mz8w2qVnuI99pXLrUEK5J",
						"https://discord.com/api/webhooks/1203108046251888681/i1z_XSZg1q40qJW80OT5yBdiStlFNGRvv43TNR0aD8R_IPP3-PrcPiA0EqZg8toIrL31",
						"https://discord.com/api/webhooks/1203108049649139712/I-fw0C5B-rpcLGaJa-JN8HRRp0OvMatznTqnkjPgGtevAZL8e27OPamCKC3t51NCU7s2",
						"https://discord.com/api/webhooks/1203108077549785109/8LBv9wsqrbo3PC5_5yPFwC30jRK2U1Lq1CM7036YmH2i-R9mB1p16z_yuKW4zVM3ILqU",
						"https://discord.com/api/webhooks/1203108079928090734/5gzMTX_J789y4gpeLJlz_o9ojpenVhu-8OeR2-w0cym-QwzGFTZ3YJtAMmhjU866nQ00"
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