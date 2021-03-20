package org.example.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Random;

import static org.openqa.selenium.support.ui.ExpectedConditions.attributeContains;
import static org.openqa.selenium.support.ui.ExpectedConditions.stalenessOf;

public class CartPageHelper extends HelperBase {
    public CartPageHelper(ApplicationManager app, WebDriver wd, WebDriverWait wait) {
        super(app, wd, wait);
    }


    public void clearCart() {
        click(By.xpath("//a[@class='link' and text()='Checkout »']"));
        List<WebElement> shotCuts = wd.findElements(By.cssSelector("li.shortcut"));
        for (int i = 1; i < shotCuts.size() + 1; i++) {
            WebElement table = wd.findElement(By.cssSelector("table.dataTable"));
            click(By.cssSelector("button[name=remove_cart_item]"));
            wait.until(stalenessOf(table));
        }
    }}
