package tests.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.util.concurrent.TimeUnit;

public class BaseTest {

    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {
        // Set path to your chromedriver (or ensure it's in the system path)
        System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");

        // Initialize Chrome options if needed (for example, to disable notifications)
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");

        // Initialize WebDriver
        driver = new ChromeDriver(options);

        // Configure timeouts
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);

        // Open the website
        driver.get("https://www.booking.com/");
    }

    @AfterMethod
    public void tearDown() {
        // Quit the driver after test execution
        if (driver != null) {
            driver.quit();
        }
    }
}
