package com.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
//import java.util.logging.FileHandler;
//
//import static jdk.jpackage.internal.WixAppImageFragmentBuilder.Component.File;

public class TakeFailScreenshot {

    WebDriver driver;

    @BeforeMethod
    public void setUp(){
//        WebDriverManager.chromeDriver().setup();
//        driver = new ChromeDriver();
//        driver.manage().window().maximize();
//        driver.get();
    }

    @AfterMethod
    public void takeScreenshotForFailures(){
        TakesScreenshot screenshot = (TakesScreenshot) driver;
        File source = screenshot.getScreenshotAs(OutputType.FILE);
        File dest = new File(System.getProperty("user.dir")+"/resources/screenshots/demo1.png");

        try {
//            FileHandler.copy(source, destination);
        }
        catch(Exception e){
            System.out.println("errorororor");
        }
    }
}
