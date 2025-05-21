package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.JavaScriptUtils;

import java.time.Duration;

public class HomePage extends BasePage {

    private Actions actions;
    JavaScriptUtils javaScriptUtils;

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

    @FindBy(linkText = "SALE")
    private WebElement saleMenu;

    @FindBy(linkText = "View All Sale")
    private WebElement viewAllSaleLink;

    @FindBy(linkText = "MEN")
    private WebElement menMenu;

    @FindBy(linkText= "View All Men")
    private WebElement viewAllMenLink;

    @FindBy(css = "p.welcome-msg")
    private WebElement welcomeMessage;

    @FindBy(linkText = "Log Out")
    private WebElement logoutButton;

    @FindBy(xpath = "//span[@class='label'][normalize-space()='Account']")
    private WebElement accButton;

    @FindBy(partialLinkText = "My Wishlist")
    private WebElement accountWishlistLink;

    @FindBy(xpath = "//a[contains(@class,'no-count')]")
    private WebElement cartButton;

    public HomePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(4));
        actions = new Actions(driver);
        javaScriptUtils  = new JavaScriptUtils(driver);
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

    public WishlistPage navigateToWishlist() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(accButton));
            click(accButton);
            wait.until(ExpectedConditions.elementToBeClickable(accountWishlistLink));
            click(accountWishlistLink);
            return new WishlistPage(driver);
        } catch (Exception e) {
            System.err.println("Error navigating to wishlist: " + e.getMessage());
            return new WishlistPage(driver);
        }
    }

    public ShoppingCartPage navigateToCart() {
        try {
            click(cartButton);
            return new ShoppingCartPage(driver);
        } catch (Exception e) {
            System.err.println("Error navigating to cart: " + e.getMessage());
            driver.get("https://ecommerce.tealiumdemo.com/checkout/cart/");
            return new ShoppingCartPage(driver);
        }
    }
    public ProductListingPage navigateToWomenCategory() {
        actions.moveToElement(womenMenu).perform();
        waitForElementToBeVisible(viewAllWomenLink);
        click(viewAllWomenLink);
        return new ProductListingPage(driver);
    }
    public SaleMenuPageTealium navigateToSalePage() {
        actions.moveToElement(saleMenu).perform();
        waitForElementToBeVisible(viewAllSaleLink);
        click(viewAllSaleLink);
        return new SaleMenuPageTealium(driver);
    }
    public MenCategoryPage navigateToMenCategory() {
        actions.moveToElement(menMenu).perform();
        waitForElementToBeVisible(viewAllMenLink);
        click(viewAllMenLink);
        return new MenCategoryPage(driver);
    }
    public String getWelcomeMessage() {
        return waitForElementToBeVisible(welcomeMessage).getText();
    }
    public boolean isWishlistItemCountCorrect(int expectedCount) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(accButton));
            click(accButton);
            WebElement wishlistLink = wait.until(ExpectedConditions.visibilityOf(accountWishlistLink));
            String linkText = wishlistLink.getText();
            return linkText.contains("(" + expectedCount + " items)");
        } catch (Exception e) {
            System.err.println("Error checking wishlist count: " + e.getMessage());
            return false;
        }
    }
    public void clickAccount() {
        click(accountButton);
    }

}