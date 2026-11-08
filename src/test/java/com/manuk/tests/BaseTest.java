package com.manuk.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

/**
 * Opens a fresh Chrome before each test and closes it after.
 * Run with -Dheadless=true to hide the browser window.
 */
public abstract class BaseTest {

    protected WebDriver driver;

    @BeforeMethod
    public void startBrowser() {
        ChromeOptions options = new ChromeOptions();
        if (Boolean.getBoolean("headless")) {
            options.addArguments("--headless=new", "--window-size=1920,1080");
        }
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
    }

    @AfterMethod(alwaysRun = true)
    public void quitBrowser() {
        if (driver != null) {
            driver.quit();
        }
    }
}
