package lab6.steps;

import io.cucumber.java.en.Then;
import lab6.driver.DriverManager;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class DuplicateProductSteps {
    private final WebDriver driver = DriverManager.getDriver();
    @Then("product {string} should appear only once in the product list")
    public void productShouldAppearOnlyOnceInTheProductList(String name) {
        List<WebElement> elements = driver.findElements(
                By.xpath("//div[contains(@class,'men-pro-item') " +
                        "and .//a[normalize-space()='" + name + "']]"));
        System.out.println("---- DUPLICATE PRODUCT CHECK ----");
        System.out.println("Searching for product: " + name);
        System.out.println("Number of matches found: " + elements.size());
        for (int i = 0; i < elements.size(); i++) {
            System.out.println("Match #" + (i + 1) + ": " + elements.get(i).getText());
        }
        System.out.println("--------------------------------");
        Assert.assertEquals(
                "Product '" + name + "' appears multiple times! Expected: 1 | Found: "
                        + elements.size(),
                1,
                elements.size()
        );
    }
}
