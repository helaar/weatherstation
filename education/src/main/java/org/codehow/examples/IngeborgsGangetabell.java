package org.codehow.examples;

/**
 */
public class IngeborgsGangetabell {
    public static void main(String[] args) {

        System.out.print("    ");
        for (int o = 1; o <= 10; o++)
            System.out.print(String.format("%4d", o));
        System.out.println();

        for (int i = 1; i <= 10; i++) {
            System.out.print(String.format("%4d", i));
            for (int j = 1; j <= 10; j++)
                System.out.print(String.format("%4d", i * j));
            System.out.println();
        }

    }
}
