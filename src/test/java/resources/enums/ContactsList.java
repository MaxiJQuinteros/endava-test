package resources.enums;

import lombok.Getter;

public enum ContactsList {
    CIRILLA("src/test/java/resources/jsons/contacts/cirillaCat.JSON"),
    EDITEDCIRILLA("src/test/java/resources/jsons/contacts/editedCirillaCat.JSON"),
    SIMBA("src/test/java/resources/jsons/contacts/simbaCat.JSON");

    @Getter
    private String path;

    ContactsList(String path){
        this.path = path;
    }
}
