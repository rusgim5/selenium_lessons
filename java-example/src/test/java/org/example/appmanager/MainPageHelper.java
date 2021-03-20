package org.example.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainPageHelper extends HelperBase {
    public MainPageHelper(ApplicationManager app, WebDriver wd, WebDriverWait wait) {
        super(app, wd, wait);
    }

    public void selectAnyProduct() {
        app.goTo().mainPage();
        WebElement product = wd.findElement(By.cssSelector("li.product"));
        click(product);

    }
}
