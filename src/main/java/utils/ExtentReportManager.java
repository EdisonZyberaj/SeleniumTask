package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ExtentReportManager implements ITestListener {

    private ExtentReports extent;
    private ExtentTest test;
    private static String reportName;

    public void onStart(ITestContext context) {
        String timeStamp = java.time.LocalDateTime.now().toString().replace(":", "-").replace(".", "-");
        reportName = "Test-Report-" + timeStamp + ".html";

        ExtentSparkReporter htmlReporter = new ExtentSparkReporter("./reports/" + reportName);
        htmlReporter.config().setDocumentTitle("    Test Results");
        htmlReporter.config().setReportName("Test Results");
        htmlReporter.config().setTheme(Theme.STANDARD);

        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("Browser", "Chrome");
    }

    public void onTestStart(ITestResult result) {
        test = extent.createTest(result.getMethod().getMethodName());
    }

    public void onTestSuccess(ITestResult result) {
        test.pass("Test passed");
    }

    public void onTestFailure(ITestResult result) {
        test.fail(result.getThrowable());
    }

    public void onTestSkipped(ITestResult result) {
        test.skip("Test skipped");
    }

    public void onFinish(ITestContext context) {
        extent.flush();
    }
}