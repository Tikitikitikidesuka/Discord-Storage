package fileops;

import com.fasterxml.jackson.databind.ObjectMapper;
import users.User;

import java.io.File;
import java.io.IOException;

public class JsonWriter {

    public static File writeJson(User user) {
        ObjectMapper objectMapper = new ObjectMapper();
		File file = new File(user.getUserID() + ".json");
        try {
            objectMapper.writeValue(file, user);
            System.out.println("JSON file created successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }

		return file;
    }
}
