package edu.ucsd.cse110.walkwalkrevolution.user;

import com.fasterxml.jackson.databind.ObjectMapper;

public class UserUtils {

    public static String serialize(User user) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(user);
    }

    public static User deserialize(String jsonString) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonString, User.class);
    }

}
