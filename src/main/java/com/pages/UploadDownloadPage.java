package com.pages;

import com.utils.JavaScriptUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class UploadDownloadPage extends BasePage {
    private By uploadFileInput = By.id("uploadFile");
    private By downloadButton = By.id("downloadButton");



    private JavaScriptUtils js;

    public UploadDownloadPage(WebDriver driver) {
        super(driver);
        this.js = new JavaScriptUtils(driver);
    }
//
//    public UploadDownloadPage navigateToPage() {
//        navigateTo("https://demoqa.com/upload-download"); // best practise
//        return this;
//    }

    public UploadDownloadPage uploadFile(String filePath) {
        try {
            WebElement uploadElement = driver.findElement(uploadFileInput);
            js.scrollToElement(uploadElement);
            uploadElement.sendKeys(filePath);
            BasePage.delay(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return this;
    }

    public UploadDownloadPage downloadFile() {
        try {
            WebElement downloadElement = driver.findElement(downloadButton);
            js.clickWithJS(downloadElement);
            // No need for extra click since clickWithJS already performs the click
            BasePage.delay(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return this;
    }
}