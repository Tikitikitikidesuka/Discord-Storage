package fileops;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileSlicer {
    public static List<byte[]> sliceFile(String inputFilePath) {
        List<byte[]> fileSlices = new ArrayList<>();
        File inputFile = new File(inputFilePath);
        int partSize = DSConfig.FILE_SIZE;

        try (FileInputStream inputStream = new FileInputStream(inputFile)) {
            byte[] buffer = new byte[partSize];
            int bytesRead = 0;
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            while ((bytesRead = inputStream.read(buffer)) > 0) {
                if (outputStream.size() + bytesRead > partSize) {
                    fileSlices.add(outputStream.toByteArray());
                    outputStream.reset();
                }
                outputStream.write(buffer, 0, bytesRead);

                if (outputStream.size() >= partSize) {
                    fileSlices.add(outputStream.toByteArray());
                    outputStream.reset();
                }
            }

            if (outputStream.size() > 0) {
                fileSlices.add(outputStream.toByteArray());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileSlices;
    }
}
