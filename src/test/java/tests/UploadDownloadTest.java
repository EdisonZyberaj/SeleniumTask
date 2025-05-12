package tests;

import com.pages.UploadDownloadPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;


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
        String filePath = System.getProperty("user.dir") + "/src/main/resources/photo.png";

        uploadDownloadPage.uploadFile(filePath)
                .downloadFile();

        File downloadedFile = new File(System.getProperty("user.home") + "/Downloads/sampleFile.jpeg");
        Assert.assertTrue(downloadedFile.exists(), "File was not downloaded");
    }
}