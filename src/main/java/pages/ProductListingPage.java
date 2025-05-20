package pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.JavaScriptUtils;

import java.time.Duration;
import java.util.List;

public class ProductListingPage extends BasePage {

    @FindBy(css = "ul.products-grid li.item")
    private List<WebElement> productItems;

    @FindBy(css = "a.product-image")
    private List<WebElement> productImages;

    @FindBy(css = "h2.product-name a")
    private List<WebElement> productNames;

    private static final String HOVER_BLUE_COLOR = "rgb(51, 153, 204)";

    private Actions actions;
    private JavaScriptUtils javaScriptUtils;

    public ProductListingPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        actions = new Actions(driver);
        javaScriptUtils = new JavaScriptUtils(driver);
    }


    public void hoverOverProduct(int index) {
        if (index < productItems.size()) {
            try {
                WebElement product = productItems.get(index);
                new WebDriverWait(driver, Duration.ofSeconds(2))
                        .until(ExpectedConditions.visibilityOf(product));
                javaScriptUtils.scrollToElement(product);
                actions.moveToElement(product).perform();
            } catch (Exception e) {
                System.out.println("Error during hover: " + e.getMessage());
            }
        }
    }

    public boolean isHoverStyleApplied(int index) {
        try {
            if (index < productItems.size()) {
                WebElement product = productItems.get(index);
                new WebDriverWait(driver, Duration.ofSeconds(2))
                        .until(ExpectedConditions.visibilityOf(product));

                JavascriptExecutor js = (JavascriptExecutor) driver;

                if (index < productImages.size()) {
                    String borderColor = (String) js.executeScript(
                            "return window.getComputedStyle(arguments[0]).getPropertyValue('border-color');",
                            productImages.get(index));

                    System.out.println("Image border color: " + borderColor);

                    if (borderColor != null && borderColor.equals(HOVER_BLUE_COLOR)) {
                        return true;
                    }
                }
                if (index < productNames.size()) {
                    String textColor = (String) js.executeScript(
                            "return window.getComputedStyle(arguments[0]).getPropertyValue('color');",
                            productNames.get(index));

                    System.out.println("Product name color: " + textColor);

                    if (textColor != null && textColor.equals(HOVER_BLUE_COLOR)) {
                        return true;
                    }
                }
            }
            return false;
        } catch (Exception e) {
            System.out.println("Error checking hover style: " + e.getMessage());
            return false;
        }
    }
}