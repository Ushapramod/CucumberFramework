Feature: Bank Manager Login

Scenario: Successful login as Bank Manager
    Given I am on the bank login page
    When I click on the "Bank Manager Login" button
    Then I should be redirected to the Bank Manager's dashboard
    And I should see options to "Add Customer", "Open Account", and "Customers" on the dashboard
