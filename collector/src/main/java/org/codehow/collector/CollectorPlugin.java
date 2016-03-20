package org.codehow.collector;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
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
                        //.to("http:www.skiklubben.no/weatherdisplaylive/images/skistua.jpg")
                        .to("http:m.skiklubben.no")
                        .process(new SkiklubbenMarshaller()).removeHeader("content-type")
                        //.process(serializer)
                        //.to("file:target?fileName=skistua-${exchangeId}.json")
                        .to("http://dummyhost")
                        .to("file:target?fileName=skistua-${exchangeId}.jpg");
            }
        };
    }
}
