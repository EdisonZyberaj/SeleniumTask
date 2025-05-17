package tests;

import pages.SignInPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SignInTest extends BaseTest {

    @Test(priority = 1)
    public void testValidLogin() {
        navigateTo("https://ecommerce.tealiumdemo.com/customer/account/login/");
        SignInPage loginPage = new SignInPage(driver);

        String email = "edisonzyberaj@gmail.com";
        String password = "Edi12345";

        loginPage.enterEmail(email);
        loginPage.enterPassword(password);
        loginPage.clickLogin();

        Assert.assertTrue(loginPage.isLoginSuccessful(), "Login failed!");
    }

    @Test(priority = 2)
    public void testInvalidLogin()  {
        navigateTo("https://ecommerce.tealiumdemo.com/customer/account/login/");
        SignInPage loginPage = new SignInPage(driver);

        loginPage.enterEmail("mock@mail.com");
        loginPage.enterPassword("123");
        loginPage.clickLogin();

        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error message not displayed!");
    }
}