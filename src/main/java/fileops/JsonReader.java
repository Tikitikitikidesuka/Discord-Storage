package fileops;

import com.fasterxml.jackson.databind.ObjectMapper;
import utils.User;

import java.io.File;
import java.io.IOException;

public class JsonReader {
    public static User readJson(String path) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            User temp = objectMapper.readValue(new File(path), User.class);
			return temp;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
