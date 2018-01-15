package com;

import java.util.List;

public class Printer {

    public static void printElements(List<InputData> inputDataList) {
        System.out.println("| nazwa | węzeł+ | węzeł- | wartość | rodzaj |");
        System.out.println("----------------------------------------------");
        for (InputData data : inputDataList) {
            System.out.print("|");
            System.out.printf("%4s   | %4d   | %4d   | %6.2f  | %4s   ", data.name, data.node1, data.node2, data.value, data.type.toString());
            System.out.print("|");
            System.out.println();
        }
        System.out.println("----------------------------------------------");
    }

    public static void printMatrix(MyMatrix myMatrix) {
        System.out.println("---------------------------------------------------------------");
        int size = myMatrix.matrixI.length;
        int mid = size / 2;

        for (int wiersz = 0; wiersz < size; wiersz++) {
            System.out.print("|");
            for (int kolumna = 0; kolumna < myMatrix.matrixG[wiersz].length; kolumna++) {
                System.out.printf("%8.3f", myMatrix.matrixG[wiersz][kolumna]);
            }

            System.out.print("  |");

            if (wiersz == mid) {
                System.out.print("  X  ");
            } else {
                System.out.print("     ");
            }

            System.out.printf("| %2s |", myMatrix.matrixV[wiersz]);

            if (wiersz == mid) {
                System.out.print("  =  ");
            } else {
                System.out.print("     ");
            }

            System.out.printf("| %6.2f", myMatrix.matrixI[wiersz]);
            System.out.print("  |");
            System.out.println();
        }
        System.out.println("---------------------------------------------------------------");
    }
}
