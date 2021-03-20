package org.example;

import org.example.appmanager.ApplicationManager;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Random;
import java.util.stream.Collectors;


public class TestBase {


    public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();
    protected static ApplicationManager app;
    public WebDriver wd;
    public WebDriverWait wait;

    public static String randomString(int length) {
        return randomStr(length, "qwertyuiopasdfghjklzxcvbnm");
    }

    public static String randomNumber(int length) {
        return randomStr(length, "1234567890");
    }

    private static String randomStr(int length, String symbols) {
        String random = new Random().ints(length, 0, symbols.length())
                .mapToObj(symbols::charAt)
                .map(Object::toString)
                .collect(Collectors.joining());
        return random;
    }

    @Before
    public void start() {
        if (app == null) {
            app = new ApplicationManager();
        }
    }

    @After
    public void stop() {
        //driver.quit();
        //driver = null;
    }


}