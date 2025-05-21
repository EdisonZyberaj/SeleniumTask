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
}