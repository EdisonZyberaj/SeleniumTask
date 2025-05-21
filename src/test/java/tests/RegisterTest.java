package tests;

import org.testng.annotations.Listeners;
import pages.RegisterPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.UUID;

@Listeners(utils.ExtentReportManager.class)
public class RegisterTest extends BaseTest {

    @Test(priority = 1)
    public void testSuccessfulRegistration() {
        navigateTo("https://ecommerce.tealiumdemo.com/");
        RegisterPage registerPage = homePage.navigateToRegister();

        Assert.assertTrue(registerPage.isPageLoaded(), "Register page is not loaded.");

        String firstName = "Test";
        String middleName = "User";
        String lastName = "Automation";
        String registeredEmail = "test_" + UUID.randomUUID().toString().substring(0, 8) + "@example.com";
        String registeredPassword = "Test@123";
        boolean subscribe = true;
        boolean rememberMe = false;

        registerPage.fillRegistrationForm(
                firstName,
                middleName,
                lastName,
                registeredEmail,
                registeredPassword,
                registeredPassword,
                subscribe,
                rememberMe
        );

        registerPage.clickRegister();

        Assert.assertTrue(registerPage.isRegistrationSuccessful(),
                "Registration was not successful!");

        homePage.navigateToLogout();
    }

    @Test(priority = 2)
    public void testRegistrationWithExistingEmail() {
        navigateTo("https://ecommerce.tealiumdemo.com/");
        RegisterPage registerPage = homePage.navigateToRegister();

        Assert.assertTrue(registerPage.isPageLoaded(), "Register page is not loaded.");

        String firstName = "Test";
        String middleName = "User";
        String lastName = "Automation";
        String existingEmail = "edisonzyberaj@gmail.com";
        String password = "Test@123";

        registerPage.fillRegistrationForm(
                firstName,
                middleName,
                lastName,
                existingEmail,
                password,
                password,
                false,
                false
        );

        registerPage.clickRegister();

        Assert.assertFalse(registerPage.isRegistrationSuccessful(),
                "Registration succeeded with an existing email!");
    }
    @Test(priority = 3)
    public void testRegistrationWithVoidInput() {
        navigateTo("https://ecommerce.tealiumdemo.com/");
        RegisterPage registerPage = homePage.navigateToRegister();

        Assert.assertTrue(registerPage.isPageLoaded(), "Register page is not loaded.");

        String emptyInput = "";
        registerPage.fillRegistrationForm(
                emptyInput,
                emptyInput,
                emptyInput,
                emptyInput,
                emptyInput,
                emptyInput,
                false,
                false
        );

        registerPage.clickRegister();

        Assert.assertFalse(registerPage.isRegistrationSuccessful(),
                "Registration succeeded with void input!");
    }
}