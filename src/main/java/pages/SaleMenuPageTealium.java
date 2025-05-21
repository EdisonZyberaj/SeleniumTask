package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.JavaScriptUtils;

import java.time.Duration;
import java.util.List;

public class SaleMenuPageTealium extends BasePage {
    private WebDriverWait wait;
    private JavaScriptUtils js;

    @FindBy(css = "ul.products-grid li.item")
    private List<WebElement> productItems;

    public SaleMenuPageTealium(WebDriver driver) {
        super(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(4));
        js = new JavaScriptUtils(driver);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
        PageFactory.initElements(driver, this);
    }

    public List<WebElement> getProductItems() {
        PageFactory.initElements(driver, this);
        wait.until(ExpectedConditions.visibilityOfAllElements(productItems));
        if (!productItems.isEmpty()) {
            try {
                wait.until(ExpectedConditions.visibilityOf(productItems.get(0)));
                js.scrollToElement(productItems.get(0));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return productItems;
    }

    public WebElement getOriginalPrice(WebElement productItem) {
        try {

            WebElement priceBox = productItem.findElement(By.cssSelector(".price-box"));

            return priceBox.findElement(By.cssSelector(".old-price"));
        } catch (StaleElementReferenceException e) {

            PageFactory.initElements(driver, this);
            return driver.findElement(By.cssSelector(".price-box .old-price"));
        }
    }

    public WebElement getFinalPrice(WebElement productItem) {
        try {
            WebElement priceBox = productItem.findElement(By.cssSelector(".price-box"));
            return priceBox.findElement(By.cssSelector(".special-price"));
        } catch (StaleElementReferenceException e) {
            PageFactory.initElements(driver, this);
            return driver.findElement(By.cssSelector(".price-box .special-price"));
        }
    }

    public boolean isStrikethrough(WebElement element) {
        try {
            WebElement priceElement = element.findElement(By.cssSelector(".price"));
            String textDecoration = priceElement.getCssValue("text-decoration-line");

            return textDecoration.contains("line-through");
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isGrey(WebElement element) {
        try {
            WebElement priceElement = element.findElement(By.cssSelector(".price"));
            String color = priceElement.getCssValue("color");

            return color.contains("160, 160, 160") || color.contains("128, 128, 128");
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isBlue(WebElement element) {
        try {
            WebElement priceElement = element.findElement(By.cssSelector(".price"));
            String color = priceElement.getCssValue("color");

            return color.contains("51, 153, 204") || color.contains("46, 138, 184");
        } catch (Exception e) {
            return false;
        }
    }
}