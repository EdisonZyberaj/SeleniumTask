package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.JavaScriptUtils;

public class RegisterPage extends BasePage {
    JavaScriptUtils javaScriptUtils;

    @FindBy(id = "firstname")
    private WebElement firstNameField;

    @FindBy(id = "middlename")
    private WebElement middleNameField;

    @FindBy(id = "lastname")
    private WebElement lastNameField;

    @FindBy(id = "email_address")
    private WebElement emailAddressField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(id = "confirmation")
    private WebElement confirmPasswordField;

    @FindBy(id = "is_subscribed")
    private WebElement isSubscribedCheckbox;

    @FindBy(xpath = "//input[@title='Remember Me']")
    private WebElement rememberMeCheckbox;

    @FindBy(xpath = "//button[@title='Register']")
    private WebElement registerButton;

    @FindBy(xpath = "//span[normalize-space()='Thank you for registering with Tealium Ecommerce.']")
    private WebElement confirmationMessage;

    @FindBy(xpath = "//a[normalize-space()='Log Out']")
    private WebElement logoutButton;

    public RegisterPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        javaScriptUtils = new JavaScriptUtils(driver);
    }

    public void enterFirstName(String firstName) {
        firstNameField.clear();
        firstNameField.sendKeys(firstName);
    }

    public void enterMiddleName(String middleName) {
        middleNameField.clear();
        middleNameField.sendKeys(middleName);
    }

    public void enterLastName(String lastName) {
        lastNameField.clear();
        lastNameField.sendKeys(lastName);
    }

    public void enterEmailAddress(String email) {
        emailAddressField.clear();
        emailAddressField.sendKeys(email);
    }

    public void enterPassword(String password) {
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    public void enterConfirmPassword(String confirmPassword) {
        confirmPasswordField.clear();
        confirmPasswordField.sendKeys(confirmPassword);
    }

    public void checkSubscribe(boolean subscribe) {
        if (subscribe && !isSubscribedCheckbox.isSelected()) {
            click(isSubscribedCheckbox);
        } else if (!subscribe && isSubscribedCheckbox.isSelected()) {
            click(isSubscribedCheckbox);
        }
    }

    public void checkRememberMe(boolean rememberMe) {
        if (rememberMe && !rememberMeCheckbox.isSelected()) {
            click(rememberMeCheckbox);
        } else if (!rememberMe && rememberMeCheckbox.isSelected()) {
            click(rememberMeCheckbox);
        }
    }

    public void clickRegister() {
        try {
            javaScriptUtils.scrollToElement(registerButton);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        click(registerButton);
    }

    public boolean isRegistrationSuccessful() {
        try {
            return waitForElementToBeVisible(confirmationMessage).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickLogout() {
        click(logoutButton);
    }

    public void fillRegistrationForm(String firstName, String middleName, String lastName,
                                     String email, String password, String confirmPassword,
                                     boolean subscribe, boolean rememberMe) {
        enterFirstName(firstName);
        enterMiddleName(middleName);
        enterLastName(lastName);
        enterEmailAddress(email);
        enterPassword(password);
        enterConfirmPassword(confirmPassword);
        checkSubscribe(subscribe);
        checkRememberMe(rememberMe);
    }
}