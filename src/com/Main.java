package com;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static class Dto {
        public float[][] matrixG;
        public float[] matrixI;
    }

    public static void main(String[] args) throws Exception {
        List<InputData> wszystkieRezystory = new ArrayList<>();

        StringBuilder wynikV = new StringBuilder();
        StringBuilder wynikI = new StringBuilder();
        StringBuilder wynikIr = new StringBuilder();

        MyMatrix myMatrix = new MyMatrix(0);
        Obliczenia obliczanie;

        float[][] matrixG = new float[0][0];
        float[] matrixI = new float[0];

        Dto dto = new Dto();
        dto.matrixG = matrixG;
        dto.matrixI = matrixI;

        InputData data1 = new InputData("s1", 0, 1, 1, ElementTpe.SI);
        InputData data2 = new InputData("r1", 1, 2, 10, ElementTpe.R);
        InputData data3 = new InputData("r2", 2, 0, 20, ElementTpe.R);
        InputData data4 = new InputData("r3", 2, 3, 16, ElementTpe.R);
        InputData data5 = new InputData("r4", 3, 0, 4, ElementTpe.R);
        AddInputData(data1, myMatrix);
        AddInputData(data2, myMatrix);
        AddInputData(data3, myMatrix);
        AddInputData(data4, myMatrix);
        AddInputData(data5, myMatrix);


        myMatrix.Utnij(dto);

        matrixG = dto.matrixG;
        matrixI = dto.matrixI;



        obliczanie = new Obliczenia(matrixI, matrixG);
        Obliczenia.Wynik wynik = obliczanie.Oblicz();

        for (int i = 0; i < wynik.V.length; i++) {
            wynikV.append(myMatrix.matrixV[i + 1]).append(" = ").append(wynik.V[i]).append("\n");
        }
        Obliczenia.Macierze macierze = wynik.WszystkieMacierze.get(wynik.WszystkieMacierze.size() - 1);
        for (int i = 0; i < macierze.I.length; i++) {
            wynikI.append("I").append(i + 1).append(" = ").append(Math.round(macierze.I[i])).append("\n");
        }
        for (InputData data : wszystkieRezystory) {
            wynikIr.append(ObliczIr(data, wynik.V)).append("\n");
        }

        System.out.println(wynikV);
        System.out.println(wynikI);
        System.out.println(wynikIr);
    }

    private static String ObliczIr(InputData data, float[] V) {
        if (V == null) {
            return "V = null";
        }
        String name = data.name;
        float[] newV = new float[V.length + 1];
        newV[0] = 0;
        for (int i = 1; i < newV.length; i++) {
            newV[i] = V[i - 1];
        }
        if (newV.length < data.node1) {
            return "V za małe+";
        }
        if (newV.length < data.node2) {
            return "V za małe-";
        }
        float V1 = newV[data.node1];
        float V2 = newV[data.node2];
        float G = (1 / data.value);
        int value = Math.round((V1 - V2) * G);
        return name + " = " + value;
    }

    public static void AddInputData(InputData data, MyMatrix myMatrix) {
        myMatrix.AddData(data);

    }
}