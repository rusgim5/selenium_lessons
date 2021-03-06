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
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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
        browserType = CHROME;

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
        } else if (browserType.equals(FIREFOX_NIGHTLY)) {
            FirefoxOptions firefoxOptions = new FirefoxOptions();
            firefoxOptions.setBinary("C:/Program Files/Firefox Nightly/firefox.exe");
            firefoxOptions.setCapability("marionette", true);
            wd = new FirefoxDriver(firefoxOptions);
        }

        wd.manage().window().maximize();
        wd.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        tlDriver.set(wd);
        wait = new WebDriverWait(wd, 3);
//        Runtime.getRuntime().addShutdownHook(
//                new Thread(() -> {
//                    wd.quit();
//                    wd = null;
//                }));
    }

    @After
    public void stop() {
        //driver.quit();
        //driver = null;
    }

    public BrowserType getBrowserType() {
        return browserType;
    }

    public void setBrowserType(BrowserType browserType) {
        this.browserType = browserType;
    }

    void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(wd.findElement(locator))).click();
    }

    void type(By locator, String value) {
        WebElement element = wd.findElement(locator);
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
        element.clear();
        element.sendKeys(value);
    }

    protected Boolean isElementPresent(By locator) {
        try {
            wait.until((WebDriver wd) -> wd.findElement(locator));
            return true;
        } catch (TimeoutException ex) {
            return false;
        }
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

    public boolean checkAlphabeticalOrder(List<String> list) {
        String previous = ""; // empty string: guaranteed to be less than or equal to any other
        for (final String current : list) {
            if (current.equals("")) continue;
            if (previous.compareTo(current) > 0) {
                return false;
            }
            previous = current;
        }
        return true;
    }

    protected void selectedByContentText(WebElement select, String contentText) {
        if (getBrowserType().equals(FIREFOX)) {
            ((JavascriptExecutor) wd).executeScript("var options = arguments[0].options;" +
                    "for (var i = 0, optionsLength = options.length; i < optionsLength; i++) {" +
                    "if (options[i].textContent == arguments[1]) {" +
                    "  arguments[0].selectedIndex = i; break;" +
                    "}" +
                    "}" +
                    "arguments[0].dispatchEvent(new Event('change'));", select, contentText);
        } else {
            Select select1 = new Select(select);
            select1.selectByVisibleText(contentText);
        }
    }

    protected void selectedByIndex(WebElement select, int index) {
        if (getBrowserType().equals(FIREFOX)) {
            ((JavascriptExecutor) wd).executeScript("arguments[0].selectedIndex = arguments[1];" +
                    "arguments[0].dispatchEvent(new Event('change'));", select,index);
        } else {
            Select select1 = new Select(select);
            select1.selectByIndex(index);
        }
    }

    enum BrowserType {
        CHROME,
        FIREFOX,
        IE,
        FIREFOX_NIGHTLY,
    }
    public static String randomString(int length) {
        return randomStr(length,"qwertyuiopasdfghjklzxcvbnm");
    }

    public static String randomNumber(int length) {
        return randomStr(length,"1234567890");
    }

    private static String randomStr(int length, String symbols) {
        String random = new Random().ints(length, 0, symbols.length())
                .mapToObj(symbols::charAt)
                .map(Object::toString)
                .collect(Collectors.joining());
        return random;
    }
}