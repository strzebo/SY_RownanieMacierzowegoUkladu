package com;

import java.util.List;

public class Obliczenia
{
    private float[][] OryginalneG;
    private float[] OryginalneI;
    private double Wielkosc;

    public Obliczenia(float[] oryginalneI, float[][] oryginalneG)
    {
        this.OryginalneG = oryginalneG;
        this.OryginalneI = oryginalneI;
        this.Wielkosc = Math.sqrt(oryginalneG.length);
    }


    private int IlePrzeksztalcen(boolean czyPierwszeZero)
    {
        int wynik = 1;
        if (czyPierwszeZero)
        {
            for (int i = 0; i < this.Wielkosc; i++)
            {
                if (this.OryginalneI[i] == 0)
                    return i;
            }
        }
        return wynik;
    }
}


class Wynik
{
    public List<Macierze> WszystkieMacierze;
    public float[] V;

    public Wynik(List<Macierze> macierze, float[] v)
    {
        this.WszystkieMacierze = macierze;
        this.V = v;
    }
}

class Macierze
{
    public float[][] G;
    public float[] I;
    public int Id;

    public Macierze(float[][] g, float[] i, int id)
    {
        this.G = g;
        this.I = i;
        this.Id = id;
    }
}
