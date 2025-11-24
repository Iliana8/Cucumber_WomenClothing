package lab6.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lab6.driver.DriverManager;
import lab6.locators.WomenPageLocators;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PriceConsistencySteps {
    private final WebDriver driver = DriverManager.getDriver();
    private final Map<String, BigDecimal> rememberedPrices = new HashMap<>();
    private String currentProductName;
    @And("I remember the price for product {string}")
    public void rememberPriceForProduct(String productNameFromScenario) {
        WebElement tile;
        try {
            tile = driver.findElement(WomenPageLocators.productTileByName(productNameFromScenario));
        } catch (NoSuchElementException e) {
            List<WebElement> tiles = driver.findElements(WomenPageLocators.PRODUCT_TILES);
            if (tiles.isEmpty()) {
                throw new AssertionError("No product tiles were found on the Women Clothing page.");}
            tile = tiles.get(0);
        }
        WebElement nameElement;
        try {
            nameElement = tile.findElement(WomenPageLocators.PRODUCT_NAME_INSIDE_TILE);
        } catch (NoSuchElementException e) {
            nameElement = tile;}
        currentProductName = nameElement.getText().trim();
        WebElement priceElement = tile.findElement(WomenPageLocators.PRICE_INSIDE_TILE);
        BigDecimal price = parsePrice(priceElement.getText());
        rememberedPrices.put(currentProductName, price);
    }
    @When("I add the product {string} to the cart")
    public void iAddTheProductToTheCart(String productNameFromScenario) {
        String nameToUse = (currentProductName != null)
                ? currentProductName
                : productNameFromScenario;
        WebElement tile;
        try {
            tile = driver.findElement(WomenPageLocators.productTileByName(nameToUse));
        } catch (NoSuchElementException e) {
            List<WebElement> tiles = driver.findElements(WomenPageLocators.PRODUCT_TILES);
            if (tiles.isEmpty()) {
                throw new AssertionError("No product tiles were found on the Women Clothing page.");}
            tile = tiles.get(0);}
        List<WebElement> buttons = tile.findElements(
                By.cssSelector("input.button, input[type='submit'][name='submit']"));
        if (buttons.isEmpty()) {
            throw new AssertionError(
                    "Could not find an \"Add to cart\" type button inside the selected product tile for product: "
                            + nameToUse);}
        buttons.get(0).click();
    }
    private void openMiniCartIfNeeded() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        List<WebElement> overlays = driver.findElements(By.id("PPMiniCart"));
        if (!overlays.isEmpty() && overlays.get(0).isDisplayed()) {
            return;
        }
        WebElement cartButton = wait.until(
                ExpectedConditions.elementToBeClickable(By.cssSelector("button.w3view-cart, .w3view-cart"))
        );
        cartButton.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("PPMiniCart")));
    }
    @When("I open the Cart page")
    public void iOpenTheCartPage() {
        openMiniCartIfNeeded();
    }
    @Then("the price for product {string} in the cart should be the same as the remembered price")
    public void thePriceForProductInTheCartShouldBeTheSameAsTheRememberedPrice(String ignoredFromScenario) {
        if (currentProductName == null) {
            throw new IllegalStateException("No product name was remembered before checking the cart.");
        }
        BigDecimal remembered = rememberedPrices.get(currentProductName);
        if (remembered == null) {
            throw new IllegalStateException("No price was remembered for product: " + currentProductName);
        }
        openMiniCartIfNeeded();
        List<WebElement> priceSpans =
                driver.findElements(By.cssSelector("#PPMiniCart span.minicart-price"));
        if (priceSpans.isEmpty() || priceSpans.get(0).getText().trim().isEmpty()) {
            throw new AssertionError(
                    "Could not find any price in the mini cart for product: " + currentProductName);
        }
        WebElement priceElement = priceSpans.get(0);
        BigDecimal actual = parsePrice(priceElement.getText());
        Assert.assertEquals(
                "Price in cart is different from listing price for " + currentProductName,
                remembered,
                actual
        );
    }
    private BigDecimal parsePrice(String text) {
        String[] parts = text.split("[^0-9.,]+");
        String firstNumber = null;
        for (String p : parts) {
            if (!p.trim().isEmpty()) {
                firstNumber = p.trim();
                break;}}
        if (firstNumber == null) {
            throw new IllegalArgumentException("Cannot parse price from text: " + text);}
        String normalized = firstNumber.replace(",", ".");
        return new BigDecimal(normalized);
    }
}
