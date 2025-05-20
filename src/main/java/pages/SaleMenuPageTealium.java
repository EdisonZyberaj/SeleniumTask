package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.JavaScriptUtils;

import java.time.Duration;
import java.util.List;

public class SaleMenuPageTealium extends BasePage{
    private WebDriverWait wait;
    private JavaScriptUtils js;


    @FindBy(css = "ul.products-grid li.item")
    private List<WebElement> productItems;

    @FindBy(css = ".old-price")
    private WebElement originalPrices;

    @FindBy(css = ".special-price")
    private WebElement finalPrices;

    public SaleMenuPageTealium(WebDriver driver) {
        super(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(4));
        js = new JavaScriptUtils(driver);
        PageFactory.initElements(driver, this);
    }

    public List<WebElement> getProductItems()  {
        PageFactory.initElements(driver, this);
        wait.until(ExpectedConditions.visibilityOfAllElements(productItems));
        if (!productItems.isEmpty()) {
            try {
                js.scrollToElement(productItems.get(0));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return productItems;
    }

    public WebElement getOriginalPrice(WebElement productItem) {
        try {
            return productItem.findElement(By.cssSelector(".price-box .old-price"));
        } catch (StaleElementReferenceException e) {
            return driver.findElement(By.cssSelector(".price-box .old-price"));
        }
    }

    public WebElement getFinalPrice(WebElement productItem) {
        try {
            return productItem.findElement(By.cssSelector(".price-box .special-price"));
        } catch (StaleElementReferenceException e) {
            return driver.findElement(By.cssSelector(".price-box .special-price"));
        }
    }
    public boolean isStrikethrough(WebElement element) {
        try {
            WebElement priceElement = element;

            if (element.getAttribute("class").contains("old-price")) {
                priceElement = element.findElement(By.cssSelector(".price"));
            }
            String textDecorationLine = priceElement.getCssValue("text-decoration-line");
            System.out.println("Text decoration line: " + textDecorationLine);

            if (textDecorationLine != null && textDecorationLine.contains("line-through")) {
                return true;
            }
            String textDecoration = priceElement.getCssValue("text-decoration-l");
            System.out.println("Text decoration: " + textDecoration);

            return textDecoration != null && textDecoration.contains("line-through");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isGrey(WebElement element) {
        String color = element.getCssValue("color");
        return color.contains("160, 160, 160");
    }

    public boolean isBlue(WebElement element) {
        String color = element.getCssValue("color");
        return color.contains("51, 153, 204");
    }
}