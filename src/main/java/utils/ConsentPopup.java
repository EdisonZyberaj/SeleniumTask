package utils;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ConsentPopup {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(id = "_tealiumGDPRecModal")
    private WebElement popupContainer;

    @FindBy(id = "privacy_pref_optin")
    private WebElement optInRadio;

    @FindBy(id = "consent_prompt_submit")
    private WebElement submitButton;

    public ConsentPopup(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        PageFactory.initElements(driver, this);
    }

    public boolean isDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(popupContainer)).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void acceptIfPresent() {
        try {
            if (isDisplayed()) {
                optInRadio.click();
                submitButton.click();
                wait.until(ExpectedConditions.invisibilityOf(popupContainer));
                System.out.println("Consent popup accepted");
            }
        } catch (Exception e) {
            System.out.println("No consent popup found or error handling it: " + e.getMessage());
        }
    }
}