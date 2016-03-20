package org.codehow.collector;

import org.junit.Test;

public class MarshallerTest {
    String TableRaw = new String ("<tr>Klokken</tr><tr>Dato</tr>");

    @Test
    public void testSplit(){
        for (String retval: TableRaw.split("</tr><tr>")){
            String row = retval.replaceAll("<tr>","").replaceAll("</tr>","");
            System.out.println(row);
        }
    }

}
