package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

public class SearchResultsPage {
    private static WebDriver driver;
    private static WebDriverWait wait;
    private static JavascriptExecutor jsExecutor;

    // Locators
    private static By PopupCloseButton = By.xpath("//*[@id=\"b2searchresultsPage\"]/div[27]/div/div/div/div[1]/div[1]/div"); // Popup close button
    private static By hotelListings = By.xpath("//div[contains(@data-testid, 'property-card')]"); // Locator for hotel listings
    private static By hotelName = By.xpath(".//div[contains(@data-testid, 'title')]"); // Locator for hotel name in each listing
    private static By nextPageButton = By.xpath("//button[contains(@aria-label, 'Next page')]"); // Next page button
    private static By pageBackground = By.xpath("//body"); // A generic background locator to click anywhere outside the popup

    // Constructor
    public SearchResultsPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        this.jsExecutor = (JavascriptExecutor) driver;
    }

    // Close the popup if it is present
    public void closePopupIfPresent() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Increased timeout
            boolean popupClosed = false;

            // Loop to retry if the popup is still visible
            for (int i = 0; i < 3 && !popupClosed; i++) {
                try {
                    // Wait for the popup close button to be clickable or try to click anywhere on the page
                    WebElement closeButton = wait.until(ExpectedConditions.elementToBeClickable(PopupCloseButton));
                    if (closeButton.isDisplayed()) {
                        closeButton.click();
                        System.out.println("Popup closed using close button.");
                        popupClosed = true;
                    } else {
                        // If close button isn't working, click somewhere outside the popup (e.g., body)
                        WebElement background = wait.until(ExpectedConditions.elementToBeClickable(pageBackground));
                        background.click();
                        System.out.println("Popup closed by clicking background.");
                        popupClosed = true;
                    }
                } catch (TimeoutException | NoSuchElementException e) {
                    // No popup found, or popup button not clickable
                    System.out.println("Popup not found or not interactable on attempt " + (i + 1));
                }
                // Optionally add some wait time before retrying
                Thread.sleep(1000);  // Add delay before retrying
            }

            if (!popupClosed) {
                System.out.println("Popup handling completed or no popup appeared.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error while closing popup.");
        }
    }

    // Select a specific hotel
    public void scrollToAndClickHotel(String hotelName) {
        try {
            JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));


            boolean hotelFound = false;
            int maxScrollAttempts = 10; // Limit to prevent infinite loops
            int currentScrollAttempts = 0;


            while (!hotelFound && currentScrollAttempts < maxScrollAttempts) {
                try {
                    // Locate the hotel element by `data-testid` and the name
                    List<WebElement> hotels = driver.findElements(By.xpath("//div[@data-testid='title' and text()='" + hotelName + "']"));


                    for (WebElement hotel : hotels) {
                        if (hotel.isDisplayed()) {
                            // Scroll to the hotel element and click it
                            jsExecutor.executeScript("arguments[0].scrollIntoView({behavior: 'instant', block: 'center'});", hotel);
                            wait.until(ExpectedConditions.elementToBeClickable(hotel)).click();
                            System.out.println("Hotel found and clicked: " + hotelName);
                            hotelFound = true;
                            break;
                        }
                    }


                    if (!hotelFound) {
                        // Scroll down further if the hotel is not visible
                        jsExecutor.executeScript("window.scrollBy(0, 500);");
                        System.out.println("Scrolling down to find hotel: " + hotelName);
                    }
                } catch (ElementClickInterceptedException e) {
                    // Handle popups or overlays blocking the element
                    System.out.println("Popup detected; attempting to close it...");
                    handlePopup();
                }
                currentScrollAttempts++;
            }


            if (!hotelFound) {
                throw new RuntimeException("Hotel not found after maximum scroll attempts: " + hotelName);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error occurred while scrolling and clicking hotel: " + hotelName);
        }
    }


    // Method to handle popups
    private void handlePopup() {
        try {
            WebElement popupCloseButton = driver.findElement(By.cssSelector("button[aria-label='Close']"));
            popupCloseButton.click();
            System.out.println("Popup closed successfully.");
        } catch (NoSuchElementException ignored) {
            System.out.println("No popup found to close.");
        }
    }




    // Check if the next page button is available
    private static boolean isNextPageAvailable() {
        try {
            WebElement nextPage = driver.findElement(nextPageButton);
            return nextPage.isDisplayed() && nextPage.isEnabled();
        } catch (NoSuchElementException e) {
            return false;
        }
    }


    // Navigate to the next page
    private static void goToNextPage() {
        try {
            WebElement nextPage = wait.until(ExpectedConditions.elementToBeClickable(nextPageButton));
            nextPage.click();
            wait.until(ExpectedConditions.stalenessOf(nextPage)); // Wait for the next page to load
        } catch (Exception e) {
            Assert.fail("Failed to navigate to the next page: " + e.getMessage());
        }
    }






}

