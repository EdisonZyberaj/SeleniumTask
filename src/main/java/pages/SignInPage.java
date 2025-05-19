package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.JavaScriptUtils;

public class SignInPage extends BasePage {

    JavaScriptUtils javaScriptUtils;

    @FindBy(id = "email")
    private WebElement emailField;

    @FindBy(id = "pass")
    private WebElement passwordField;

    @FindBy(id = "send2")
    private WebElement loginButton;

    @FindBy(xpath = "//span[contains(text(), 'Invalid login or password.')]")
    private WebElement errorMessage;

    @FindBy(css = "p[class='hello'] strong")
    private WebElement welcomeMessage;


    public SignInPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        javaScriptUtils = new JavaScriptUtils(driver);
    }

    private void enterEmail(String email) {
        wait.until(ExpectedConditions.visibilityOf(emailField));
        emailField.clear();
        emailField.sendKeys(email);
    }

    private void enterPassword(String password) {
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    private void clickLogin() {
        try {
            javaScriptUtils.scrollToElement(loginButton);
            click(loginButton);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isLoginSuccessful() {
        return driver.getCurrentUrl().contains("customer/account")&&
                welcomeMessage.getText().contains("Hello,");
    }

    public boolean isErrorMessageDisplayed() {
        try {
            System.out.println("Error message: " + errorMessage.getText());
            return errorMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    public void login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickLogin();
    }
}