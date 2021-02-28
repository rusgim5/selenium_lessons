package org.example;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.example.TestBase.BrowserType.*;

public class TestBase {

    public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();
    public WebDriver wd;
    public WebDriverWait wait;
    BrowserType browserType;

    @Before
    public void start() {
        if (tlDriver.get() != null) {
            wd = tlDriver.get();
            wait = new WebDriverWait(wd, 10);
            return;
        }
        browserType = FIREFOX;

        if (browserType.equals(CHROME)) {
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.setCapability("marionette", true);
            wd = new ChromeDriver(chromeOptions);
        } else if (browserType.equals(FIREFOX)) {
            FirefoxOptions firefoxOptions = new FirefoxOptions();
            firefoxOptions.setCapability("marionette", true);
            wd = new FirefoxDriver(firefoxOptions);
        } else if (browserType.equals(IE)) {
            InternetExplorerOptions internetExplorerOptions = new InternetExplorerOptions();
            internetExplorerOptions.setCapability("marionette", true);
//            internetExplorerOptions.setCapability("logLevel", "DEBUG");
            wd = new InternetExplorerDriver(internetExplorerOptions);
        }else if (browserType.equals(FIREFOX_NIGHTLY)) {
            FirefoxOptions firefoxOptions = new FirefoxOptions();
            firefoxOptions.setBinary("C:/Program Files/Firefox Nightly/firefox.exe");
            firefoxOptions.setCapability("marionette", true);
            wd = new FirefoxDriver(firefoxOptions);
        }

        tlDriver.set(wd);
        System.out.println(((HasCapabilities) wd).getCapabilities());
        wait = new WebDriverWait(wd, 10);
        Runtime.getRuntime().addShutdownHook(
                new Thread(() -> {
                    wd.quit();
                    wd = null;
                }));
    }

    @After
    public void stop() {
        //driver.quit();
        //driver = null;
    }

    void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(wd.findElement(locator))).click();
    }

    void type(By locator, String value) {
        wd.findElement(locator).click();
        wd.findElement(locator).clear();
        wd.findElement(locator).sendKeys(value);
    }

    protected Boolean isElementPresent(By locator) {
        try {
            wait.until((WebDriver wd) -> wd.findElement(locator));
            return true;
        } catch (TimeoutException ex) {
            return false;
        }
    }

    enum BrowserType {
        CHROME,
        FIREFOX,
        IE,
        FIREFOX_NIGHTLY,
    }

    protected void login() {
        goTo("http://localhost/litecart/admin");
        if (isElementPresent(By.className("fa-sign-out"))) return;
        type(By.name("username"), "admin");
        type(By.name("password"), "admin");
        click(By.name("login"));
    }
    protected void goTo(String url) {
        wd.get(url);
    }

    public boolean checkAlphabeticalOrder(List<WebElement> list) {
        String previous = ""; // empty string: guaranteed to be less than or equal to any other
        for (final WebElement current : list) {
            if (current.getText().equals("")) continue;
            if (previous.compareTo(current.getText()) > 0) {
                return false;
            }
            previous = current.getText();
        }
        return true;
    }
}