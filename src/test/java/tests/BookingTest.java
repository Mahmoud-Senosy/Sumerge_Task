import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.SearchResultsPage;
import pages.DetailsPage;
import pages.ConfirmationPage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.*;

public class BookingTest {
    private WebDriver driver;
    private HomePage homePage;
    private SearchResultsPage searchResultsPage;
    private DetailsPage detailsPage;
    private ConfirmationPage confirmationPage;

    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.booking.com");

        homePage = new HomePage(driver);
        searchResultsPage = new SearchResultsPage(driver);
        detailsPage = new DetailsPage(driver);
        confirmationPage = new ConfirmationPage(driver);
    }

    @DataProvider(name = "excelData")
    public Object[][] excelData() throws IOException {
        // Use the correct file path here
        FileInputStream fis = new FileInputStream(new File("src/test/resources/booking_data.xlsx"));
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheetAt(0);

        int rowCount = sheet.getPhysicalNumberOfRows();
        Object[][] data = new Object[rowCount - 1][3];

        for (int i = 1; i < rowCount; i++) {
            Row row = sheet.getRow(i);
            data[i - 1][0] = row.getCell(0).getStringCellValue(); // Destination
            data[i - 1][1] = row.getCell(1).getStringCellValue(); // Check-in Date
            data[i - 1][2] = row.getCell(2).getStringCellValue(); // Check-out Date
        }

        workbook.close();
        fis.close();

        return data;
    }

    @Test(dataProvider = "excelData")
    public void testHotelBooking(String destination, String checkInDate, String checkOutDate) throws InterruptedException {
        try {
            // Step 1: Home Page actions
            homePage.handleCookies();
            homePage.enterDestination(destination);
            homePage.selectCheckInAndOutDates(checkInDate, checkOutDate);
            homePage.clickSearchButton();

            // Step 2: Search Results actions
            searchResultsPage.closePopupIfPresent();
            searchResultsPage.scrollToAndClickHotel("Tolip Hotel Alexandria");

            // Step 3: Hotel Details actions
            //detailsPage.selectBedOption();
            //detailsPage.selectPrice("1"); // Select the price option
            //detailsPage.clickSelectAndContinue();


            //6- Selecet bed, Price and Click on I will reserve
           // DetailsPage detailsPage = new DetailsPage(driver);
            String priceValue = "1";
            detailsPage.performReservation(priceValue);

            // Step 4: Confirmation Page actions
            //confirmationPage.verifyBookingDetails();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Test failed: " + e.getMessage());
        }
    }
}
