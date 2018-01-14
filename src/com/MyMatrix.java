package com;

public class MyMatrix {
    public double[][] matrixG;
    public double[] matrixI;
    public String[] matrixV;
    private int size;

    public MyMatrix(int size) {
        this.size = size;
        matrixG = new double[size][size];
        matrixI = new double[size];
        matrixV = new String[size];
    }

    private void IncreaseMatrixSize(int increaseToSize) {
        matrixG = KopiujG(matrixG, increaseToSize + 1);
        matrixI = KopiujI(matrixI, increaseToSize + 1);
        matrixV = KopiujV(matrixV, increaseToSize + 1);
    }

    public void Utnij(Main.Dto dto) {
        double[] newI = new double[matrixI.length - 1];
        double[][] newG = new double[matrixG.length - 1][matrixG.length - 1];

        for (int i = 1; i < matrixI.length; i++) {
            newI[i - 1] = matrixI[i];
        }
        //matrixI = newI;
        dto.matrixI = newI;
        for (int i = 1; i < matrixG.length; i++) {
            for (int j = 1; j < matrixG.length; j++) {
                newG[i - 1][j - 1] = matrixG[i][j];
            }
        }
        //matrixG = newG;
        dto.matrixG = newG;
    }

    private String[] KopiujV(String[] poprzednieI, int nowaWielkosc) {
        String[] newV = new String[nowaWielkosc];
        for (int i = 0; i < poprzednieI.length; i++) {
            newV[i] = poprzednieI[i];
        }
        size = nowaWielkosc;
        return newV;
    }

    private double[] KopiujI(double[] poprzednieI, int nowaWielkosc) {
        double[] newI = new double[nowaWielkosc];
        for (int i = 0; i < poprzednieI.length; i++) {
            newI[i] = poprzednieI[i];
        }
        size = nowaWielkosc;
        return newI;
    }

    private double[][] KopiujG(double[][] macierzKopiowana, int nowaWielkosc) {
        double[][] kopia = new double[nowaWielkosc][nowaWielkosc];
        for (int i = 0; i < macierzKopiowana.length; i++) {
            for (int j = 0; j < macierzKopiowana.length; j++) {
                kopia[i][j] = macierzKopiowana[i][j];
            }
        }
        size = nowaWielkosc;
        return kopia;
    }

    public void AddData(InputData data) {
        ElementTpe type = data.type;

        if (type.equals(ElementTpe.SI)) {
            AddIS(data);
        } else if (type.equals(ElementTpe.R)) {
            AddR(data);
        } else if (type.equals(ElementTpe.Iv)) {
            AddIv(data);
        }
    }

    private void AddIv(InputData data) {
        int maxSize;
        int nPlus;
        int nMinus;

        if (data.node1 > data.node2) {
            maxSize = data.node1 + 1;
        } else {
            maxSize = data.node2 + 1;
        }

        nPlus = data.node1;
        nMinus = data.node2;

        if (matrixG == null || matrixG.length == 0) {
            matrixG = new double[maxSize][maxSize];
        }
        if (matrixI == null || matrixI.length == 0) {
            matrixI = new double[maxSize];
        }

        maxSize = matrixI.length;

        // będzie zawsze za mała
        IncreaseMatrixSize(maxSize);
        double value = data.value * 10f;
        matrixG[maxSize][nPlus] += 1;
        matrixG[maxSize][nMinus] += -1;
        matrixG[nPlus][maxSize] += 1;
        matrixG[nMinus][maxSize] += -1;
        matrixV[maxSize] = "I" + data.name;
        matrixV[nPlus] = "V" + nPlus;
        matrixV[nMinus] = "V" + nMinus;
        matrixI[maxSize] += value;
        MatrixVAddZeroToEmpty();
    }

    private void AddR(InputData data) {
        int maxSize;
        int nPlus;
        int nMinus;

        if (data.node1 > data.node2) {
            maxSize = data.node1;
        } else {
            maxSize = data.node2;
        }
        nPlus = data.node1;
        nMinus = data.node2;

        if (matrixG == null) {
            matrixG = new double[maxSize][maxSize];
        }
        if (matrixI == null) {
            matrixI = new double[maxSize];
        }

        if (matrixG.length > maxSize) {
            //odpowiednia wielkosc
            double value = (1f / data.value) * 10f;
            matrixG[nPlus][nPlus] += value;
            matrixG[nMinus][nMinus] += value;
            matrixG[nPlus][nMinus] += -value;
            matrixG[nMinus][nPlus] += -value;
            matrixV[nPlus] = "V" + nPlus;
            matrixV[nMinus] = "V" + nMinus;
            MatrixVAddZeroToEmpty();
        } else {
            // za mała
            IncreaseMatrixSize(maxSize);
            double value = (1f / data.value) * 10f;
            matrixG[nPlus][nPlus] += value;
            matrixG[nMinus][nMinus] += value;
            matrixG[nPlus][nMinus] += -value;
            matrixG[nMinus][nPlus] += -value;
            matrixV[nPlus] = "V" + nPlus;
            matrixV[nMinus] = "V" + nMinus;
            MatrixVAddZeroToEmpty();
        }
    }

    private void AddIS(InputData data) {
        int maxSize;
        int nPlus;
        int nMinus;

        if (data.node1 > data.node2) {
            maxSize = data.node1;
        } else {
            maxSize = data.node2;
        }
        nPlus = data.node1;
        nMinus = data.node2;

        if (matrixG == null) {
            matrixG = new double[maxSize][maxSize];
        }
        if (matrixI == null) {
            matrixI = new double[maxSize];
        }

        if (matrixI.length > maxSize) {
            //odpowiednia wielkosc
            double value = data.value * 10f;
            matrixI[nPlus] += -value;
            matrixI[nMinus] += value;
            matrixV[nPlus] = "V" + nPlus;
            matrixV[nMinus] = "V" + nMinus;
            MatrixVAddZeroToEmpty();
        } else {
            // za mała
            IncreaseMatrixSize(maxSize);
            double value = data.value * 10f;
            matrixI[nPlus] += -value;
            matrixI[nMinus] += value;
            matrixV[nPlus] = "V" + nPlus;
            matrixV[nMinus] = "V" + nMinus;
            MatrixVAddZeroToEmpty();
        }
    }

    private void MatrixVAddZeroToEmpty() {
        for (int i = 0; i < matrixV.length; i++) {
            if (matrixV[i] == null || matrixV[i].isEmpty()) {
                matrixV[i] = "0";
            }
        }
    }
}
