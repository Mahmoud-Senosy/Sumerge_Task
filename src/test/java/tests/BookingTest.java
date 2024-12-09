package tests;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.DetailsPage;
import pages.SearchResultsPage;
import pages.ConfirmationPage;
import io.github.bonigarcia.wdm.WebDriverManager;


import java.time.Duration;
import java.util.logging.Logger;

import static pages.SearchResultsPage.*;


public class BookingTest {
    private WebDriver driver;
    private HomePage homePage;
    private static final Logger logger = Logger.getLogger(BookingTest.class.getName());
   // private By popupCloseButton = By.xpath("//div[contains(@class, 'bui-modal__header')]//button");


    @BeforeClass
    public void setUp() {
        logger.info("Setting up WebDriver and launching browser.");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.manage().window().maximize();
        driver.get("https://www.booking.com");
        homePage = new HomePage(driver);
        logger.info("Browser launched and navigated to Booking.com.");

    }
/*
   @AfterClass
   public void tearDown() {
       logger.info("Closing browser.");
       if (driver != null) {
           driver.quit();
       }
   }*/


    @Test
    public void testHotelBooking() throws InterruptedException {

        //1- Handle Cookies
        logger.info("Handling cookies.");
        homePage.handleCookies();
        logger.info("Cookies handled successfully.");

         //2- Entering destination: Alexandria
        logger.info("Entering destination: Alexandria.");
        homePage.enterDestination("Alexandria");
        logger.info("Destination entered, and check-in/out dates selected successfully.");

        //3-Click on the Search Page
        logger.info("Clicking search button.");
         homePage.clickSearchButton();
        logger.info("Search initiated successfully.");
        logger.info("Closing popup if present.");

        //4-Close the Pop up at the Search Page
        SearchResultsPage searchResultsPage = new SearchResultsPage(driver);
        searchResultsPage.closePopupIfPresent();

        //5- Selecting Tolip Hotel Alexandria from the search result
         logger.info("Selecting hotel: Tolip Hotel Alexandria.");
         searchResultsPage.scrollToAndClickHotel("Tolip Hotel Alexandria");

        //6- Selecet bed, Price and Click on I will reserve
        DetailsPage detailsPage = new DetailsPage(driver);
        String priceValue = "1";
        detailsPage.performReservation(priceValue);

         // Aseertion of the date at the Confirmation Page :
        ConfirmationPage confirmationPage = new ConfirmationPage(driver);
        confirmationPage.verifyBookingDates();

    }
}

