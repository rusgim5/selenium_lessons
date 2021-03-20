package org.example;

import org.example.model.UserData;
import org.junit.Test;

public class UserTests extends TestBase {
    @Test
    public void testUserRegistration() throws InterruptedException {
        UserData user = new UserData();
        app.user().registration(user);
    }
}
