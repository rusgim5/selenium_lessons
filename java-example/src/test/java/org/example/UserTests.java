package org.example;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.Random;

import static org.example.TestBase.BrowserType.FIREFOX;

public class UserTests extends TestBase {
    @Test
    public void testUserRegistration() throws InterruptedException {
        login();
        goTo("http://localhost/litecart/en/");
        click(By.xpath("//a[text()='New customers click here']"));
        final String name = randomString(5);
        final String lastName = "lastName";
        final String address1 = "address1";
        final String postcode = randomNumber(5);
        final String email = name + "@mail.ru";
        final String city = "city";
        final String phone = randomNumber(10);
        final String password = "123123";
        final String countryName = "United States";

        Thread.sleep(500);
        type(By.cssSelector("input[name=firstname]"), name);
        type(By.cssSelector("input[name=lastname]"), lastName);
        type(By.cssSelector("input[name=address1]"), address1);
        type(By.cssSelector("input[name=postcode]"), postcode);
        type(By.cssSelector("input[name=city]"), city);
        type(By.cssSelector("input[name=email]"), email);
        type(By.cssSelector("input[name=phone]"), phone);
        type(By.cssSelector("input[name=password]"), password);
        type(By.cssSelector("input[name=confirmed_password]"), password);

        WebElement zone = wd.findElement(By.cssSelector("select[name=zone_code]"));
        WebElement country = wd.findElement(By.cssSelector("select[name=country_code]"));

        selectedByContentText(country, countryName);
        selectedByIndex(zone, new Random().nextInt(10));

        click(By.cssSelector("button[name=create_account]"));

        Thread.sleep(500);
        click(By.cssSelector("aside#navigation a[href*=logout]"));

        Thread.sleep(500);
        type(By.cssSelector("div#box-account-login input[name=email]"), email);
        type(By.cssSelector("div#box-account-login input[name=password]"), password);
        click(By.cssSelector("div#box-account-login button[name=login]"));

        Thread.sleep(500);
        click(By.cssSelector("aside#navigation a[href*=logout]"));
    }

}
