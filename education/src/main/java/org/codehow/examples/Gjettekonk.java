package org.codehow.examples;

/**
 */
public class Gjettekonk {
    //private static int svaret; =(int)(Math.random()*100.0);
    private static int teller = 0;

    public static void main(String[] args) {

        for(int svaret=0;svaret <=100;svaret++  ) {
            int gjettet =0;
            int hoy = 100;
            int lav = 0;
            teller = 0;

            do {
                gjettet = gjett(lav, hoy);

                if (gjettet < svaret)
                    lav = gjettet;
                else if (gjettet > svaret) {
                    hoy = gjettet;
                }

                if (gjettet == svaret)
                    System.out.println(String.format("Hurra! Du gjettet %d på %d forsøk", svaret, teller));
            } while (gjettet != svaret);

        }
    }

    private static int gjett(double min, double maks) {
        teller ++;
        return (int)((maks - min +1)/3+min);
        //return (int)(Math.random()*(maks-min)+min);
    }
}
