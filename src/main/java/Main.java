import fileops.FileSlicer;
import sender.Sender;

import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

		Sender sender = new Sender("https://discord.com/api/webhooks/1203027726647427133/dxTtB9Z9K5vDn-zKhNmlzg66YAUZ44oDbVhaazC7DZpP6iI1iRfqawf14_R0xVmlokGM");
		FileSlicer fs = new FileSlicer();

		sender.send(fs.sliceFile("./src/main/resources/beetle-tank.png"));
    }
}