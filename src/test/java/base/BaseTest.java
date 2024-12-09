package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import utils.DriverFactory;

public class BaseTest {
    protected WebDriver driver;

    @BeforeClass
    public void setUp() {
        // Automatically manage chromedriver version matching the browser
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://www.booking.com");
    }

    @AfterClass
    public void tearDown() {
        // Quit WebDriver
        DriverFactory.quitDriver();
    }
}
