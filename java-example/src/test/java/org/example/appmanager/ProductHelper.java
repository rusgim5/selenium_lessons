package org.example.appmanager;

import org.example.model.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.attributeContains;
import static org.openqa.selenium.support.ui.ExpectedConditions.stalenessOf;

public class ProductHelper extends HelperBase{

    public ProductHelper(ApplicationManager app, WebDriver wd, WebDriverWait wait) {
        super(app, wd, wait);
    }

    public void createProduct(Product product) {
        app.session().login();
        initCreate();
        fillProductInfo(product);
        submint(product);


    }

    private void submint(Product product) {
        click(By.cssSelector("button[name=save]"));
        assertTrue(isElementPresent(By.xpath("//a[text()='" + product.getName() + "']")));
    }

    private void fillProductInfo(Product product) {
        click(By.xpath("//label[text()=' Enabled']//input"));
        type(By.cssSelector("input[name*=name]"), product.getName());
        type(By.cssSelector("input[name=code]"), product.getCode());
        click(By.xpath("//tr[./td[text()='Unisex']]//input"));
        type(By.cssSelector("input[name=quantity]"), product.getQuantity());
        wd.findElement(By.cssSelector("input[type=file]")).sendKeys(product.getFilePath());
        click(By.cssSelector("a[href*=tab-information]"));
        sleep(1000);
        selectedByIndex(wd.findElement(By.cssSelector("select[name=manufacturer_id]")), 1);
        type(By.cssSelector("input[name=keywords]"), product.getName());
        type(By.cssSelector("input[name*=short_description]"), product.getShortDescription());
        type(By.cssSelector("div.trumbowyg-editor"), product.getDescription());
        type(By.cssSelector("input[name*=head_title]"), product.getShortDescription());
        type(By.cssSelector("input[name*=meta_description]"), product.getShortDescription());
        click(By.cssSelector("a[href*=tab-prices"));
        sleep(1000);
        type(By.cssSelector("input[name=purchase_price]"), product.getPrice());
        selectedByIndex(wd.findElement(By.cssSelector("select[name=purchase_price_currency_code]")), 1);
        type(By.cssSelector("input[name='prices[USD]']"), product.getPrice());
        type(By.cssSelector("input[name='gross_prices[USD]']"), 20);
        type(By.cssSelector("input[name='prices[EUR]']"), product.getPrice());
        type(By.cssSelector("input[name='gross_prices[EUR]']"), 20);
    }

    private void initCreate() {
        click(By.xpath("//ul[@id='box-apps-menu']//span[text()='Catalog']"));
        click(By.xpath("//a[contains(text(),' Add New Product')]"));
    }

    public void addToCart(int quantity) {
        //        Добавляем 3 товара в корзину

    }



    public void checkStickers() {
        app.goTo().mainPage();
        List<WebElement> products = wd.findElements(By.className("product"));
        for (WebElement product : products) {
            assertEquals(1, product.findElements(By.className("sticker")).size());
        }
    }
}
