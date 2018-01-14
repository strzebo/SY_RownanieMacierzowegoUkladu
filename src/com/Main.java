package com;

import java.io.Console;

public class Main
{
    public static void main(String[] args) throws Exception
    {
        float[][] g = new float[3][3];
        float[] i = new float[3];
        i[0] = 10;
        i[1] = 0;
        i[2] = 0;
        g[0][0] = 1;
        g[0][1] = -1;
        g[0][2] = 0;
        g[1][0] = -1;
        g[1][1] = 2.125f;
        g[1][2] = -0.625f;
        g[2][0] = 0;
        g[2][1] = -0.625f;
        g[2][2] = 3.125f;

        Obliczenia obliczenia = new Obliczenia(i, g);
        Obliczenia.Wynik wynik = obliczenia.Oblicz();
        for(int v = 0; v < wynik.V.length; v++)
        {
            int zmienna = v + 1;
            System.out.printf("V" + zmienna + " : " + wynik.V[v]);
        }

        System.out.println();
    }
}