package org.example.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SessionHelper extends HelperBase {
    public SessionHelper(ApplicationManager app, WebDriver wd, WebDriverWait wait) {
        super(app, wd, wait);
    }
    public void logout(){
        click(By.cssSelector("aside#navigation a[href*=logout]"));
    }
    public void login() {
        if (isElementPresent(By.className("fa-sign-out"))) return;
        app.goTo().loginPage();
        type(By.name("username"), "admin");
        type(By.name("password"), "admin");
        click(By.name("login"));
    }
}
