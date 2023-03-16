package com.identity.tests;
import com.identity.pages.ReadData;
import com.identity.pages.SearchData;
import com.identity.pages.SearchDataResults;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class IdentityTest {

    private static String currentDirectory = new File("").getAbsolutePath();


    WebDriver driver;

    @DataProvider(name = "registrations")
    public Iterator<Object[]> registrationProvider() throws IOException {
        String filename = currentDirectory + "/src/test/Resources/inputFiles";
        ReadData m = new ReadData();
        List<String> registrations = m.matchRegistrations(filename);
        Collection<Object[]> data = new ArrayList<Object[]>();
        registrations.forEach(item -> data.add(new Object[]{item}));
        return data.iterator();
    }


    @BeforeMethod(alwaysRun = true)
    public void beforeMethod() {
        System.setProperty("webdriver.chrome.driver","/Users/shivahosur/Downloads/path/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-extensions");
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get("https://www.cazoo.co.uk/value-my-car/");
    }

    @Test(enabled = true, dataProvider = "registrations")
    public void searchTest(String inputData) throws IOException, InterruptedException {
        String inputValue = inputData.replaceAll("\\s+", "");
        SearchData page = new SearchData(driver);
        ReadData readData = new ReadData();
        SearchDataResults resultsPage = new SearchDataResults(driver);
        page.searchCar(inputData);


        String results = resultsPage.getResultsText();
        results = results.replace("Make/model: ", "");
        int index = readData.indexOf("\\s+", results, 2);
        String inputVehicleModel = results.substring(0, index);
        String inputCarModel = results.substring(index + 1);
        String  linetovalidate = readData.readOutputData(currentDirectory + "/src/test/Resources/outputFiles", inputData);

        String output[] = linetovalidate.split("[,]", 0);
        String outputValue = output[0];
        String outputCarModel = output[1];
        String outputVehicleModel = output[2];

        Assert.assertEquals(inputValue , outputValue);
        Assert.assertEquals(inputVehicleModel, outputCarModel);
        Assert.assertEquals(inputCarModel, outputVehicleModel);
    }

    @AfterMethod(alwaysRun = true)
    public void afterTest() {
        driver.quit();

    }

}
