package tests;

import pages.BasePage;
import pages.UploadDownloadPage;
import utils.TakeFailScreenshot;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;

// mock per te provuar pjesen e screenshot se si funksionon
public class UploadDownloadTest extends BaseTest {
    private UploadDownloadPage uploadDownloadPage;
    private static final String URL = "https://demoqa.com/upload-download";

    @BeforeMethod
    public void initPage() {
        navigateTo(URL);
        uploadDownloadPage = new UploadDownloadPage(driver);
    }

    @Test
    public void testFileUploadAndDownload() {

        File resourceFile = new File("src/main/resources/img1.png");
        String filePath = resourceFile.getAbsolutePath();

        uploadDownloadPage.uploadFile(filePath)
                .downloadFile();

        File downloadedFile = new File(System.getProperty("user.home") +
                File.separator + "Downloads" +
                File.separator + "sampleFile.jpeg");

        Assert.assertTrue(downloadedFile.exists(), "File was not downloaded");
    }

}