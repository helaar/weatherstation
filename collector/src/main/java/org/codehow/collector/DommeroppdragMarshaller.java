package org.codehow.collector;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 */
public class DommeroppdragMarshaller implements Processor {


    private final static Pattern TABLEEXP = Pattern.compile("<table class.*?matchTable.*?>.*?<tr.*?\"headerRow\".*?</tr>(.*?)</table>");
    private final static Pattern ROWEXPR = Pattern.compile("<tr.*?(<td.*?)</tr>");
    private final static Pattern COLEXPR = Pattern.compile("<td.*?matchnr.*?\"timeSpan\">(.*?)</span>.*?\"division\".*?>(.*?)<.*\"hometeam\".*?<a.*?>(.*?)<.*?\"awayteam\".*?<a.*?>(.*?)<");
    private final static Pattern TEAMEXPR = Pattern.compile("^(.*?) .*([0-9]{1,2})$");


    @Override
    public void process(Exchange exchange) throws Exception {

        final String body = exchange.getIn().getBody(String.class).replaceAll("\n", "").replaceAll("\r", "");
        final Matcher tableMatcher = TABLEEXP.matcher(body);
        final StringBuilder builder = new StringBuilder();
        if (tableMatcher.find()) {
            final String table = tableMatcher.group(1);
            final Matcher rowMatcher = ROWEXPR.matcher(table);
            final int[] order = {2,1,0,3,4};
            int counter = 0;
            while (rowMatcher.find()) {
                counter++;
                final List<String> columns = new ArrayList<>();
                columns.add("");
                final Matcher dataMatcher = COLEXPR.matcher(rowMatcher.group(1));
                if (dataMatcher.find()) {
                    for (int i = 1; i <= dataMatcher.groupCount(); i++)
                        columns.add(transform(i, dataMatcher));

                    for(int col: order )
                        builder.append(columns.get(col)).append(";");
                    builder.append("\n");
                }
            }

            if (counter == 0)
                throw new Exception("Ingen rader funnet i tabellen");

        } else
            throw new Exception("Fant ikke tabellen i body");

        if (builder.length() == 0)
            throw new Exception("Fant ingen kamper");

        exchange.getIn().setBody(builder.toString(), String.class);
    }

    private static String transform(int i, Matcher m) {
        final String value = m.group(i).trim();
        switch (i) {
            case 2:
                return value.substring(0, 1);
            case 3:
            case 4:
                return TEAMEXPR.matcher(value).replaceFirst("$1 $2");
            default:
                return value;
        }
    }
}
