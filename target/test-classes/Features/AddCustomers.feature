Feature: Add Customer functionality

  As a bank manager,
  I want to be able to add a new customer with their details
  So that I can successfully create a customer in the system

  Scenario: Adding a new customer with valid details
    Given I am logged in as a bank manager
    When I navigate to the "Add Customer" page
    Then I should see the "Add Customer" form with the following fields:
      | First Name  | Last Name  | Post Code |
    When I fill in the "First Name" field with "John"
    And I fill in the "Last Name" field with "Doe"
    And I fill in the "Post Code" field with "12345"
    And I click the "Add Customer" button
    Then I should see a confirmation message that the "Customer added successfully"
    And the customer "John Doe" should be listed in the customer records

     # New scenario for adding a customer with invalid details
    Scenario: Adding a customer with invalid details
    Given I am logged in as a bank manager
    When I navigate to the "Add Customer" page
    Then I should see the "Add Customer" form with the following fields:
      | First Name  | Last Name  | Post Code |
    When I fill in the "First Name" field with "John"
    And I fill in the "Last Name" field with ""
    And I fill in the "Post Code" field with "invalid"
    When I click the "Submit" button
    Then I should see a confirmation message that the "Form is invalid"
    And no customer should be listed in the customer records