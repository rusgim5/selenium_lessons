package org.example.appmanager;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.example.appmanager.ApplicationManager.BrowserType.FIREFOX;

public class HelperBase {

    protected ApplicationManager app;
    protected WebDriver wd;
    protected WebDriverWait wait;

    public HelperBase(ApplicationManager app, WebDriver wd, WebDriverWait wait) {
        this.app = app;
        this.wd = wd;
        this.wait = wait;

    }

    void click(By locator) {
        sleep(500);
        wait.until(ExpectedConditions.elementToBeClickable(wd.findElement(locator))).click();
    }

    void click(WebElement webElement) {
        sleep(500);
        wait.until(ExpectedConditions.elementToBeClickable(webElement)).click();
    }
    protected void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    void type(By locator, double value) {
        type(locator, String.valueOf(value));
    }

    void type(By locator, String value) {
        sleep(500);
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

    protected WebElement returnIsElementPresent(By locator) {
        try {
            return wait.until((WebDriver wd) -> wd.findElement(locator));
        } catch (TimeoutException ex) {
            return null;
        }
    }







    protected void selectedByContentText(WebElement select, String contentText) {
        if (app.getBrowserType().equals(FIREFOX)) {
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
        if (app.getBrowserType().equals(FIREFOX)) {
            ((JavascriptExecutor) wd).executeScript("arguments[0].selectedIndex = arguments[1];" +
                    "arguments[0].dispatchEvent(new Event('change'));", select, index);
        } else {
            Select select1 = new Select(select);
            select1.selectByIndex(index);
        }
    }

}
