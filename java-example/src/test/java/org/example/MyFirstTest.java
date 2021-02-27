package org.example;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class MyFirstTest extends TestBase {

    @Test
    public void myFirstTest() {
        wd.navigate().to("http://www.google.com");
        type(By.name("q"), "webdriver");
        click(By.name("btnK"));
        wait.until(titleIs("webdriver - Поиск в Google"));
    }

    @Test
    public void testLogin() {
        login();
    }


    @Test
    public void testHeaders() {
        login();
//      В этом меню, прям любой клик пересоздает все элемены, из-за чего пришлось использовать for, а не foreach
//      Пока не понял правильно ли так бороться с StaleElementReferenceException
        if (isElementPresent(By.cssSelector("ul#box-apps-menu"))) {
            int appMenuCount = wd.findElements(By.cssSelector("ul#box-apps-menu li#app-")).size();
            for (int i = 1; i <= appMenuCount; i++) {
                WebElement app = wd.findElement(By.cssSelector("ul#box-apps-menu li#app-:nth-child(" + i + ")"));
                app.click();
                int subDocCount = wd.findElements(By.cssSelector("li[id*=doc]")).size();
                for (int j = 1; j <= subDocCount; j++) {
                    WebElement doc = wd.findElement(By.cssSelector("li[id*=doc]:nth-child(" + j + ")"));
                    doc.click();
                    assertTrue("Header not found", isElementPresent(By.cssSelector("h1")));
                }
            }
        }
    }

    @Test
    public void testStickers() {
        goTo("http://localhost/litecart/en/");
        List<WebElement> products = wd.findElements(By.className("product"));
        for (WebElement product : products) {
            assertEquals(1, product.findElements(By.className("sticker")).size());
        }
    }

    @Test
    public void testAlphabetOrderCountries() throws InterruptedException {
        login();
        goTo("http://localhost/litecart/admin/?app=countries&doc=countries");

        List<WebElement> countries = wd.findElements(By.cssSelector("[name=countries_form] tr td:nth-of-type(5)"));
        assertTrue(checkAlphabeticalOrder(countries));

        int countriesCount = countries.size();
        for (int i = 2; i <= countriesCount; i++) {
            WebElement country = wd.findElement(By.cssSelector("[name=countries_form] tr:nth-child(" + i + ")"));
            if (Integer.parseInt(country.findElement(By.cssSelector("td:nth-of-type(6)")).getText())>0){
                country.findElement(By.cssSelector("[title=Edit]")).click();
                List<WebElement> zones = wd.findElements(By.cssSelector("table#table-zones tr td:nth-of-type(3)"));
                assertTrue(checkAlphabeticalOrder(zones));
                goTo("http://localhost/litecart/admin/?app=countries&doc=countries");
            }
        }

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