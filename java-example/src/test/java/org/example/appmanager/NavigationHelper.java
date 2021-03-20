package org.example.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.assertTrue;

public class NavigationHelper extends HelperBase {
    public NavigationHelper(ApplicationManager app, WebDriver wd, WebDriverWait wait) {
        super(app, wd, wait);
    }
    public void url(String url) {
        wd.get(url);
    }

    public void loginPage() {
        wd.get("http://localhost/litecart/admin");
    }

    public void countriesPage() {
        wd.get("http://localhost/litecart/admin/?app=countries&doc=countries");
    }

    public void geoZonesPage() {
        wd.get("http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones");
    }

    public void mainPage() {
        wd.get("http://localhost/litecart/en/");
    }

    public void checkMenu() {
        app.session().login();
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

    public void catalogPage() {
        wd.get("http://localhost/litecart/admin/?app=catalog&doc=catalog&category_id=1");
    }
}
