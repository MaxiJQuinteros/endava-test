Feature: Endava contacts test

  Scenario Outline: Verify contact list on a new user
    Given I am a "<userType>" user
    When I check the user contact list
    Then the contact list is empty
    Examples:
      | userType |
      | Beskar   |

  Scenario Outline: Verify adding a contact on a new user
    Given I am a "<userType>" user
    When I add a new contact "<contactName>"
    And I check the user contact list
    Then the new contact information is displayed for "<contactName>"
    Examples:
      | userType | contactName |
      | Beskar   | Cirilla     |

  Scenario Outline: Verify the update of a contact
    Given I am a "<userType>" user
    When I add a new contact "<contactName>"
    And I edit an entire contact "<contactName>" with contact "<editedContact>"
    And I check the user contact list
    Then the new contact information is displayed for "<editedContact>"
    Examples:
      | userType | contactName | editedContact |
      | Beskar   | Cirilla     | editedCirilla |

  Scenario Outline: Verify the deletion of a contact
    Given I am a "<userType>" user
    When I add a new contact "<contactName>"
    And I delete a contact "<contactName>"
    And I check the user contact list
    Then the contact list is empty
    Examples:
      | userType | contactName |
      | Beskar   | Cirilla     |