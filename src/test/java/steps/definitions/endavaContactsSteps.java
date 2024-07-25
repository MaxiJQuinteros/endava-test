package steps.definitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.After;
import org.junit.Assert;
import resources.enums.ContactsList;
import services.BaseService;
import services.Contacts;
import services.Users;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class endavaContactsSteps {

    private static String response;
    private static String userType;

    @Given("I am a {string} user")
    public void iAmAUser(String userType) throws IOException {
        endavaContactsSteps.userType = userType;
        Users.createNewUser(userType);
    }

    @When("I check the user contact list")
    public static void iCheckTheUserContactList() {
        response = Contacts.checkUserContactList(userType);
    }

    @Then("the contact list is empty")
    public void theContactListIsEmpty() {
        assertThat(response,is(equalTo("[\n    \n]")));
    }

    @When("I add a new contact {string}")
    public void iAddANewContact(String contactName) throws IOException {
        Contacts.addNewContact(userType, contactName);
    }

    @Then("the new contact information is displayed for {string}")
    public void theNewContactInformationIsDisplayed(String contactName) throws IOException {
        Assert.assertTrue(BaseService.compareJsonString(Contacts.trimContact(response),contactName));
    }

    @When("I edit an entire contact {string} with contact {string}")
    public void iEditAnEntireContact(String contactName, String editedContact) throws IOException {
        Contacts.editContact(userType, contactName, editedContact);
    }

    @Then("the contact new information is displayed for {string}")
    public void theContactNewInformationIsDisplayed(String contactName) throws IOException {
        Assert.assertEquals(response, Contacts.generateStringFromResource(ContactsList.valueOf(contactName.toUpperCase()).getPath()));
    }

    @When("I delete a contact {string}")
    public void iDeleteAContact(String contactName) {
        Contacts.deleteContact(userType, contactName);
    }

    @When("I delete all contacts")
    public void iDeleteAllContacts() {
        Contacts.deleteAllContacts(userType);
    }

    @After
    public void deleteUser() {
        Users.deleteUser(userType);
    }

}
