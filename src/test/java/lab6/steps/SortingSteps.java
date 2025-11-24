package lab6.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lab6.driver.DriverManager;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;

import java.math.BigDecimal;
import java.util.*;

public class SortingSteps {

    private final WebDriver driver = DriverManager.getDriver();
    private List<String> originalOrder;
    private List<WebElement> getVisibleTiles() {
        List<WebElement> tiles = driver.findElements(By.cssSelector("div.men-pro-item.simpleCart_shelfItem"));
        List<WebElement> visible = new ArrayList<>();
        for (WebElement t : tiles) {
            if (t.isDisplayed()) visible.add(t);}
        return visible;
    }
    private String getName(WebElement tile) {
        return tile.findElement(By.cssSelector("h4 a")).getText().trim();
    }
    private BigDecimal getPrice(WebElement tile) {
        String txt = tile.findElement(By.cssSelector("span.item_price"))
                .getText()
                .replaceAll("[^0-9.]", "");
        return new BigDecimal(txt);
    }

    @And("I capture the original product order")
    public void captureOriginalOrder() {
        originalOrder = new ArrayList<>();
        for (WebElement tile : getVisibleTiles()) {
            originalOrder.add(getName(tile));}
        Assert.assertFalse("Empty product list!", originalOrder.isEmpty());
    }
    @When("I sort products by option {string}")
    public void sortProducts(String option) {
        Select select = new Select(driver.findElement(By.id("country1")));
        select.selectByVisibleText(option);
        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
    }
    @Then("products should be sorted by price ascending")
    public void verifyAscendingPrice() {
        List<BigDecimal> prices = new ArrayList<>();
        for (WebElement tile : getVisibleTiles()) {
            prices.add(getPrice(tile));
        }
        System.out.println("VISIBLE PRICES: " + prices);
        List<BigDecimal> sorted = new ArrayList<>(prices);
        Collections.sort(sorted);
        Assert.assertEquals("Prices NOT ASCENDING!", sorted, prices);
    }
    @Then("products should be sorted by price descending")
    public void verifyDescendingPrice() {
        List<BigDecimal> prices = new ArrayList<>();
        for (WebElement tile : getVisibleTiles()) {
            prices.add(getPrice(tile));}
        List<BigDecimal> sorted = new ArrayList<>(prices);
        sorted.sort(Collections.reverseOrder());
        Assert.assertEquals("Prices NOT DESCENDING!", sorted, prices);
    }
    @Then("products should be sorted by name ascending")
    public void verifyNameAscending() {
        List<String> names = new ArrayList<>();
        for (WebElement tile : getVisibleTiles()) {
            names.add(getName(tile));}
        List<String> sorted = new ArrayList<>(names);
        sorted.sort(String.CASE_INSENSITIVE_ORDER);
        Assert.assertEquals("Names NOT A-Z!", sorted, names);
    }
    @When("I reset the sort to default")
    public void resetSort() {
        new Select(driver.findElement(By.id("country1")))
                .selectByVisibleText("Default");
        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
    }
    @Then("the product list should be back to the original order")
    public void verifyOriginalOrder() {
        List<String> current = new ArrayList<>();
        for (WebElement tile : getVisibleTiles()) {
            current.add(getName(tile));}
        Assert.assertEquals("Order NOT restored!", originalOrder, current);
    }
}
