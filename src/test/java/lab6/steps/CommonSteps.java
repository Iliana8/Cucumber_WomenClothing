package lab6.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lab6.driver.DriverManager;
import lab6.locators.WomenPageLocators;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
public class CommonSteps {

    private final WebDriver driver = DriverManager.getDriver();
    private String rememberedUrl;
    @Given("I am on the Women Clothing page")
    public void iAmOnWomenClothingPage() {
        driver.get(WomenPageLocators.WOMEN_PAGE_URL);
    }
    @Given("I am on the main page")
    public void iAmOnTheMainPage() {
        driver.get(WomenPageLocators.MAIN_PAGE_URL);
    }
    @When("I refresh the current page")
    public void iRefreshTheCurrentPage() {
        driver.navigate().refresh();
    }
    @Given("I remember the current page URL")
    public void iRememberTheCurrentPageURL() {
        rememberedUrl = driver.getCurrentUrl();
    }
    @Then("the current page URL should be the remembered one")
    public void theCurrentPageURLShouldBeTheRememberedOne() {
        Assert.assertEquals("URL should not change after empty search", rememberedUrl, driver.getCurrentUrl());
    }
}
