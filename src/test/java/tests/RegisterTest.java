package tests;

import pages.RegisterPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.UUID;

public class RegisterTest extends BaseTest {

    @Test(priority = 1)
    public void testSuccessfulRegistration() {
        navigateTo("https://ecommerce.tealiumdemo.com/customer/account/create/");
        RegisterPage registerPage = new RegisterPage(driver);

        String uniqueEmail = "test_" + UUID.randomUUID().toString().substring(0, 8) + "@example.com";

        String firstName = "Test";
        String middleName = "User";
        String lastName = "Automation";
        String password = "Test@123";

        registerPage.fillRegistrationForm(
                firstName,
                middleName,
                lastName,
                uniqueEmail,
                password,
                password,
                true,
                false
        );

        registerPage.clickRegister();

        Assert.assertTrue(registerPage.isRegistrationSuccessful(),
                "Registration was not successful!");
    }

    @Test(priority = 2)
    public void testRegistrationWithExistingEmail() {
        navigateTo("https://ecommerce.tealiumdemo.com/customer/account/create/");
        RegisterPage registerPage = new RegisterPage(driver);

        String existingEmail = "edisonzyberaj@gmail.com";

        registerPage.fillRegistrationForm(
                "Test",
                "User",
                "Automation",
                existingEmail,
                "Test@123",
                "Test@123",
                false,
                false
        );

        registerPage.clickRegister();
        Assert.assertFalse(registerPage.isRegistrationSuccessful(),
                "Registration succeeded with an existing email!");
    }
}