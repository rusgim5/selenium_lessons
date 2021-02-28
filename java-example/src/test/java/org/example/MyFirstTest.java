package org.example;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;

import java.util.List;

import static org.example.TestBase.BrowserType.IE;
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
            if (Integer.parseInt(country.findElement(By.cssSelector("td:nth-of-type(6)")).getText()) > 0) {
                country.findElement(By.cssSelector("[title=Edit]")).click();
                List<WebElement> zones = wd.findElements(By.cssSelector("table#table-zones tr td:nth-of-type(3)"));
                assertTrue(checkAlphabeticalOrder(zones));
                goTo("http://localhost/litecart/admin/?app=countries&doc=countries");
            }
        }

    }

    //    Перемудрил, нужен рефакторинг). Но в целом, задачу выполняет).
    @Test
    public void compareProductsView() {
        goTo("http://localhost/litecart/en/");

        WebElement webCategory = wd.findElement(By.xpath("//div[./h3[text()='Campaigns']]"));
        WebElement webProduct = webCategory.findElement(By.cssSelector("li[class*=product]"));

        Product product = getProduct(webProduct);
        Product productDetails = product.getProductDetails();

        VisualProperties regularPriceVisualTemplate = new VisualProperties().withColor("rgb(119, 119, 119)");
        VisualProperties campaignPriceVisualTemplate = new VisualProperties().withColor("rgb(204, 0, 0)");
        VisualProperties detailRegularPriceVisualTemplate = new VisualProperties().withColor("rgb(102, 102, 102)");

//        Проверяем, что продукты во всем одинаковые кроме оформления
        assertEquals("Продукт и его детали не равны", product, productDetails);
//        Проверяем оформление продукта на главной странице
        assertEquals("На главной странице. Неверная визуализация обычной цены", regularPriceVisualTemplate, product.getPriceVisualProperties().getPropRegularPrice());
        assertEquals("На главной странице. Неверная визуализация акционной цены", campaignPriceVisualTemplate, product.getPriceVisualProperties().getPropCampaignPrice());
        assertTrue("На главной странице. Размер шрифта аукционной цены меньше размера обычной цены",
                product.getPriceVisualProperties().getPropCampaignPrice().getFontSize() >
                        product.getPriceVisualProperties().getPropRegularPrice().getFontSize());

//        Проверяем оформление детализации продукта
        assertEquals("На станице с детализацией продукта. Неверная визуализация обычной цены", detailRegularPriceVisualTemplate, productDetails.getPriceVisualProperties().getPropRegularPrice());
        assertEquals("На станице с детализацией продукта. Неверная визуализация акционной цены", campaignPriceVisualTemplate, productDetails.getPriceVisualProperties().getPropCampaignPrice());
        assertTrue("На станице с детализацией продукта. Размер шрифта аукционной цены меньше размера обычной цены",
                product.getPriceVisualProperties().getPropCampaignPrice().getFontSize() >
                        product.getPriceVisualProperties().getPropRegularPrice().getFontSize());
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
                        .withFontSize(regularPrice.getCssValue("font-size"))
                        .withColor(regularPrice.getCssValue("color")))
                .withCampaignPrice(campaignPrice.getText())
                .withVisualPropCampaignPrice(new VisualProperties()
                        .withFontSize(campaignPrice.getCssValue("font-size"))
                        .withColor(campaignPrice.getCssValue("color")));
    }

}