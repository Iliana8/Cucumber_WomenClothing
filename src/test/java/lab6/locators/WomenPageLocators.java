package lab6.locators;
import org.openqa.selenium.By;

public class WomenPageLocators {

    public static final String WOMEN_PAGE_URL = "https://loving-hermann-e2094b.netlify.app/womens.html";
    public static final String MAIN_PAGE_URL  = "https://loving-hermann-e2094b.netlify.app/";
    public static final By PRODUCT_TILES =
            By.cssSelector("div.col-md-3.product-men");
    public static final By PRODUCT_NAME_INSIDE_TILE =
            By.cssSelector("h4");
    public static final By PRICE_INSIDE_TILE =
            By.cssSelector("span.item_price");
    public static final By SEARCH_INPUT =
            By.cssSelector("input[type='search'], input[name*='search'], " +
                    "input[placeholder*='Search'], input[placeholder*='search']");
    public static final By SEARCH_BUTTON =
            By.cssSelector("button[type='submit'], button[class*='search'], " +
                    "button[aria-label*='search'], button[aria-label*='Search']");
    public static By productTileByName(String name) {
        return By.xpath(
                "//div[contains(@class,'col-md-3') and contains(@class,'product-men')]" +
                        "[.//h4[contains(normalize-space(),'" + name + "')]]"
        );
    }


}
