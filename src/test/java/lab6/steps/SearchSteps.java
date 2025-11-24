package lab6.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.When;
import lab6.driver.DriverManager;
import lab6.locators.WomenPageLocators;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;
import java.util.Map;

public class SearchSteps {
    private final WebDriver driver = DriverManager.getDriver();
    @When("I search using the following terms")
    public void iSearchUsingTheFollowingTerms(DataTable table) {
        List<Map<String, String>> rows = table.asMaps(String.class, String.class);
        for (Map<String, String> row : rows) {
            String term = row.get("term");
            String expectation = row.get("expectation");
            WebElement input = driver.findElement(WomenPageLocators.SEARCH_INPUT);
            input.clear();
            input.sendKeys(term);
            driver.findElement(WomenPageLocators.SEARCH_BUTTON).click();
            List<WebElement> products = driver.findElements(WomenPageLocators.PRODUCT_TILES);
            if ("results".equalsIgnoreCase(expectation)) {
                Assert.assertTrue(
                        "Expected results for term: " + term,
                        products.size() > 0);
            } else if ("noResults".equalsIgnoreCase(expectation)) {
                boolean matchFound = false;
                for (WebElement product : products) {
                    String text = product.getText().toLowerCase();
                    if (text.contains(term.toLowerCase())) {
                        matchFound = true;
                        break;
                    }
                }
                Assert.assertFalse(
                        "Expected NO results for term: " + term + " but some products matched.",
                        matchFound
                );
            }
        }
    }
    @When("I submit an empty search")
    public void iSubmitAnEmptySearch() {
        WebElement input = driver.findElement(WomenPageLocators.SEARCH_INPUT);
        input.clear();
        driver.findElement(WomenPageLocators.SEARCH_BUTTON).click();
    }
}