package tests;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import pages.HomePage;
import utils.ConsentPopup;
import utils.TakeFailScreenshot;

public class BaseTest {
    protected WebDriver driver;
    protected BasePage basePage;
    protected ConsentPopup consentPopup;
    protected HomePage homePage;

    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        homePage = new HomePage(driver);
    }
//
//    @BeforeClass
//    public void setUp() {
//        WebDriverManager.chromedriver().setup();
//        driver = new ChromeDriver();
//        driver.manage().window().maximize();
//    }

    protected void navigateTo(String url) {
        driver.get(url);
        new ConsentPopup(driver).acceptIfPresent();
    }

    @AfterMethod
    public void takeScreenshotOnFailure(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            String timestamp = new java.text.SimpleDateFormat("yyyy-MM-dd_HH-mm-ss")
                    .format(new java.util.Date());
            String screenshotName = result.getName() + "_failed_" + timestamp;

            TakeFailScreenshot.takeScreenshot(driver, screenshotName);
            System.out.println("Test failed! Screenshot captured: " + screenshotName);
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
