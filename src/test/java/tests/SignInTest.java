package tests;

import pages.SignInPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SignInTest extends BaseTest {

    @Test(priority = 1)
    public void testSignInWithRegisteredUser() {
        navigateTo("https://ecommerce.tealiumdemo.com/");

        SignInPage signInPage = homePage.navigateToLogin();

        String registeredEmail = "edisonzyberaj@gmail.com";
        String registeredPassword = "Edi12345";
        signInPage.login(registeredEmail, registeredPassword);

        Assert.assertTrue(signInPage.isLoginSuccessful(), "Login failed!");
        homePage.navigateToLogout();
    }

    @Test(priority = 2)
    public void testValidLogin() {
        navigateTo("https://ecommerce.tealiumdemo.com/customer/account/login/");
        SignInPage loginPage = new SignInPage(driver);

        String email = "edisonzyberaj@gmail.com";
        String password = "Edi12345";

        loginPage.login(email, password);

        Assert.assertTrue(loginPage.isLoginSuccessful(), "Login failed!");
    }

    @Test(priority = 3)
    public void testInvalidLogin() {
        navigateTo("https://ecommerce.tealiumdemo.com/customer/account/login/");
        SignInPage loginPage = new SignInPage(driver);

        loginPage.login("mock@gmail.com", "123");

        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error message not displayed!");
    }
}