package resources.enums;

import lombok.Getter;

public enum UserTypes {
    BESKAR("beskar@fake.com", "myPassword", "src/test/java/resources/jsons/users/beskarCatUser.JSON");

    @Getter
    private String username;
    @Getter
    private String password;
    @Getter
    private String path;

    UserTypes(String username, String password, String path) {
        this.username = username;
        this.password = password;
        this.path = path;
    }

}
