import com.fasterxml.jackson.databind.ObjectMapper;
import fileops.FileSlicer;
import fileops.FileStitcher;
import sender.Downloader;
import sender.Sender;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {
		Sender sender = new Sender("https://discord.com/api/webhooks/1203027726647427133/dxTtB9Z9K5vDn-zKhNmlzg66YAUZ44oDbVhaazC7DZpP6iI1iRfqawf14_R0xVmlokGM");
		ArrayList<String> urls = sender.send(FileSlicer.sliceFile("./src/main/resources/aldeaoindemoniado.png"));
		FileStitcher.stitchFile(Downloader.downloadFile(urls), "filename.png");
    }
}