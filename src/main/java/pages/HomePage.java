package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class HomePage {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor jsExecutor;

    // Locators
    private By destinationField = By.name("ss"); // Destination field
    private By searchButton = By.xpath("//button[@type='submit']"); // Search button
    private By cookieAcceptButton = By.xpath("//button[text()='Accept']"); // Cookie accept button
    private By firstOptionInDropdown = By.xpath("//div[@id='autocomplete-results']//li[@role='option'][1]"); // First option in dropdown

    private By calendarCheckIn = By.xpath("//span[@data-date]"); // Locator for check-in and check-out dates

    // Constructor
    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        this.jsExecutor = (JavascriptExecutor) driver;
    }

    // Handle cookies
    public void handleCookies() {
        try {
            WebElement acceptCookies = wait.until(ExpectedConditions.visibilityOfElementLocated(cookieAcceptButton));
            Assert.assertNotNull(acceptCookies, "Cookie accept button is not found.");
            jsExecutor.executeScript("arguments[0].scrollIntoView(true);", acceptCookies);
            acceptCookies.click();
            System.out.println("Cookies were handled successfully.");
        } catch (Exception e) {
            Assert.fail("Failed to handle cookies: " + e.getMessage());
        }
    }

    // Enter destination and select the first option
    public void enterDestination(String destination) throws InterruptedException {
        try {
            // Enter destination
            WebElement destinationInput = wait.until(ExpectedConditions.visibilityOfElementLocated(destinationField));
            Assert.assertNotNull(destinationInput, "Destination input field is not found.");
            destinationInput.clear();
            destinationInput.sendKeys(destination);
            System.out.println("Destination entered: " + destination);

            // Wait and select the first option from the dropdown
            Thread.sleep(500);
            WebElement firstOption = wait.until(ExpectedConditions.visibilityOfElementLocated(firstOptionInDropdown));
            Assert.assertNotNull(firstOption, "First option in the dropdown is not found.");
            firstOption.click();
            System.out.println("First option from the dropdown selected successfully.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            Assert.fail("Thread was interrupted: " + e.getMessage());
        } catch (Exception e) {
            Assert.fail("Failed to enter destination or select the first option: " + e.getMessage());
        }
    }

    // Select check-in and check-out dates from the calendar
    public void selectCheckInAndOutDates(String checkInDate, String checkOutDate) {
        try {
            // Scroll down to bring the calendar into view
            Thread.sleep(100);
            jsExecutor.executeScript("window.scrollBy(0, 250);");
            System.out.println("Scrolled down to bring the calendar into view.");


            // Select check-in date
            WebElement dateElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[@id=\"calendar-searchboxdatepicker\"]/div/div[1]/div/div[1]/table/tbody/tr[4]/td[4]/span")));
            dateElement.click();
            System.out.println("Check-in date selected: 2024-12-19");

            // Wait before selecting check-out date
            Thread.sleep(200);

            // Select check-out date
            WebElement outDateElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[@id=\"calendar-searchboxdatepicker\"]/div/div[1]/div/div[1]/table/tbody/tr[4]/td[7]/span")));
            outDateElement.click();
            System.out.println("Check-out date selected: 2024-12-22");

        } catch (Exception e) {
            Assert.fail("Failed to select check-in and check-out dates: " + e.getMessage());
        }
    }

    // Click the search button
    public void clickSearchButton() {
        try {
            WebElement searchBtn = wait.until(ExpectedConditions.elementToBeClickable(searchButton));
            Assert.assertNotNull(searchBtn, "Search button is not clickable.");
            searchBtn.click();
            System.out.println("Search button clicked successfully.");
        } catch (Exception e) {
            Assert.fail("Failed to click the search button: " + e.getMessage());
        }
    }
}
