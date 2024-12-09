package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class ConfirmationPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By bookingDetails = By.cssSelector(".booking-confirmation-details");
    private By Indata = By.xpath("//*[@id=\"bodyconstraint-inner\"]/div[7]/div[3]/aside/div/div[2]/div[2]/div/div[2]/div[1]/div/time[1]");
    private By Outdata = By.xpath("//*[@id=\"bodyconstraint-inner\"]/div[7]/div[3]/aside/div/div[2]/div[2]/div/div[2]/div[1]/div/time[2]/div[1]");

    public ConfirmationPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    public void verifyBookingDetails() {
        try {
            WebElement details = wait.until(ExpectedConditions.visibilityOfElementLocated(bookingDetails));
            WebElement INDA = wait.until(ExpectedConditions.visibilityOfElementLocated(Indata));
            WebElement OUTD = wait.until(ExpectedConditions.visibilityOfElementLocated(Outdata));
            String text = details.getText();
            System.out.println(details.getText());
            System.out.println(INDA.getText());
            System.out.println(OUTD.getText());

            Assert.assertTrue(text.contains("Thu 19 Dec 2024"), "Check-in date not found in booking details.");
            Assert.assertTrue(text.contains("Sun 22 Dec 2024"), "Check-out date not found in booking details.");
            System.out.println("Booking details verified successfully.");
        } catch (Exception e) {
            Assert.fail("Failed to verify booking details: " + e.getMessage());
        }
    }
}
