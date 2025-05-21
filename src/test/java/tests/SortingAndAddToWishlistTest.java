package tests;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.ProductListingPage;
import pages.SignInPage;
import utils.TealiumMomentsPopup;

public class SortingAndAddToWishlistTest extends BaseTest {

    @BeforeMethod
    public void setupTest() {
        navigateTo("https://ecommerce.tealiumdemo.com/");
        homePage.navigateToLogin().login("edisonzyberaj@gmail.com", "Edi12345");
        Assert.assertTrue(new SignInPage(driver).isLoginSuccessful(), "Failed to login!");
    }

    @Test
    public void testSortingAndWishlist() {
        ProductListingPage productListingPage = homePage.navigateToWomenCategory();
        new TealiumMomentsPopup(driver).handleIfPresent();

        productListingPage.sortByPrice();
        Assert.assertTrue(productListingPage.areProductsSortedByPrice(),
                "Products are not sorted by price correctly");

        productListingPage.addProductToWishlist(0);
        driver.navigate().back();
        productListingPage = homePage.navigateToWomenCategory();
        productListingPage.sortByPrice();

        productListingPage.addProductToWishlist(1);

        Assert.assertTrue(homePage.isWishlistItemCountCorrect(2),
                "Wishlist item count is not correct");
    }
}