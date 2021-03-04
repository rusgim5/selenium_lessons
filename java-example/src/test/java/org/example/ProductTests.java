package org.example;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
