package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class ConfirmationPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators for check-in and check-out elements
    private By checkInDate = By.xpath("//div[contains(text(),'Thu, Dec 19, 2024')]");
    private By checkOutDate = By.xpath("//div[contains(text(),'Sun, Dec 22, 2024')]");

    // Constructor
    public ConfirmationPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Method to assert that the check-in and check-out dates are displayed correctly
    public void verifyBookingDates() {
        try {
            // Wait for the check-in and check-out elements to be visible
            WebElement checkInElement = wait.until(ExpectedConditions.visibilityOfElementLocated(checkInDate));
            WebElement checkOutElement = wait.until(ExpectedConditions.visibilityOfElementLocated(checkOutDate));

            // Assert that the text matches the expected dates
            String expectedCheckIn = "Thu, Dec 19, 2024";
            String expectedCheckOut = "Sun, Dec 22, 2024";

            String actualCheckIn = checkInElement.getText();
            String actualCheckOut = checkOutElement.getText();

            // Verify the text displayed on the page matches the expected values
            Assert.assertEquals(actualCheckIn, expectedCheckIn, "Check-in date is incorrect!");
            Assert.assertEquals(actualCheckOut, expectedCheckOut, "Check-out date is incorrect!");

            System.out.println("Booking details verified: Check-in and Check-out dates are correct.");

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error while verifying booking dates.");
        }
    }
}
