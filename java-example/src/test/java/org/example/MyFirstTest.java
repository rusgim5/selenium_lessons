package org.example;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class MyFirstTest extends TestBase {

    @Test
    public void myFirstTest() {
//        wd.navigate().to("http://www.google.com");
//        type(By.name("q"), "webdriver");
//        click(By.name("btnK"));
//        wait.until(titleIs("webdriver - Поиск в Google"));
    }

    @Test
    public void testLogin() {
        app.session().login();
    }


    @Test
    public void testHeaders() {
        app.goTo().checkMenu();

    }

    @Test
    public void testStickers() {
        app.product().checkStickers();

    }


    //    Перемудрил, нужен рефакторинг). Но в целом, задачу выполняет).


}