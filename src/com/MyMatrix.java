package com;

public class MyMatrix {
    public float[][] matrixG;
    public float[] matrixI;
    public String[] matrixV;
    private int size;

    public MyMatrix(int size) {
        this.size = size;
        matrixG = new float[size][size];
        matrixI = new float[size];
        matrixV = new String[size];
    }

    private void IncreaseMatrixSize(int increaseToSize) {
        matrixG = KopiujG(matrixG, increaseToSize + 1);
        matrixI = KopiujI(matrixI, increaseToSize + 1);
        matrixV = KopiujV(matrixV, increaseToSize + 1);
    }

    public void Utnij(Main.Dto dto) {
        float[] newI = new float[matrixI.length - 1];
        float[][] newG = new float[matrixG.length - 1][matrixG.length - 1];

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

    private float[] KopiujI(float[] poprzednieI, int nowaWielkosc) {
        float[] newI = new float[nowaWielkosc];
        for (int i = 0; i < poprzednieI.length; i++) {
            newI[i] = poprzednieI[i];
        }
        size = nowaWielkosc;
        return newI;
    }

    private float[][] KopiujG(float[][] macierzKopiowana, int nowaWielkosc) {
        float[][] kopia = new float[nowaWielkosc][nowaWielkosc];
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
            matrixG = new float[maxSize][maxSize];
        }
        if (matrixI == null || matrixI.length == 0) {
            matrixI = new float[maxSize];
        }

        maxSize = matrixI.length;

        // będzie zawsze za mała
        IncreaseMatrixSize(maxSize);
        float value = data.value * 10f;
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
            matrixG = new float[maxSize][maxSize];
        }
        if (matrixI == null) {
            matrixI = new float[maxSize];
        }

        if (matrixG.length > maxSize) {
            //odpowiednia wielkosc
            float value = (1f / data.value) * 10f;
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
            float value = (1f / data.value) * 10f;
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
            matrixG = new float[maxSize][maxSize];
        }
        if (matrixI == null) {
            matrixI = new float[maxSize];
        }

        if (matrixI.length > maxSize) {
            //odpowiednia wielkosc
            float value = data.value * 10f;
            matrixI[nPlus] += -value;
            matrixI[nMinus] += value;
            matrixV[nPlus] = "V" + nPlus;
            matrixV[nMinus] = "V" + nMinus;
            MatrixVAddZeroToEmpty();
        } else {
            // za mała
            IncreaseMatrixSize(maxSize);
            float value = data.value * 10f;
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
