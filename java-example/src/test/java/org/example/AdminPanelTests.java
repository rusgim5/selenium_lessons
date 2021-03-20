package org.example;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.Assert.*;

public class AdminPanelTests extends TestBase {
    @Test
    public void testLogs() {
        app.session().login();
        app.goTo().catalogPage();
        int productCount = wd.findElements(By.xpath("//tr[./td/a[contains(@href,'product_id') and not(@title)]]")).size();
        for (int i = 1; i < productCount; i++) {
            WebElement h1 = wd.findElement(By.tagName("h1"));
            WebElement product = wd.findElement(By.xpath("//tr[./td/a[contains(@href,'product_id') and not(@title)]]["+i+"]//a"));
            product.click();
            wait.until(ExpectedConditions.stalenessOf(h1));
            wd.manage().logs().get("browser").getAll().forEach(l-> assertThat(l, CoreMatchers.equalTo("")));
            app.goTo().catalogPage();
        }
    }
}
