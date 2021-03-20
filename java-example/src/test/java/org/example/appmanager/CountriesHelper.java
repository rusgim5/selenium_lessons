package org.example.appmanager;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;

public class CountriesHelper extends HelperBase{
    public CountriesHelper(ApplicationManager app, WebDriver wd, WebDriverWait wait) {
        super(app, wd, wait);
    }

    public boolean checkAlphabeticalOrder(List<String> list) {
        String previous = ""; // empty string: guaranteed to be less than or equal to any other
        for (final String current : list) {
            if (current.equals("")) continue;
            if (previous.compareTo(current) > 0) {
                return false;
            }
            previous = current;
        }
        return true;
    }

    public void checkCountryAndInnerZonesByAlphabeticalOrder() {
        app.session().login();
        app.goTo().countriesPage();

        List<String> countries = wd.findElements(By.cssSelector("[name=countries_form] tr td:nth-of-type(5)")).stream().map((w)->w.getText()).collect(Collectors.toList());
        assertTrue(checkAlphabeticalOrder(countries));

        int countriesCount = countries.size();
        for (int i = 2; i <= countriesCount; i++) {
            WebElement country = wd.findElement(By.cssSelector("[name=countries_form] tr:nth-of-type(" + i + ")"));
            if (Integer.parseInt(country.findElement(By.cssSelector("td:nth-of-type(6)")).getText()) > 0) {
                country.findElement(By.cssSelector("[title=Edit]")).click();
                List<String> zones = wd.findElements(By.cssSelector("table#table-zones tr td:nth-of-type(3)")).stream().map((w)->w.getText()).collect(Collectors.toList());
                assertTrue(checkAlphabeticalOrder(zones));
                app.goTo().countriesPage();
            }
        }
    }

    public void checkZonesInGeoZonesPageByAlphabetOrder() {
        app.session().login();
        app.goTo().geoZonesPage();
        List<WebElement> countries = wd.findElements(By.cssSelector("tr.row"));
        int countriesCount = countries.size();
        for (int i = 2; i <= countriesCount + 1; i++) {
            WebElement country = wd.findElement(By.cssSelector("table.dataTable tr.row:nth-of-type(" + i + ") a"));
            country.click();
            List<String> zones = wd.findElements(By.cssSelector("table#table-zones tr td:nth-child(3) [selected]")).stream().map((w)->w.getText()).collect(Collectors.toList());
            assertTrue(checkAlphabeticalOrder(zones));
            app.goTo().geoZonesPage();
        }
    }

    public void editCountry() {
        app.session().login();
        app.goTo().countriesPage();
        String mainWindow = wd.getWindowHandle();
        Set<String> oldWindows = wd.getWindowHandles();
        click(By.cssSelector("a.button[href*=edit_country]"));
        List<WebElement> externalLinkFields = wd.findElements(By.cssSelector("a[target='_blank']"));
        for (WebElement externalLinkField :externalLinkFields) {
            click(externalLinkField);
            String newWindow = wait.until(thereIsWindowOtherThan(oldWindows));
            wd.switchTo().window(newWindow);
            sleep(1000);
            wd.close();
            wd.switchTo().window(mainWindow);
        }
    }

    private ExpectedCondition<String> thereIsWindowOtherThan(Set<String> oldWindows) {
        return new ExpectedCondition<String>() {
            @NullableDecl
            @Override
            public String apply(@NullableDecl WebDriver input) {
                Set<String> handles=wd.getWindowHandles();
                handles.removeAll(oldWindows);
                return handles.size()>0?handles.iterator().next():null;
            }
        };
    }
}
