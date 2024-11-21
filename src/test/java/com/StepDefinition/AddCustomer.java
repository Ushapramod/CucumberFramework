package com.StepDefinition;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import com.runner.Runner;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class AddCustomer {
    private WebDriver driver;
    private WebDriverWait wait;

    public AddCustomer() {
        // Initialize WebDriver and WebDriverWait from the Runner class
        this.driver = Runner.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));  // Use 10 seconds for waits
    }

    @Given("I am logged in as a bank manager")
    public void i_am_logged_in_as_a_bank_manager() {
        // Navigate to the login page
        driver.get("https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login");

        // Wait until the page is loaded
        wait.until(ExpectedConditions.titleContains("XYZ Bank"));

        // Click on the "Bank Manager Login" button
        WebElement managerLoginButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[ng-click='manager()']")));
        managerLoginButton.click();

        // Wait for an element that confirms we're on the "Bank Manager" page (e.g., a dashboard button)
        WebElement managerDashboard = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[ng-class='btnClass1']")));  // Adjust the locator based on actual page
        Assert.assertTrue(managerDashboard.isDisplayed());  // Ensure we are on the correct page
    }

    @When("I navigate to the {string} page")
    public void i_navigate_to_the_page(String page) {
        // Click on the "Add Customer" button
        WebElement addCustomerButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[ng-click='addCust()']")));
        addCustomerButton.click();
    }

    @Then("I should see the {string} form with the following fields:")
    public void i_should_see_the_form_with_the_following_fields(String formName, DataTable dataTable) {
        // Check if the "Add Customer" form is displayed with the necessary fields
        Assert.assertTrue(driver.findElement(By.cssSelector("[ng-model='fName']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.cssSelector("[ng-model='lName']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.cssSelector("[ng-model='postCd']")).isDisplayed());
    }

    @When("I fill in the {string} field with {string}")
    public void i_fill_in_the_field_with(String fieldName, String value) {
        // Fill in the form based on field name
        WebElement field = null;
        
        switch (fieldName) {
            case "First Name":
                field = driver.findElement(By.cssSelector("[ng-model='fName']"));
                break;
            case "Last Name":
                field = driver.findElement(By.cssSelector("[ng-model='lName']"));
                break;
            case "Post Code":
                field = driver.findElement(By.cssSelector("[ng-model='postCd']"));
                break;
            default:
                Assert.fail("Unknown field name: " + fieldName);
        }

        // Type the value into the identified field
        field.sendKeys(value);
    }

    @When("I click the {string} button")
    public void i_click_the_button(String buttonName) throws InterruptedException {
        // Click the submit button to add the customer
        driver.findElement(By.cssSelector("[type='submit']")).click();
        Thread.sleep(4000);
    }

    @Then("I should see a confirmation message that the {string}")
    public void i_should_see_a_confirmation_message_that_the(String expectedMessage) {
        // Wait for the confirmation alert and verify the message
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        String actualText = alert.getText();
        Assert.assertTrue(actualText.contains(expectedMessage));
        alert.accept();  // Close the alert
        System.out.println("started");
        String pagesourec = driver.getPageSource();
        System.out.println(pagesourec);
        
        System.out.println("finished");
    }

    @Then("the customer {string} should be listed in the customer records")
    public void the_customer_should_be_listed_in_the_customer_records(String customerName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // Click on the Customers button to show the list
        WebElement customersButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[ng-class='btnClass3']")));
        customersButton.click();

        // Wait for the table to be visible
        WebElement customerTable = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//table[contains(@class, 'table') and contains(@class, 'table-bordered') and contains(@class, 'table-striped')]")
        ));

        // Extract and log the table's text content
        String tableText = customerTable.getText();
        
		/*
		 * for verifictaion System.out.println("Table Data: \n" + tableText);
		 * 
		 * // Now, check if the customer is in the table if
		 * (tableText.contains(customerName)) { System.out.println("Customer " +
		 * customerName + " found in the table."); } else {
		 * System.out.println("Customer " + customerName + " NOT found in the table.");
		 * }
		 */

        // Proceed with other operations if necessary
        // In this case, we will just assert if the name exists in the text
        Assert.assertTrue("Customer " + customerName + " should be listed.", tableText.contains(customerName));
    }

    //New scenario for adding a customer with invalid details

    @When("I fill in the {string} field with invalid value {string}")
    public void i_fill_in_the_invalid_field_with(String fieldName, String value) {
        WebElement field = null;
        
        switch (fieldName) {
            case "First Name":
                field = driver.findElement(By.cssSelector("[ng-model='fName']"));
                break;
            case "Last Name":
                field = driver.findElement(By.cssSelector("[ng-model='lName']"));
                break;
            case "Post Code":
                field = driver.findElement(By.cssSelector("[ng-model='postCd']"));
                break;
            default:
                Assert.fail("Unknown field name: " + fieldName);
        }

        // Enter the invalid value
        field.sendKeys(value);
    }

    @Then("I Should see a error message that the {string}")
    public void i_should_see_a_confirmation_message_that_the_invalid(String expectedMessage) {
        // Wait for the error message to appear (check for error messages specific to your app)
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[ng-show='customerForm.$invalid']"))); // Adjust locator
        String actualText = errorMessage.getText();
        Assert.assertTrue(actualText.contains(expectedMessage));
    }

    @Then("no customer should be listed in the customer records")
    public void no_customer_should_be_listed_in_the_customer_records() {
        WebElement customersButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[ng-class='btnClass3']")));
        customersButton.click();
        
        // Wait for the table to be visible and check if it's empty
        WebElement customerTable = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//table[contains(@class, 'table') and contains(@class, 'table-bordered') and contains(@class, 'table-striped')]")
        ));

        // Ensure that the table does not contain the invalid customer
        String tableText = customerTable.getText();
        Assert.assertFalse("No customer should be listed", tableText.contains("John Doe"));
    }

    
    
}
