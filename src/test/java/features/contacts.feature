Feature: Endava contacts test

  Scenario: Verify contact list on a new user
    Given I am a new user
    When I check my contacts list
    Then the list is empty

  Scenario: Verify adding a contact on a new user
    Given I am a new user
    When I add a new contact
    And I check my contacts list
    Then the new contact information is displayed

  Scenario: Verify the update of a contact
    Given I am a new user
    When I edit a contact
    And I check my contacts list
    Then the contact new information is displayed

  Scenario: Verify the deletion of contact
    Given I am a new user
    When I delete a contact
    And check my contacts list
    Then the list is empty