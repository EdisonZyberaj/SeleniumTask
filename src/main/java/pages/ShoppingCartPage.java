package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.JavaScriptUtils;

import java.time.Duration;
import java.util.List;

public class ShoppingCartPage extends BasePage {

    private JavaScriptUtils javaScriptUtils;

    @FindBy(css = ".product-cart-actions input.qty")
    private List<WebElement> quantityInputs;

    @FindBy(css = ".product-cart-actions button[name='update_cart_action']")
    private List<WebElement> updateButtons;

    @FindBy(css = ".cart-totals .price")
    private WebElement grandTotalPrice;

    @FindBy(css = ".product-cart-price .price")
    private List<WebElement> productPrices;

    @FindBy(css = ".product-cart-total .price")
    private List<WebElement> productSubtotals;

    @FindBy(css = "td.a-center.product-cart-remove.last a.btn-remove")
    private List<WebElement> removeButtons;

    @FindBy(css = "div.cart-empty")
    private WebElement emptyCartMessage;

    @FindBy(css = "div.header-minicart span.count")
    private WebElement cartCountElement;

    @FindBy(css = "button#empty_cart_button")
    private WebElement emptyCartButton;

    public ShoppingCartPage(WebDriver driver) {
        super(driver);
        javaScriptUtils = new JavaScriptUtils(driver);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        PageFactory.initElements(driver, this);
    }

    public void updateQuantity(int index, int quantity) {
        try {
            if (index < quantityInputs.size()) {
                WebElement qtyInput = quantityInputs.get(index);
                javaScriptUtils.scrollToElement(qtyInput);
                qtyInput.clear();
                qtyInput.sendKeys(String.valueOf(quantity));

                WebElement updateButton = updateButtons.get(index);
                javaScriptUtils.scrollToElement(updateButton);
                click(updateButton);

                wait.until(ExpectedConditions.stalenessOf(qtyInput));
                PageFactory.initElements(driver, this);
            }
        } catch (Exception e) {
            System.err.println("Error updating quantity: " + e.getMessage());
        }
    }

    public double getGrandTotal() {
        try {
            javaScriptUtils.scrollToElement(grandTotalPrice);
            String totalText = grandTotalPrice.getText().replace("$", "").replace(",", "").trim();
            return Double.parseDouble(totalText);
        } catch (Exception e) {
            System.err.println("Error getting grand total: " + e.getMessage());
            return 0.0;
        }
    }

    public double getCalculatedTotal() {
        try {
            double total = 0.0;
            for (WebElement subtotal : productSubtotals) {
                String subtotalText = subtotal.getText().replace("$", "").replace(",", "").trim();
                total += Double.parseDouble(subtotalText);
            }
            return total;
        } catch (Exception e) {
            System.err.println("Error calculating total: " + e.getMessage());
            return 0.0;
        }
    }

    public boolean verifyTotalMatchesSum() {
        double grandTotal = getGrandTotal();
        double calculatedTotal = getCalculatedTotal();

        return Math.abs(grandTotal - calculatedTotal) == 0;
    }

    public int getCartItemCount() {
        try {
            PageFactory.initElements(driver, this);
            wait.until(ExpectedConditions.visibilityOf(cartCountElement));
            String countText = cartCountElement.getText().trim();

            if (countText.isEmpty()) {
                return 0;
            }

            return Integer.parseInt(countText);
        } catch (Exception e) {
            System.err.println("Error getting cart count: " + e.getMessage());
            return 0;
        }
    }
    public ShoppingCartPage removeFirstItem() {
        try {
            PageFactory.initElements(driver, this);
            if (!removeButtons.isEmpty()) {
                WebElement removeButton = removeButtons.get(0);
                javaScriptUtils.scrollToElement(removeButton);
                click(removeButton);

                wait.until(ExpectedConditions.stalenessOf(removeButton));
                PageFactory.initElements(driver, this);
            }
        } catch (Exception e) {
            System.err.println("Error removing item from cart: " + e.getMessage());
        }
        return this;
    }
    public boolean isCartEmpty() {
        try {
            PageFactory.initElements(driver, this);
            return emptyCartMessage.isDisplayed() &&
                    emptyCartMessage.getText().contains("You have no items in your shopping cart");
        } catch (Exception e) {
            return false;
        }
    }
    public String getEmptyCartMessage() {
        try {
            PageFactory.initElements(driver, this);
            return emptyCartMessage.getText().trim();
        } catch (Exception e) {
            return "";
        }
    }

}