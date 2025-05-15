package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class TakeFailScreenshot {

    public static boolean takeScreenshot(WebDriver driver, String screenshotName) {

        if (driver == null) {
            return false;
        }

        try {

            File screenshotsDir = new File("resources/screenshots");
            if (!screenshotsDir.exists()) {
                screenshotsDir.mkdirs();
            }

            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            File destination = new File("resources/screenshots/" + screenshotName + ".png");

            Files.copy(screenshot.toPath(), destination.toPath());

            System.out.println("Screenshot saved: " + destination.getAbsolutePath());
            return true;

        } catch (IOException e) {
            System.out.println("Failed to take screenshot: " + e.getMessage());
            return false;
        }
    }
}