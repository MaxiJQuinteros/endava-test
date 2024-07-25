package services;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import io.cucumber.core.internal.com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.JsonNode;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import resources.enums.ContactsList;
import resources.enums.UserTypes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;

public class BaseService {
    private static Response response;
    private static String username;
    private static String password;

    protected static final String BASE_URI = "https://thinking-tester-contact-list.herokuapp.com";

    public static String generateStringFromResource(String path) throws IOException {
        return new String(Files.readAllBytes(Paths.get(path)));
    }

    public static String getAuthToken(String userType) {
        username = UserTypes.valueOf(userType.toUpperCase()).getUsername();
        password = UserTypes.valueOf(userType.toUpperCase()).getPassword();
        String body = "{\"email\": \"" + username + "\", \"password\": \"" + password + "\"}";
        return given().contentType(ContentType.JSON).body(body).post(BASE_URI + "/users/login").then().statusCode(200).extract().response().jsonPath().getString("token");
    }

    public static Boolean compareJsonString(String response, String jsonName) throws IOException {
        String expected = generateStringFromResource(ContactsList.valueOf(jsonName.toUpperCase()).getPath());
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode1 = objectMapper.readTree(response);
        JsonNode jsonNode2 = objectMapper.readTree(expected);
        return jsonNode1.equals(jsonNode2);
    }
}
