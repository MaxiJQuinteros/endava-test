package services;

import io.restassured.http.ContentType;
import resources.enums.UserTypes;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class Users extends BaseService {
    private static String token;

    public static void createNewUser(String contactName) throws IOException {
        String jsonBody = generateStringFromResource(UserTypes.valueOf(contactName.toUpperCase()).getPath());
        given()
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .post(BASE_URI + "/users")
                .then().statusCode(201);
    }


    public static void deleteUser(String userType) {
        token = getAuthToken(userType);
        given()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .delete(BASE_URI + "/users/me")
                .then().statusCode(200);
    }
}
