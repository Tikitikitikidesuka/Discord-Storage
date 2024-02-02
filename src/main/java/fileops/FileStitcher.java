package fileops;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class FileStitcher {
	public static File stitchFile(ArrayList<byte[]> bytes, String path) {

		File file = new File(path);
		try (FileOutputStream outputStream = new FileOutputStream(file)) {
			for(byte[] b : bytes) {
				outputStream.write(b);
			}
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return file;
	}
}
