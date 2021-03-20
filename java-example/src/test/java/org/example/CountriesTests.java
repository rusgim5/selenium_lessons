package org.example;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class CountriesTests extends TestBase{

    @Test
    public void testAlphabetOrderCountries() throws InterruptedException {
        app.countries().checkCountryAndInnerZonesByAlphabeticalOrder();

    }

    @Test
    public void testAlphabetOrderZonesInGeoZonesPage() {
        app.countries().checkZonesInGeoZonesPageByAlphabetOrder();


    }

    @Test
    public void testSwitchWindow() {

        app.countries().editCountry();


    }


}
