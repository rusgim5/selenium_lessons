package org.example;

import net.lightbody.bmp.core.har.Har;
import org.junit.Test;

public class ProxyTests extends TestBase {
    @Test
    public void testProxy() {
        app.proxy.newHar();
        app.session().login();
        Har har = app.proxy.endHar();
        har.getLog().getEntries().forEach(l-> System.out.println(l.getResponse().getStatus()+":"+l.getRequest().getUrl()));

    }
}
