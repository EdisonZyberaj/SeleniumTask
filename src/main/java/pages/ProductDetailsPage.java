package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.JavaScriptUtils;

import java.time.Duration;
import java.util.List;

public class ProductDetailsPage extends BasePage {

    WebDriverWait wait;

    private JavaScriptUtils javaScriptUtils;

    @FindBy(css = "ul#configurable_swatch_color li a.swatch-link")
    private List<WebElement> colorOptions;

    @FindBy(css = "ul#configurable_swatch_size li a.swatch-link")
    private List<WebElement> sizeOptions;

    @FindBy(css = "button[onclick='productAddToCartForm.submit(this)']")
    private WebElement addToCartButton;

    public ProductDetailsPage(WebDriver driver) {
        super(driver);
        javaScriptUtils = new JavaScriptUtils(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        PageFactory.initElements(driver, this);
    }

    public void selectFirstAvailableColor() {
        try {
            if (!colorOptions.isEmpty()) {
                WebElement color = colorOptions.get(0);
                javaScriptUtils.scrollToElement(color);
                click(color);
            }
        } catch (Exception e) {
            System.err.println("Error selecting color: " + e.getMessage());
        }
    }

    public void selectFirstAvailableSize() {
        try {
            if (!sizeOptions.isEmpty()) {
                for (WebElement size : sizeOptions) {
                    if (size.isDisplayed() && size.isEnabled()) {
                        javaScriptUtils.scrollToElement(size);
                        click(size);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error selecting size: " + e.getMessage());
        }
    }

    public ShoppingCartPage clickAddToCart() {
        try {
            javaScriptUtils.scrollToElement(addToCartButton);
            wait.until(ExpectedConditions.visibilityOf(addToCartButton));
            click(addToCartButton);
            return new ShoppingCartPage(driver);
        } catch (Exception e) {
            System.err.println("Error clicking add to cart: " + e.getMessage());
            return new ShoppingCartPage(driver);
        }
    }

    public ShoppingCartPage addProductToCart() {
        selectFirstAvailableColor();
        selectFirstAvailableSize();
        return clickAddToCart();
    }
}