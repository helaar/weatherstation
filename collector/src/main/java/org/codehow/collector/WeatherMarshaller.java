package org.codehow.collector;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.codehow.model.CurrentWeather;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 */
public class WeatherMarshaller implements Processor {

    private final static Pattern IMGEXP = Pattern.compile("\"main-content\".*<img src=\"(.*?)\"");
    private final static Pattern TABLEEXP = Pattern.compile("<table.*?>(.*?)</table>");

    @Override
    public void process(Exchange exchange) throws Exception {

        final String body = exchange.getIn().getBody(String.class).replaceAll("\n","").replaceAll("\r","");
        final Matcher imgMatcher = IMGEXP.matcher(body);
        if( imgMatcher.find())
            exchange.getIn().setHeader(Exchange.HTTP_URI,imgMatcher.group(1));

        final Matcher tableMatcher = TABLEEXP.matcher(body);
        if( tableMatcher.find())
            exchange.getIn().setBody(parseTable(tableMatcher.group(1)));

    }

    private CurrentWeather parseTable(String table) {

        CurrentWeather cw = new CurrentWeather();
        // todo: parse
        return cw;
    }


}
