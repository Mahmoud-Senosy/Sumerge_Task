package pages;


import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import org.openqa.selenium.support.ui.Select;


public class DetailsPage {


    private WebDriver driver;
    private JavascriptExecutor jsExecutor;
    private WebDriverWait wait;


    // Locators
    private By bedRadioButton = By.xpath("//*[@id='hprt-table']/tbody/tr[1]/td[1]/div/div[3]/div[1]/label[3]/div/input");
    private By dropdownElement = By.xpath("//*[@id='hprt_nos_select_78883120_373531459_2_34_0_131741']");
    private By reserveButtonLocator = By.xpath("//span[contains(@class, 'js-reservation-button__text') and (text()='Select and continue' or text()=\"I'll reserve\")]");


    // Constructor
    public DetailsPage(WebDriver driver) {
        this.driver = driver;
        this.jsExecutor = (JavascriptExecutor) driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }


    // Scroll to a specific element
    private void scrollToSpecificElement(By locator) {
        try {
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            jsExecutor.executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", element);
            System.out.println("Scrolled to specific element.");
        } catch (Exception e) {
            throw new RuntimeException("Failed to scroll to element: " + e.getMessage());
        }
    }


    // Step 1: Switch to the new tab
    private void switchToNewTab() {
        String mainWindowHandle = driver.getWindowHandle();


        for (String windowHandle : driver.getWindowHandles()) {
            if (!windowHandle.equals(mainWindowHandle)) {
                driver.switchTo().window(windowHandle);
                System.out.println("Switched to new tab.");
                break;
            }
        }
    }


    // Step 2: Select the radio button
    public void selectBedOption() {
        try {
            scrollToSpecificElement(bedRadioButton); // Ensure the element is in view
            WebElement bedOption = wait.until(ExpectedConditions.elementToBeClickable(bedRadioButton));
            bedOption.click();
            System.out.println("Radio button for bed option selected.");
        } catch (Exception e) {
            throw new RuntimeException("Failed to select bed option: " + e.getMessage());
        }
    }


    // Step 3: Select the price
    public void selectPrice(String value) {
        try {
            scrollToSpecificElement(dropdownElement); // Ensure the element is in view
            WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(dropdownElement));
            Select select = new Select(dropdown);
            select.selectByValue(value);
            System.out.println("Price selected successfully.");
        } catch (Exception e) {
            throw new RuntimeException("Failed to select price: " + e.getMessage());
        }
    }


    // Step 4: Click "Select and continue"
    public void clickSelectAndContinue() {
        try {
            scrollToSpecificElement(reserveButtonLocator); // Ensure the button is in view
            WebElement reserveButton = wait.until(ExpectedConditions.elementToBeClickable(reserveButtonLocator));
            reserveButton.click();
            System.out.println("Clicked 'Select and continue'.");
        } catch (Exception e) {
            throw new RuntimeException("Failed to click 'Select and continue': " + e.getMessage());
        }
    }


    // Perform all actions in sequence
    public void performReservation(String priceValue) {
        switchToNewTab(); // Switch to the new tab
        selectBedOption(); // Select bed option
        selectPrice(priceValue); // Select price
        clickSelectAndContinue(); // Click "Select and continue"
    }
}

