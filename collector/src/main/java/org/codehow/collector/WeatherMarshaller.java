package org.codehow.collector;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.codehow.model.CurrentWeather;

import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.time.format.DateTimeFormatter.ofPattern;

/**
 */
public class WeatherMarshaller implements Processor {

    private final static Pattern IMGEXP = Pattern.compile("\"main-content\".*<img src=\"(.*?)\"");
    private final static Pattern TABLEEXP = Pattern.compile("<table.*?>(.*?)</table>");
    private final static Pattern ROWEXP = Pattern.compile("<tr>(.*?)</tr>");
    private final static Pattern COLEXP = Pattern.compile("<td.*?>(.*?)</td> *?<td.*?strong>(.*?)</strong></td>");

    @Override
    public void process(Exchange exchange) throws Exception {

        final String body = exchange.getIn().getBody(String.class).replaceAll("\n","").replaceAll("\r","");
        final Matcher imgMatcher = IMGEXP.matcher(body);
        if( imgMatcher.find())
            exchange.getIn().setHeader(Exchange.HTTP_URI,imgMatcher.group(1));

        final Matcher tableMatcher = TABLEEXP.matcher(body);
        if( tableMatcher.find()) {
            CurrentWeather current = parseTable(tableMatcher.group(1));
            exchange.getIn().setBody(current);
            exchange.getIn().setHeader("weather-sample-id", current.getSampleId());
        }

    }

    private CurrentWeather parseTable(String table) {

        Builder builder = new Builder();

        List<String[]> rows = splitRows(table);
        for(String[] row:rows) {
            if("Klokken:".equals(row[0]))
                builder.time = row[1];
            else if("Dato:".equals(row[0]))
                builder.date = row[1];
            else if("Temperatur:".equals(row[0]))
                builder.temp=row[1];
            else if("Vindstyrke:".equals(row[0]))
                builder.wind=row[1];
            else if("Vindretning:".equals(row[0]))
                builder.windDirection=row[1];
            else if("Luftfuktighet:".equals(row[0]))
                builder.hum=row[1];
            else if("Lufttrykk:".equals(row[0]))
                builder.pressure=row[1];
        }
        try {
            return builder.build();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private List<String[]> splitRows(String table) {

        List<String[]> ret = new ArrayList<>();
        Matcher cols = COLEXP.matcher(table);
        while(cols.find()) {
            String label = cols.group(1).trim();
            String value = cols.group(2).trim();
            ret.add(new String[]{label,value});
        }
        return ret;
    }

    private static class Builder {

        private String time;
        private String date;
        private String temp;
        private String wind;
        private String windDirection;
        private String hum;
        private String pressure;

        private Builder() {
        }


        CurrentWeather build() throws ParseException {

            final LocalDateTime dt = LocalDateTime.parse(date+" "+time, ofPattern("dd/MM/yy HH:mm"));
            final NumberFormat nf = NumberFormat.getInstance();
            final double temp = nf.parse(this.temp.replace(".",",")).doubleValue();
            final double wind = nf.parse(this.wind.replace(".",",")).doubleValue();
            final int windDir = nf.parse(this.windDirection.replace(".",",").split(" ")[1]).intValue();
            final int hum = nf.parse(this.hum.replace(".",",")).intValue();
            final double press = nf.parse(this.pressure.replace(".",",")).doubleValue();
            final String id=dt.format(ofPattern("yyyy-MM-dd_HHmm"));
            return new CurrentWeather(id,dt,temp, wind, windDir,hum,press);
        }

    }
}
