package org.codehow.collector;

import org.apache.camel.builder.RouteBuilder;
import org.kantega.reststop.api.Export;
import org.kantega.reststop.api.Plugin;

/**
 */
@Plugin
public class CollectorPlugin {

    @Export
    private final RouteBuilder imageRoute;

    public CollectorPlugin() {
        imageRoute = new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("timer:imageTimer?period=60000")
                        .to("http:www.skiklubben.no/weatherdisplaylive/images/skistua.jpg")
                        .to("file:target?fileName=skistua-${exchangeId}.jpg");
            }
        };
    }
}
