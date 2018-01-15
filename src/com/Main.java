package com;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static class Dto {
        public double[][] matrixG;
        public double[] matrixI;
    }

    public static void main(String[] args) throws Exception {
        List<InputData> wszystkieRezystory = new ArrayList<>();
        List<InputData> all = new ArrayList<>();

        StringBuilder wynikV = new StringBuilder();
        StringBuilder wynikI = new StringBuilder();
        StringBuilder wynikIr = new StringBuilder();

        MyMatrix myMatrix = new MyMatrix(0);
        Obliczenia obliczanie;

        double[][] matrixG = new double[0][0];
        double[] matrixI = new double[0];

        Dto dto = new Dto();
        dto.matrixG = matrixG;
        dto.matrixI = matrixI;

        InputData data1 = new InputData("s1", 0, 1, 1, ElementType.SI);
        InputData data2 = new InputData("r1", 1, 2, 10, ElementType.R);
        InputData data3 = new InputData("r2", 2, 0, 20, ElementType.R);
        InputData data4 = new InputData("r3", 2, 3, 16, ElementType.R);
        InputData data5 = new InputData("r4", 3, 0, 4, ElementType.R);
        AddInputData(data1, myMatrix, wszystkieRezystory, all);
        AddInputData(data2, myMatrix, wszystkieRezystory, all);
        AddInputData(data3, myMatrix, wszystkieRezystory, all);
        AddInputData(data4, myMatrix, wszystkieRezystory, all);
        AddInputData(data5, myMatrix, wszystkieRezystory, all);


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
            wynikI.append("I").append(i + 1).append(" = ").append(Math.round(macierze.I[i], 2)).append("\n");
        }
        for (InputData data : wszystkieRezystory) {
            wynikIr.append(ObliczIr(data, wynik.V)).append("\n");
        }

        System.out.println(wynikV);
        System.out.println(wynikI);
        System.out.println(wynikIr);
        System.out.println();
        wyswietlElementy(all);

    }

    private static String ObliczIr(InputData data, double[] V) {
        if (V == null) {
            return "V = null";
        }
        String name = data.name;
        double[] newV = new double[V.length + 1];
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
        double V1 = newV[data.node1];
        double V2 = newV[data.node2];
        double G = (1 / data.value);
        double value = Math.round((V1 - V2) * G, 2);
        return name + " = " + value;
    }

    public static void AddInputData(InputData data, MyMatrix myMatrix, List<InputData> rezystory, List<InputData> all) {
        all.add(data);
        myMatrix.AddData(data);
        if (data.type.equals(ElementType.R)) {
            rezystory.add(data);
        }
    }

    public static void wyswietlElementy(List<InputData> inputDataList) {
        System.out.println("nazwa | węzeł+ | węzeł- | wartość | rodzaj");
        System.out.println("------------------------------------------");
        for (InputData data : inputDataList) {
            System.out.printf("%3s   | %4d   | %4d   | %6.2f  | %4s", data.name, data.node1, data.node2, data.value, data.type.toString());
            System.out.println();
        }
        System.out.println("------------------------------------------");

    }
}