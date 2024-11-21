package com.StepDefinition;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.runner.Runner;  // Import the Runner class to access WebDriver

import java.time.Duration;

public class BankManagerLogin {

    private WebDriver driver = Runner.getDriver();  // Use the shared WebDriver instance

    @Given("I am on the bank login page")
    public void i_am_on_the_bank_login_page() {
        driver.get("https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login");
        System.out.println("Page Title: " + driver.getTitle());
        System.out.println("Current URL: " + driver.getCurrentUrl());
    }

    @When("I click on the {string} button")
    public void i_click_on_the_button(String buttonName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement managerLoginButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[ng-click='manager()']")));
        managerLoginButton.click();
    }

    @Then("I should be redirected to the Bank Manager's dashboard")
    public void i_should_be_redirected_to_the_bank_manager_s_dashboard() {
        String expectedTitle = "XYZ Bank";  // Update this to the actual expected title
        Assert.assertEquals(driver.getTitle(), expectedTitle);
    }

    @Then("I should see options to {string}, {string}, and {string} on the dashboard")
    public void i_should_see_options_to_and_on_the_dashboard(String option1, String option2, String option3) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Check if each option is displayed on the dashboard
        Assert.assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[ng-class='btnClass1']"))).isDisplayed());
        Assert.assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[ng-class='btnClass2']"))).isDisplayed());
        Assert.assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[ng-class='btnClass3']"))).isDisplayed());
    }
}
