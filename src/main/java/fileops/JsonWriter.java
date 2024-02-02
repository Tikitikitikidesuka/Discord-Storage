package fileops;

import com.fasterxml.jackson.databind.ObjectMapper;
import utils.User;

import java.io.File;
import java.io.IOException;

public class JsonWriter {

    public static void writeJson(User user) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new File(user.getUserID() + ".json"), user);
            System.out.println("JSON file created successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
