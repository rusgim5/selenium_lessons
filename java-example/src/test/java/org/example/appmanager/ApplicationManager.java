package org.example.appmanager;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static org.example.appmanager.ApplicationManager.BrowserType.*;

public class ApplicationManager {
    public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();
    public static WebDriver wd;
    public WebDriverWait wait;
    BrowserType browserType;
    public BrowserMobProxy proxy;
    private ProductHelper productHelper;
    private NavigationHelper navigationHelper;
    private UserHelper userHelper;
    private SessionHelper sessionHelper;
    private CountriesHelper countriesHelper;
    private CartPageHelper cartPageHelper;
    private MainPageHelper mainPageHelper;
    private ProductPageHelper productPageHelper;

    public ApplicationManager() {
        if (tlDriver.get() != null) {
            wd = tlDriver.get();
            wait = new WebDriverWait(wd, 10);
            return;
        }
        proxy = new BrowserMobProxyServer();
        proxy.start();

        Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);

        browserType = CHROME;

        if (browserType.equals(CHROME)) {
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.setCapability("marionette", true);
            chromeOptions.setCapability("proxy", seleniumProxy);
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

    public BrowserType getBrowserType() {
        return browserType;
    }

    public void setBrowserType(BrowserType browserType) {
        this.browserType = browserType;
    }

    public ProductHelper product() {
        if (productHelper == null) {
            productHelper = new ProductHelper(this, wd,wait);
        }
        return productHelper;
    }

    public SessionHelper session() {
        if (sessionHelper == null) {
            sessionHelper = new SessionHelper(this, wd, wait);
        }
        return sessionHelper;
    }
    public NavigationHelper goTo() {
        if (navigationHelper == null) {
            navigationHelper = new NavigationHelper(this, wd, wait);
        }
        return navigationHelper;
    }
    public UserHelper user() {
        if (userHelper == null) {
            userHelper = new UserHelper(this, wd, wait);
        }
        return userHelper;
    }
    public MainPageHelper mainPage() {
        if (mainPageHelper == null) {
            mainPageHelper = new MainPageHelper(this, wd, wait);
        }
        return mainPageHelper;
    }
    public ProductPageHelper productPage() {
        if (productPageHelper == null) {
            productPageHelper = new ProductPageHelper(this, wd, wait);
        }
        return productPageHelper;
    }
    public CartPageHelper cartPage() {
        if (cartPageHelper == null) {
            cartPageHelper = new CartPageHelper(this, wd, wait);
        }
        return cartPageHelper;
    }
    public CountriesHelper countries() {
        if (countriesHelper == null) {
            countriesHelper = new CountriesHelper(this, wd, wait);
        }
        return countriesHelper;
    }
    enum BrowserType {
        CHROME,
        FIREFOX,
        IE,
        FIREFOX_NIGHTLY,
    }


}
