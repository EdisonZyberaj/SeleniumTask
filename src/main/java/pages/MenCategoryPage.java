package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.JavaScriptUtils;

import java.time.Duration;
import java.util.List;

public class MenCategoryPage  {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavaScriptUtils js;

    @FindBy(css = "ul.products-grid li.item")
    private List<WebElement> productItems;

    @FindBy(css = "img[title='Black']")
    private WebElement blackColorFilter;

    @FindBy(css = "a[href='https://ecommerce.tealiumdemo.com/men.html?color=20&price=70-']")
    private WebElement priceFilter;

    @FindBy(css = ".price-box .price")
    private List<WebElement> productPrices;

    public MenCategoryPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(4));
        this.js = new JavaScriptUtils(driver);
        PageFactory.initElements(driver, this);
    }

    public void clickBlackColorFilter() {
        try {
            js.scrollToElement(blackColorFilter);
            wait.until(ExpectedConditions.elementToBeClickable(blackColorFilter)).click();
            wait.until(ExpectedConditions.stalenessOf(productItems.get(0)));
            PageFactory.initElements(driver, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clickPriceFilter() {
        try {
            js.scrollToElement(priceFilter);
            priceFilter.click();
            PageFactory.initElements(driver, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean verifyAllProductsHaveBlackColorSelected() {
        for (WebElement product : productItems) {
            try {
                WebElement blackSwatch = product.findElement(By.xpath("//body/div[@class='wrapper']/div[@class='page']/div[@class='main-container col3-layout']/div[@class='main']/div[@class='col-wrapper']/div[@class='col-main']/div[@class='category-products']/ul[@class='products-grid products-grid--max-4-col first last odd']/li[1]/div[1]/ul[1]/li[1]/a[1]"));

                String borderColor = blackSwatch.getCssValue("border-left-color");

                if (!(borderColor.contains("(51, 153, 204, 1)"))) {
                    return false;
                }
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    public boolean verifyProductPriceMatchesCriteria(double minPrice) {
        try{
            js.scrollToElement(productItems.get(0));

        for(int i = 0; i < productPrices.size(); i++){

            String priceText = productPrices.get(i).getText().replace("$", "").replace(",", "");
            double price = Double.parseDouble(priceText);

            if(price < minPrice){
                return false;
            }
        }
        return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}