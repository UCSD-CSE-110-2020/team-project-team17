package edu.ucsd.cse110.walkwalkrevolution.route;

import com.fasterxml.jackson.databind.ObjectMapper;

public class RouteUtils {

    public static String serialize(Route route) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(route);
    }

    public static Route deserialize(String jsonString) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonString, Route.class);
    }
}
