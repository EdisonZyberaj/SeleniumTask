package tests;

import pages.SignInPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.io.IOException;

public class SignInTest extends BaseTest {

    @Test(priority = 3)
    public void testValidLogin() throws IOException {
        navigateTo("https://ecommerce.tealiumdemo.com/customer/account/login/");
        SignInPage loginPage = new SignInPage(driver);

        String email = "Edi";
        String password = "Zyberi";

        loginPage.enterEmail(email);
        loginPage.enterPassword(password);
        loginPage.clickLogin();

        Assert.assertTrue(loginPage.isLoginSuccessful(), "Login failed!");
    }

    @Test(priority = 4)
    public void testInvalidLogin() {
        driver.get("https://ecommerce.tealiumdemo.com/customer/account/login/");
        SignInPage loginPage = new SignInPage(driver);

        loginPage.enterEmail("mock@mail.com");
        loginPage.enterPassword("123");
        loginPage.clickLogin();

        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error message not displayed!");
    }
}