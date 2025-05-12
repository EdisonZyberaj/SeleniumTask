package com.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JavaScriptUtils {

    private final WebDriver driver;

    // Lazy loading:  By po ashtu    by-me dinamike
    // @FindBy(name = "firstname")
    //private WebElement firstName;
    // me Early binding


    public JavaScriptUtils(WebDriver driver) {
        this.driver = driver;
    }


    public  void clickWithJS(WebElement element) throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
        Thread.sleep(1000);
        js.executeScript("arguments[0].click();", element);
    }


    public void scrollToElement(WebElement element) throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
        Thread.sleep(1000);
    }
}