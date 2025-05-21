package tests;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.SaleMenuPageTealium;
import pages.SignInPage;
import utils.TealiumMomentsPopup;

public class SaleProductStyleTest extends BaseTest {

    @BeforeMethod
    public void setupTest() {
        navigateTo("https://ecommerce.tealiumdemo.com/");
        homePage.navigateToLogin().login("edisonzyberaj@gmail.com", "Edi12345");
        Assert.assertTrue(new SignInPage(driver).isLoginSuccessful(), "Failed to login!");
    }

    @Test
    public void testSaleProductsStyle() {
        SaleMenuPageTealium saleMenuPageTealium = homePage.navigateToSalePage();

        new TealiumMomentsPopup(driver).handleIfPresent();

        for (WebElement productItem : saleMenuPageTealium.getProductItems()) {
            WebElement originalPrice = saleMenuPageTealium.getOriginalPrice(productItem);
            WebElement finalPrice = saleMenuPageTealium.getFinalPrice(productItem);

            Assert.assertTrue(saleMenuPageTealium.isGrey(originalPrice),"Original price is not grey.");
            Assert.assertTrue( saleMenuPageTealium.isStrikethrough(originalPrice),"Original price is not strikethrough.");

            Assert.assertTrue( saleMenuPageTealium.isBlue(finalPrice),"Final price is not blue.");
            Assert.assertFalse( saleMenuPageTealium.isStrikethrough(finalPrice),"Final price is strikethrough.");
        }
    }
}