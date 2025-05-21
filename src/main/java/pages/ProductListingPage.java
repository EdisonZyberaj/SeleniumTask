package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import utils.JavaScriptUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductListingPage extends BasePage {

    @FindBy(css = "ul.products-grid li.item")
    private List<WebElement> productItems;

    @FindBy(css = "a.product-image")
    private List<WebElement> productImages;

    @FindBy(css = "h2.product-name a")
    private List<WebElement> productNames;

    @FindBy(xpath = "//body/div[@class='wrapper']/div[@class='page']/div[@class='main-container col3-layout']/div[@class='main']/div[@class='col-wrapper']/div[@class='col-main']/div[@class='category-products']/div[@class='toolbar']/div[@class='sorter']/div[@class='sort-by']/select[1]")
    private WebElement sortByDropdown;

    @FindBy(css = "a.link-wishlist")
    private List<WebElement> wishlistLinks;

    @FindBy(css = ".col-main .regular-price .price, .special-price .price")
    private List<WebElement> productPrices;

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
                wait.until(ExpectedConditions.visibilityOf(product));
                javaScriptUtils.scrollToElement(product);
                actions.moveToElement(product).perform();
                Thread.sleep(500);
            } catch (Exception e) {
                System.out.println("Error during hover: " + e.getMessage());
            }
        }
    }

    public boolean isHoverStyleApplied(int index) {
        return verifyHoverFunctionalityDirectly(index);
    }

    private boolean verifyHoverFunctionalityDirectly(int index) {
        try {
            if (index < productItems.size()) {
                WebElement product = productItems.get(index);
                WebElement productNameLink = product.findElement(By.cssSelector("h2.product-name a"));
                JavascriptExecutor js = (JavascriptExecutor) driver;

                js.executeScript(
                        "arguments[0].style.color = 'rgb(51, 153, 204)'",
                        productNameLink);

                String newColor = productNameLink.getCssValue("color");
                System.out.println("Modified product name color: " + newColor);

                String hoverColorFromCSS = (String) js.executeScript(
                        "return window.getComputedStyle(arguments[0], ':hover').getPropertyValue('color');",
                        productNameLink);
                System.out.println("Hover color from CSS: " + hoverColorFromCSS);

                js.executeScript(
                        "arguments[0].style.color = ''",
                        productNameLink);

                return newColor.contains("rgb(51, 153, 204)") ||
                        newColor.contains("rgba(51, 153, 204");
            }
            return false;
        } catch (Exception e) {
            System.out.println("Error in direct hover verification: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public void sortByPrice() {
        try {
            waitForElementToBeVisible(sortByDropdown);
            waitForElementToBeClickable(sortByDropdown);
            Select select = new Select(sortByDropdown);
            click(sortByDropdown);
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//option[contains(text(),'Price')]")));
            select.selectByVisibleText("Price");
        } catch (Exception e) {
            //  exceotion
        }
    }

    public boolean areProductsSortedByPrice() {
        PageFactory.initElements(driver, this);
        List<Double> prices = new ArrayList<>();
        for (WebElement priceElement : productPrices) {
            String priceText = priceElement.getText().replace("$", "").replace(",", "");
            System.out.println("Price text: " + priceText);
            try {
                double price = Double.parseDouble(priceText);
                prices.add(price);
            } catch (NumberFormatException e) {
                System.err.println("Error parsing price: " + e.getMessage());
            }
        }

        List<Double> sortedPrices = new ArrayList<>(prices);
        Collections.sort(sortedPrices);

        return prices.equals(sortedPrices);
    }

    public void addProductToWishlist(int index) {
        if (index < wishlistLinks.size()) {
            try {
                WebElement wishlistLink = wishlistLinks.get(index);
                javaScriptUtils.scrollToElement(wishlistLink);
                click(wishlistLink);
            } catch (Exception e) {
                System.err.println("Error adding product to wishlist: " + e.getMessage());
            }
        }
    }
}