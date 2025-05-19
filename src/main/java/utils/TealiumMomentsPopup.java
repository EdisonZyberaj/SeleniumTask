package utils;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TealiumMomentsPopup {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavaScriptUtils javaScriptUtils;

    @FindBy(id="tealiumlabs_retail-21_inquiry5667_outerContainer")
    private WebElement popupContainer;

    @FindBy(id="tealiumlabs_retail-21_inquiry5667_question1_answer2")
    private WebElement casualOption;

    @FindBy(id="tealiumlabs_retail-21_inquiry5667_primaryButton")
    private WebElement submitButton;

    @FindBy(css = "#tealiumlabs_retail-21_inquiry5667_closeIcon")
    private WebElement closeButton;

    public TealiumMomentsPopup(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        this.javaScriptUtils = new JavaScriptUtils(driver);
        PageFactory.initElements(driver, this);
    }

    public boolean isDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(popupContainer)).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void handleIfPresent() {
        try {
            if (isDisplayed()) {
                if (casualOption.isDisplayed()) {
                    javaScriptUtils.clickWithJS(casualOption);
                }

                if (submitButton.isDisplayed()) {
                    submitButton.click();
                } else if (closeButton.isDisplayed()) {
                    closeButton.click();
                }

                wait.until(ExpectedConditions.invisibilityOf(popupContainer));
                System.out.println("Tealium Moments popup handled");
            }
        } catch (Exception e) {
            System.out.println("No Tealium Moments popup found or error handling it: " + e.getMessage());
        }
    }
}
