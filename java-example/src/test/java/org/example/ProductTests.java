package org.example;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;

import java.util.Random;

import static org.junit.Assert.*;

public class ProductTests extends TestBase {

    @Test
    public void compareProductsView() {
        goTo("http://localhost/litecart/en/");

        WebElement webCategory = wd.findElement(By.xpath("//div[./h3[text()='Campaigns']]"));
        WebElement webProduct = webCategory.findElement(By.cssSelector("li[class*=product]"));
        Product product = getProduct(webProduct);
        Product productDetails = product.getProductDetails();
//        Проверяем, что продукты во всем одинаковые кроме оформления
        assertEquals("Продукт и его детали не равны", product, productDetails);
//        Проверяем оформление продукта на главной странице
        assertTrue(isGray(product.getPriceVisualProperties().getPropRegularPrice().getColor()));
        assertTrue(isRed(product.getPriceVisualProperties().getPropCampaignPrice().getColor()));
        assertTrue("На главной станице. Размер шрифта аукционной цены меньше размера обычной цены",
                product.getPriceVisualProperties().getPropCampaignPrice().getFontSize() >
                        product.getPriceVisualProperties().getPropRegularPrice().getFontSize());
//        Проверяем оформление детализации продукта
        assertTrue(isGray(productDetails.getPriceVisualProperties().getPropRegularPrice().getColor()));
        assertTrue(isRed(productDetails.getPriceVisualProperties().getPropCampaignPrice().getColor()));
        assertTrue("На станице с детализацией продукта. Размер шрифта аукционной цены меньше размера обычной цены",
                product.getPriceVisualProperties().getPropCampaignPrice().getFontSize() >
                        product.getPriceVisualProperties().getPropRegularPrice().getFontSize());
    }

    @Test
    public void testAddNewProduct() {
        final String name = "velocity"+randomString(4);
        final String code = randomNumber(5);
        final int quantity = new Random().nextInt(100);
        final String shortDescription = "Детский зеленый велосипед";
        final String filePath = System.getProperty("user.dir") + "\\src\\test\\resources\\product1.jpg";
        final String description = "Детский велосипед"+ Keys.RETURN+"Зеленого цвета";
        final String price = new Random().nextInt(100)+",0";

        login();
        click(By.xpath("//ul[@id='box-apps-menu']//span[text()='Catalog']"));
        click(By.xpath("//a[contains(text(),' Add New Product')]"));
        click(By.xpath("//label[text()=' Enabled']//input"));
        type(By.cssSelector("input[name*=name]"), name);
        type(By.cssSelector("input[name=code]"), code);
        click(By.xpath("//tr[./td[text()='Unisex']]//input"));
        type(By.cssSelector("input[name=quantity]"), quantity);
        wd.findElement(By.cssSelector("input[type=file]")).sendKeys(filePath);
        click(By.cssSelector("a[href*=tab-information]"));
        sleep(1000);
        selectedByIndex(wd.findElement(By.cssSelector("select[name=manufacturer_id]")),1);
        type(By.cssSelector("input[name=keywords]"), name);
        type(By.cssSelector("input[name*=short_description]"), shortDescription);
        type(By.cssSelector("div.trumbowyg-editor"), description);
        type(By.cssSelector("input[name*=head_title]"), shortDescription);
        type(By.cssSelector("input[name*=meta_description]"), shortDescription);
        click(By.cssSelector("a[href*=tab-prices"));
        sleep(1000);
        type(By.cssSelector("input[name=purchase_price]"), price);
        selectedByIndex(wd.findElement(By.cssSelector("select[name=purchase_price_currency_code]")),1);
        type(By.cssSelector("input[name='prices[USD]']"), price);
        type(By.cssSelector("input[name='gross_prices[USD]']"), 20);
        type(By.cssSelector("input[name='prices[EUR]']"), price);
        type(By.cssSelector("input[name='gross_prices[EUR]']"), 20);
        click(By.cssSelector("button[name=save]"));
        assertTrue(isElementPresent(By.xpath("//a[text()='"+name+"']")));
    }

    private boolean isGray(Color color) {
        return color.getColor().getGreen() == color.getColor().getRed() && color.getColor().getRed() == color.getColor().getBlue();
    }

    private boolean isRed(Color color) {
        return color.getColor().getRed() > 0 && color.getColor().getBlue() == 0 && color.getColor().getGreen() == 0;
    }


    private Product getProduct(WebElement product) {
        String name = product.findElement(By.cssSelector(".name")).getText();
        WebElement regularPrice = product.findElement(By.cssSelector(".regular-price"));
        WebElement campaignPrice = product.findElement(By.cssSelector(".campaign-price"));
        PriceVisualProperties priceVisualProperties = getPriceVisualProperties(regularPrice, campaignPrice);
        product.click();
        Product productDetails = new Product(
                wd.findElement(By.cssSelector("h1.title")).getText()
                , getPriceVisualProperties(
                wd.findElement(By.cssSelector(".regular-price")),
                wd.findElement(By.cssSelector(".campaign-price"))));
        return new Product(name, priceVisualProperties, productDetails);
    }

    private PriceVisualProperties getPriceVisualProperties(WebElement regularPrice, WebElement campaignPrice) {
        return new PriceVisualProperties()
                .withRegularPrice(regularPrice.getText())
                .withVisualPropRegularPrice(new VisualProperties()
                        .withTextDecoration(regularPrice.getCssValue("text-decoration"))
                        .withFontSize(regularPrice.getCssValue("font-size"))
                        .withColor(regularPrice.getCssValue("color")))
                .withCampaignPrice(campaignPrice.getText())
                .withVisualPropCampaignPrice(new VisualProperties()
                        .withTextDecoration(campaignPrice.getCssValue("text-decoration"))
                        .withFontSize(campaignPrice.getCssValue("font-size"))
                        .withColor(campaignPrice.getCssValue("color")));

    }


}
