package pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.JavaScriptUtils;

import java.util.List;

public class ProductListingPage extends BasePage {

    @FindBy(css = "ul.products-grid li.item")
    private List<WebElement> productItems;

    @FindBy(css = "a.product-image")
    private List<WebElement> productImages;

    @FindBy(css = "button.button.btn-cart")
    private List<WebElement> addToCartButtons;

    @FindBy(css = "ul.products-grid li.item .actions")
    private List<WebElement> productActions;

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
                javaScriptUtils.scrollToElement(productItems.get(index));
                actions.moveToElement(productItems.get(index)).perform();
            } catch (Exception e) {
                System.out.println("Error during hover: " + e.getMessage());
            }
        }
    }

    public boolean isHoverStyleApplied(int index) {
        try {
            if (index < addToCartButtons.size() && addToCartButtons.get(index).isDisplayed()) {
                return true;
            }

            if (index < productItems.size()) {
                JavascriptExecutor js = (JavascriptExecutor) driver;

                if (index < productImages.size()) {
                    String borderColor = (String) js.executeScript(
                            "return window.getComputedStyle(arguments[0]).getPropertyValue('border-color');",
                            productImages.get(index));

                    if (borderColor != null &&
                            (borderColor.contains("rgb(0, 136, 204)") ||
                                    borderColor.contains("rgb(0,136,204)") ||
                                    borderColor.contains("#0088cc"))) {
                        return true;
                    }
                }
                if (index < productActions.size() && productActions.get(index).isDisplayed()) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            System.out.println("Error checking hover style: " + e.getMessage());
            return false;
        }
    }
}