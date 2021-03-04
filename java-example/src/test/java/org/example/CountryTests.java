package org.example;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;

public class CountryTests extends TestBase{

    @Test
    public void testAlphabetOrderCountries() throws InterruptedException {
        login();
        goTo("http://localhost/litecart/admin/?app=countries&doc=countries");

        List<String> countries = wd.findElements(By.cssSelector("[name=countries_form] tr td:nth-of-type(5)")).stream().map((w)->w.getText()).collect(Collectors.toList());
        assertTrue(checkAlphabeticalOrder(countries));

        int countriesCount = countries.size();
        for (int i = 2; i <= countriesCount; i++) {
            WebElement country = wd.findElement(By.cssSelector("[name=countries_form] tr:nth-of-type(" + i + ")"));
            if (Integer.parseInt(country.findElement(By.cssSelector("td:nth-of-type(6)")).getText()) > 0) {
                country.findElement(By.cssSelector("[title=Edit]")).click();
                List<String> zones = wd.findElements(By.cssSelector("table#table-zones tr td:nth-of-type(3)")).stream().map((w)->w.getText()).collect(Collectors.toList());
                assertTrue(checkAlphabeticalOrder(zones));
                goTo("http://localhost/litecart/admin/?app=countries&doc=countries");
            }
        }
    }

    @Test
    public void testAlphabetOrderZonesInGeoZonesPage() {
        login();
        goTo("http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones");
        List<WebElement> countries = wd.findElements(By.cssSelector("tr.row"));
        int countriesCount = countries.size();
        for (int i = 2; i <= countriesCount + 1; i++) {
            WebElement country = wd.findElement(By.cssSelector("table.dataTable tr.row:nth-of-type(" + i + ") a"));
            country.click();
            List<String> zones = wd.findElements(By.cssSelector("table#table-zones tr td:nth-child(3) [selected]")).stream().map((w)->w.getText()).collect(Collectors.toList());
            assertTrue(checkAlphabeticalOrder(zones));
            goTo("http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones");
        }

    }

}
