package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    private static final int TIMEOUT = 5;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
        PageFactory.initElements(driver, this);
    }

    protected void click(WebElement element) {
        try {
            waitForElementToBeClickable(element).click();
        } catch (Exception e) {
            System.err.println("Error clicking element: " + e.getMessage());
        }
    }

    protected String getText(WebElement element) {
        return waitForElementToBeVisible(element).getText();
    }

    protected WebElement waitForElementToBeVisible(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected WebElement waitForElementToBeClickable(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }
}