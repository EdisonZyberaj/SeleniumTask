package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.MenCategoryPage;
import pages.SignInPage;
import utils.TealiumMomentsPopup;

public class TestPageFilters extends BaseTest {

    @BeforeMethod
    public void setupTest() {
        navigateTo("https://ecommerce.tealiumdemo.com/");
        homePage.navigateToLogin().login("edisonzyberaj@gmail.com", "Edi12345");
        Assert.assertTrue(new SignInPage(driver).isLoginSuccessful(), "Failed to login!");
    }

    @Test
    public void testPageFilters() {
        MenCategoryPage menCategoryPage = homePage.navigateToMenCategory();
        new TealiumMomentsPopup(driver).handleIfPresent();

        menCategoryPage.clickBlackColorFilter();

        Assert.assertTrue(menCategoryPage.verifyAllProductsHaveBlackColorSelected(),
                "Not all products have black color with blue border");

        menCategoryPage.clickPriceFilter();

        Assert.assertTrue(menCategoryPage.verifyProductPriceMatchesCriteria(70.0),
                "Not all products match the price criteria");
    }
}
