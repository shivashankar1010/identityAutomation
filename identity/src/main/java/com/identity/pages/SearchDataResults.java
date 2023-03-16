package com.identity.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;


import java.time.Duration;
import java.util.function.Function;

public class SearchDataResults {

    WebDriver driver;
    public SearchDataResults(WebDriver driver) {
        this.driver = driver;


    }

    By CarResults = By.xpath("//div[@data-test-id='your-registration-number-summary']//p[2]");
    public String getResultsText() {

        Boolean isPresent = driver.findElements(By.xpath("//div[@data-test-id='your-registration-number-summary']//p[2]")).size() > 0;

        String results = null;
        if (isPresent == true) {
            Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                    .withTimeout(Duration.ofSeconds(10L))
                    .pollingEvery(Duration.ofSeconds(5L))
                    .ignoring(NoSuchElementException.class);

            WebElement foo = wait.until(new Function<WebDriver, WebElement>() {
                public WebElement apply(WebDriver driver) {
                    return driver.findElement(CarResults);
                }
            });
            results = driver.findElement(CarResults).getText();
        } else {

            Assert.fail("Registration Does not exist");
        }
        return results;

    }
}