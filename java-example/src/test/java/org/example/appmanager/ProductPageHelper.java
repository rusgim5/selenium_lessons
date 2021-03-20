package org.example.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Random;

import static org.openqa.selenium.support.ui.ExpectedConditions.attributeContains;

public class ProductPageHelper extends HelperBase {
    public ProductPageHelper(ApplicationManager app, WebDriver wd, WebDriverWait wait) {
        super(app, wd, wait);
    }

    public void addToCart() {
        WebElement size = returnIsElementPresent(By.cssSelector("select[name='options[Size]']"));
        if (size != null) {
            selectedByIndex(size, new Random().ints(2, size.findElements(By.cssSelector("option")).size() - 1).findFirst().getAsInt());
        }
        WebElement card = wd.findElement(By.cssSelector("span.quantity"));
        int count = Integer.parseInt(card.getAttribute("textContent"));
        click(By.cssSelector("button[name=add_cart_product]"));
        wait.until(attributeContains(card, "textContent", String.valueOf(count + 1)));
    }}
