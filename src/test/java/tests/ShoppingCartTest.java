package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.*;
import utils.TealiumMomentsPopup;

public class ShoppingCartTest extends BaseTest {

    @BeforeMethod
    public void setupTest() {
        navigateTo("https://ecommerce.tealiumdemo.com/");
        homePage.navigateToLogin().login("edisonzyberaj@gmail.com", "Edi12345");
        Assert.assertTrue(new SignInPage(driver).isLoginSuccessful(), "Failed to login!");

        ProductListingPage productListingPage = homePage.navigateToWomenCategory();
        new TealiumMomentsPopup(driver).handleIfPresent();
        productListingPage.sortByPrice();
        productListingPage.addProductToWishlist(0);
        driver.navigate().back();
        productListingPage = homePage.navigateToWomenCategory();
        productListingPage.sortByPrice();
        productListingPage.addProductToWishlist(1);

        WishlistPage wishlistPage = homePage.navigateToWishlist();
        Assert.assertTrue(wishlistPage.isOnWishlistPage(), "Not on wishlist page");
        ProductDetailsPage productDetailsPage = wishlistPage.addItemToCart();
        productDetailsPage.addProductToCart();
        wishlistPage = homePage.navigateToWishlist();
        productDetailsPage = wishlistPage.addItemToCart();
        ShoppingCartPage cartPage = productDetailsPage.addProductToCart();
        System.out.println("Items added to cart: " + cartPage.getCartItemCount());
    }

    @Test
    public void testEmptyShoppingCart() {
        ShoppingCartPage cartPage = homePage.navigateToCart();

        int initialCount = cartPage.getCartItemCount();
        System.out.println("Initial cart count: " + initialCount);

        while (cartPage.getCartItemCount() > 0) {
            int currentCount = cartPage.getCartItemCount();
            System.out.println("Current cart count: " + currentCount);

            cartPage.removeFirstItem();

            if (currentCount > 1) {
                int newCount = cartPage.getCartItemCount();
                Assert.assertEquals(newCount, currentCount - 1,
                        "Cart count should decrease by 1 after deletion");
                System.out.println("Cart count after deletion: " + newCount);
            }
        }
        Assert.assertTrue(cartPage.isCartEmpty(), "Cart should be empty with message displayed");
        System.out.println("Successfully verified empty cart message: " + cartPage.getEmptyCartMessage());
    }
}
