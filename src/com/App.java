package com;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    private List<InputData> allResistors = new ArrayList<>();
    private List<InputData> all = new ArrayList<>();
    private MyMatrix myMatrix = new MyMatrix(0);
    private double[][] matrixG = new double[0][0];
    private double[] matrixI = new double[0];

    public void run() throws Exception {
        initData();
        prepareMatrixAfterAddedData();
        Obliczenia obliczanie = new Obliczenia(matrixI, matrixG);
        Obliczenia.Wynik wynik = obliczanie.Oblicz();
        printResult(wynik);

        getDataFromUserAndCalc();
    }

    private InputData getDataFromUser() throws Exception {
        InputData data = null;
        Scanner scan = new Scanner(System.in);
        String name = scan.next();

        try {
            int node1 = Integer.parseInt(scan.next());
            int node2 = Integer.parseInt(scan.next());
            double value = Double.parseDouble(scan.next());
            ElementType type = ElementType.valueOf(scan.next());
            data = new InputData(name, node1, node2, value, type);
        } catch (Exception e) {
            scan.remove();
            scan.reset();
            scan.close();
            System.out.println("Niepoprawne dane wejsciowe!");
            throw e;
        }

        return data;
    }

    private void getDataFromUserAndCalc() {


        while (true) {
            try {
                System.out.println();
                System.out.println("Dodaj nowe dane w formacie");
                System.out.println("<nazwa> <węzeł+> <węzeł-> <vartosc> <typ - Si | R | Iz | >");

                InputData data = getDataFromUser();
                AddInputData(data, myMatrix, allResistors, all);
                prepareMatrixAfterAddedData();
                Obliczenia obliczanie = new Obliczenia(matrixI, matrixG);
                Obliczenia.Wynik wynik = obliczanie.Oblicz();
                printResult(wynik);
            } catch (Exception e) {
                System.out.println("Niepoprawne dane wejsciowe! Spróbuj ponowanie");
            }
        }

    }

    private String ObliczIr(InputData data, double[] V) {
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

    private void AddInputData(InputData data, MyMatrix myMatrix, List<InputData> rezystory, List<InputData> all) {
        all.add(data);
        myMatrix.AddData(data);
        if (data.type.equals(ElementType.R)) {
            rezystory.add(data);
        }
    }

    private void initData() {
        InputData data1 = new InputData("s1", 0, 1, 1, ElementType.SI);
        InputData data2 = new InputData("r1", 1, 2, 10, ElementType.R);
        InputData data3 = new InputData("r2", 2, 0, 20, ElementType.R);
        InputData data4 = new InputData("r3", 2, 3, 16, ElementType.R);
        InputData data5 = new InputData("r4", 3, 0, 4, ElementType.R);
        AddInputData(data1, myMatrix, allResistors, all);
        AddInputData(data2, myMatrix, allResistors, all);
        AddInputData(data3, myMatrix, allResistors, all);
        AddInputData(data4, myMatrix, allResistors, all);
        AddInputData(data5, myMatrix, allResistors, all);
    }

    private void printResult(Obliczenia.Wynik wynik) {

        StringBuilder wynikV = new StringBuilder();
        StringBuilder wynikI = new StringBuilder();
        StringBuilder wynikIr = new StringBuilder();

        for (int i = 0; i < wynik.V.length; i++) {
            wynikV.append(myMatrix.matrixV[i + 1]).append(" = ").append(Math.round(wynik.V[i], 2)).append("\n");
        }
        Obliczenia.Macierze macierze = wynik.WszystkieMacierze.get(wynik.WszystkieMacierze.size() - 1);
        for (int i = 0; i < macierze.I.length; i++) {
            wynikI.append("I").append(i + 1).append(" = ").append(Math.round(macierze.I[i], 2)).append("\n");
        }
        for (InputData data : allResistors) {
            wynikIr.append(ObliczIr(data, wynik.V)).append("\n");
        }

        System.out.println(wynikV);
        System.out.println(wynikI);
        System.out.println(wynikIr);
        System.out.println();
        Printer.printElements(all);
        System.out.println();
        Printer.printMatrix(myMatrix);
    }

    private void prepareMatrixAfterAddedData() {
        App.Dto dto = new App.Dto();
        dto.matrixG = matrixG;
        dto.matrixI = matrixI;

        myMatrix.Utnij(dto);

        matrixG = dto.matrixG;
        matrixI = dto.matrixI;
    }

    public static class Dto {
        public double[][] matrixG;
        public double[] matrixI;
    }

}
