package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.*;
import utils.TealiumMomentsPopup;

public class AddToCartTest extends BaseTest {

    @BeforeMethod
    public void setupTest() {
        navigateTo("https://ecommerce.tealiumdemo.com/");
        homePage.navigateToLogin().login("edisonzyberaj1@gmail.com", "Edi12345");
        Assert.assertTrue(new SignInPage(driver).isLoginSuccessful(), "Failed to login!");

        ProductListingPage productListingPage = homePage.navigateToWomenCategory();
        new TealiumMomentsPopup(driver).handleIfPresent();
        productListingPage.sortByPrice();
        productListingPage.addProductToWishlist(0);
        driver.navigate().back();
        productListingPage = homePage.navigateToWomenCategory();
        productListingPage.sortByPrice();
        productListingPage.addProductToWishlist(1); // precondition punon
        System.out.println("Precondition completed");
    }

    @Test
    public void testAddWishlistItemsToCartAndVerifyTotal() {
        WishlistPage wishlistPage = homePage.navigateToWishlist();
        Assert.assertTrue(wishlistPage.isOnWishlistPage(), "Not on wishlist page");

        ProductDetailsPage productDetailsPage = wishlistPage.addItemToCart();
        productDetailsPage.addProductToCart();

        wishlistPage = homePage.navigateToWishlist();
        productDetailsPage = wishlistPage.addItemToCart();
        ShoppingCartPage cartPage = productDetailsPage.addProductToCart();

        cartPage.updateQuantity(0, 2);
        cartPage.updateQuantity(1, 2);

        Assert.assertTrue(cartPage.verifyTotalMatchesSum(),
                "Grand total does not match the sum of product subtotals");
    }
}