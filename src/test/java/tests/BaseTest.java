package tests;

import com.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.time.Duration;

public class BaseTest {
    protected WebDriver driver;
    protected BasePage basePage;

    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    /**
     * Base method to navigate to a page
     * Child classes will implement their own @BeforeMethod
     * to navigate to specific URLs
     */
    protected void navigateTo(String url) {
        driver.get(url);
    }

    @AfterClass
    public void tearDown() {
        BasePage.delay(2000);
        if (driver != null) {
            driver.quit();
        }
    }
}
