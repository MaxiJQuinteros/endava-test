package services;

import io.cucumber.core.internal.com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.SerializationFeature;
import io.restassured.http.ContentType;
import resources.enums.ContactsList;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;

public class Contacts extends BaseService {
    private static String token;

    public static String trimContact(String jsonResponse) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Map<String, Object>> contacts = objectMapper.readValue(jsonResponse, List.class);
        List<Map<String, Object>> modifiedContacts = contacts.stream().map(contact -> {
            contact.remove("_id");
            contact.remove("__v");
            contact.remove("owner");
            return contact;
        }).collect(Collectors.toList());
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        String finalResponse = objectMapper.writeValueAsString(modifiedContacts);
        if (finalResponse.startsWith("[")&&finalResponse.endsWith("]")){
            finalResponse = finalResponse.substring(2, finalResponse.length()-2);
        }
        return finalResponse;
    }

    public static String checkUserContactList(String userType) {
        token = getAuthToken(userType);
        String response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .get(BASE_URI + "/contacts")
                .then()
                .statusCode(200)
                .extract()
                .response()
                .getBody().prettyPrint();
        return response;
    }

    public static List<String> contactListIds(String userType) {
        token = getAuthToken(userType);
        return given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .get(BASE_URI + "/contacts")
                .then()
                .statusCode(200)
                .extract()
                .response()
                .jsonPath()
                .getList("_id");
    }

    public static String contactId(String userType, String contactName) {
        token = getAuthToken(userType);
        String contact = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .get(BASE_URI + "/contacts")
                .then()
                .statusCode(200)
                .extract()
                .response()
                .jsonPath()
                .getString("findAll { it.firstName == '" + contactName + "' }._id");
        return contact.substring(1, contact.length() - 1);
    }

    public static void addNewContact(String userType, String contactName) throws IOException {
        String jsonBody = generateStringFromResource(ContactsList.valueOf(contactName.toUpperCase()).getPath());
        token = getAuthToken(userType);
        given().contentType(ContentType.JSON).header("Authorization", token).body(jsonBody).post(BASE_URI + "/contacts").then().statusCode(201);
    }

    public static void editContact(String userType, String contactName, String editedContact) throws IOException {
        String jsonBody = generateStringFromResource(ContactsList.valueOf(editedContact.toUpperCase()).getPath());
        String contactId = contactId(userType, contactName);
        token = getAuthToken(userType);
        given().contentType(ContentType.JSON).header("Authorization", token).body(jsonBody).put(BASE_URI + "/contacts/" + contactId).then().statusCode(200);
    }

    public static void deleteAllContacts(String userType) {
        token = getAuthToken(userType);
        List<String> contacts = contactListIds(userType);
        if (!contacts.isEmpty()) {
            for (int i = 0; i <= contacts.size(); i++) {
                given()
                        .contentType(ContentType.JSON)
                        .header("Authorization", token)
                        .delete(BASE_URI + "/contacts/" + contacts.get(i))
                        .then()
                        .statusCode(200);
            }
        }
    }

    public static void deleteContact(String userType, String conctact) {
        token = getAuthToken(userType);
        String contactId = contactId(userType, conctact);

        given()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .delete(BASE_URI + "/contacts/" + contactId)
                .then()
                .statusCode(200);
    }
}

