package com.manuk.tests;

import com.manuk.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    private static final String VALID_USERNAME = "tomsmith";
    private static final String VALID_PASSWORD = "SuperSecretPassword!";

    @Test
    public void validLogin() {
        LoginPage loginPage = new LoginPage(driver).open();

        loginPage.loginAs(VALID_USERNAME, VALID_PASSWORD);

        Assert.assertTrue(loginPage.getFlashMessage().contains("You logged into a secure area!"),
                "Success message not shown");
        Assert.assertTrue(driver.getCurrentUrl().endsWith("/secure"),
                "Not redirected to the secure page");
    }

    @Test
    public void invalidPassword() {
        LoginPage loginPage = new LoginPage(driver).open();

        loginPage.loginAs(VALID_USERNAME, "wrongPassword");

        Assert.assertTrue(loginPage.getFlashMessage().contains("Your password is invalid!"),
                "Password error not shown");
    }

    @Test
    public void invalidUsername() {
        LoginPage loginPage = new LoginPage(driver).open();

        loginPage.loginAs("wrongUser", VALID_PASSWORD);

        Assert.assertTrue(loginPage.getFlashMessage().contains("Your username is invalid!"),
                "Username error not shown");
    }
}
