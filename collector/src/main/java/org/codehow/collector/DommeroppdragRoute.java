package org.codehow.collector;

import org.apache.camel.builder.RouteBuilder;

/**
 */
public class DommeroppdragRoute extends RouteBuilder {

    private final int dagnr;

    public DommeroppdragRoute(int dagnr) {
        this.dagnr = dagnr;
    }


    @Override
    public void configure() throws Exception {

        final String[] baner = {"11341635", "11341987", "11341988", "11341989"};
        int i=1;

        for (String bane : baner) {
            from(String.format("timer:%s?repeatCount=1", bane))
                .to(String.format("http://miniserientrondheimvest.cups.nu/2017,nb/result/arena/%s/%d", bane, dagnr))
                .process(new DommeroppdragMarshaller()).removeHeader("content-type")
                .to(String.format("file:target?charset=iso-8859-1&fileName=bane%d-%d.csv", (i++), dagnr));
        }
    }
}
