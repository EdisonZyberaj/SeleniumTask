package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.JavaScriptUtils;

import java.time.Duration;

public class WishlistPage extends BasePage {
    private JavaScriptUtils javaScriptUtils;

    private final By addToCartButton = By.cssSelector("div.cart-cell button");

    public WishlistPage(WebDriver driver) {
        super(driver);
        javaScriptUtils = new JavaScriptUtils(driver);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        PageFactory.initElements(driver, this);
    }

    public boolean isOnWishlistPage() {
        return wait.until(ExpectedConditions.titleContains("My Wishlist"));
    }

    public ProductDetailsPage addItemToCart() {
        try {
            WebElement button = wait.until(ExpectedConditions.elementToBeClickable(addToCartButton));

            javaScriptUtils.scrollToElement(button);
            button.click();

            return new ProductDetailsPage(driver);
        } catch (Exception e) {

            System.err.println("Retry failed: ");
        }
        return null;
    }
}