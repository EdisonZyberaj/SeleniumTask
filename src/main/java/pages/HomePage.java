package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage extends BasePage {

    private Actions actions;

    @FindBy(css = ".skip-link.skip-account")
    private WebElement accountButton;

    @FindBy(linkText = "Register")
    private WebElement registerLink;

    @FindBy(linkText = "Log In")
    private WebElement loginLink;

    @FindBy(linkText = "WOMEN")
    private WebElement womenMenu;

    @FindBy(linkText = "View All Women")
    private WebElement viewAllWomenLink;

    @FindBy(css = "p.welcome-msg")
    private WebElement welcomeMessage;

    @FindBy(linkText = "Log Out")
    private WebElement logoutButton;


    public HomePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(4));
        actions = new Actions(driver);
    }

    public RegisterPage navigateToRegister() {
        wait.until(ExpectedConditions.elementToBeClickable(accountButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(registerLink)).click();
        return new RegisterPage(driver);
    }

    public SignInPage navigateToLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(accountButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(loginLink)).click();
        return new SignInPage(driver);
    }
    public void navigateToLogout() {
        click(accountButton);
        click(logoutButton);
    }

    public ProductListingPage navigateToWomenCategory() {
        actions.moveToElement(womenMenu).perform();
        waitForElementToBeVisible(viewAllWomenLink);
        click(viewAllWomenLink);
        return new ProductListingPage(driver);
    }

    public String getWelcomeMessage() {
        return waitForElementToBeVisible(welcomeMessage).getText();
    }
}