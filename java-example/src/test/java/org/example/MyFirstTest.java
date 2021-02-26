package org.example;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

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
                    Assert.assertTrue("Header not found", isElementPresent(By.cssSelector("h1")));
                }
            }
        }
    }

}