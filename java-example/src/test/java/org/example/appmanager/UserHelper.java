package org.example.appmanager;

import org.example.model.UserData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Random;

public class UserHelper extends HelperBase {
    public UserHelper(ApplicationManager app, WebDriver wd, WebDriverWait wait) {
        super(app, wd, wait);
    }

    public void initUserRegistaration() {
        app.session().login();
        app.goTo().url("http://localhost/litecart/en/");
        click(By.xpath("//a[text()='New customers click here']"));
    }

    public void registration(UserData user) {
        initUserRegistaration();
        sleep(500);
        fillUserInfo(user);
        sumintRegistration();
        sleep(500);
        logout();
        sleep(500);
        loginByNewUser(user);
        logout();

    }

    private void loginByNewUser(UserData user) {
        type(By.cssSelector("div#box-account-login input[name=email]"), user.getEmail());
        type(By.cssSelector("div#box-account-login input[name=password]"), user.getPassword());
        click(By.cssSelector("div#box-account-login button[name=login]"));
    }


    private void logout() {
        app.session().logout();
    }

    private void sumintRegistration() {
        click(By.cssSelector("button[name=create_account]"));
    }

    private void fillUserInfo(UserData user) {
        type(By.cssSelector("input[name=firstname]"), user.getName());
        type(By.cssSelector("input[name=lastname]"), user.getLastName());
        type(By.cssSelector("input[name=address1]"), user.getAddress1());
        type(By.cssSelector("input[name=postcode]"), user.getPostcode());
        type(By.cssSelector("input[name=city]"), user.getCity());
        type(By.cssSelector("input[name=email]"), user.getEmail());
        type(By.cssSelector("input[name=phone]"), user.getPhone());
        type(By.cssSelector("input[name=password]"), user.getPassword());
        type(By.cssSelector("input[name=confirmed_password]"), user.getPassword());
        WebElement zone = wd.findElement(By.cssSelector("select[name=zone_code]"));
        WebElement country = wd.findElement(By.cssSelector("select[name=country_code]"));

        selectedByContentText(country, user.getCountryName());
        selectedByIndex(zone, new Random().nextInt(10));
    }
}
