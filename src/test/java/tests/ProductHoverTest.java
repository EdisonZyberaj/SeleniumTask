package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.ProductListingPage;
import pages.SignInPage;
import utils.TealiumMomentsPopup;

public class ProductHoverTest extends BaseTest {
    @BeforeMethod
    public void loginPreCondition() {
        navigateTo("https://ecommerce.tealiumdemo.com/");
        homePage.navigateToLogin().login("edisonzyberaj@gmail.com", "Edi12345");
        Assert.assertTrue(new SignInPage(driver).isLoginSuccessful(),"Failed to login!");
    }

    @Test(priority = 1)
    public void testHoverStyleOnProduct() {
        ProductListingPage productListingPage = homePage.navigateToWomenCategory();
        new TealiumMomentsPopup(driver).handleIfPresent();

        productListingPage.hoverOverProduct(0);
        Assert.assertTrue(productListingPage.isHoverStyleApplied(0),
                "Hover style is not applied to the product!");
    }
}