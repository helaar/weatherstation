package org.codehow.collector;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.codehow.model.CurrentWeather;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
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

        Builder builder = new Builder();

        List<String[]> rows = splitRows(table);
        for(String[] row:rows) {
            if(row[0].equals("Klokken:"))
                builder.time(row[1]);
            else if()
        }
        return builder.build();
    }

    private List<String[]> splitRows(String table) {

        List<String[]> ret = new ArrayList<>();
        for (String line: table.split("</tr><tr>")){
            String row = line.replaceAll("<tr>","").replaceAll("</tr>","");
            String cols[] = row.split("</td><td>");
            for(int i=0; i < cols.length;i++)
                cols[i] = cols[i].replaceAll("<td>","").replaceAll("</td>","").trim();
            ret.add(cols);
        }
        return ret;
    }

    private static class Builder {

        private String time;
        private String date;

        Builder time(String s) {
            time = s;
            return this;
        }

        Builder date(String s) {
            date = s;
            return this;
        }

        CurrentWeather build() {

            LocalDateTime dt = LocalDateTime.parse(date+" "+time, DateTimeFormatter.ofPattern("DD/MM/YY HH:mm"));
            return new CurrentWeather("0",dt,);
        }
    }
}
