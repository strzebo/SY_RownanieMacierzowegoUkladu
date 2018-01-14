package com;

import java.io.Console;
import java.util.ArrayList;
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

    public Wynik Oblicz() throws Exception
    {
        int iloscPrzeksztalcen = IlePrzeksztalcen(false);

        return ObliczWynik(iloscPrzeksztalcen);
    }


    private int IlePrzeksztalcen(boolean czyPierwszeZero)
    {
        int wynik = 1;
        if (czyPierwszeZero)
        {
            for (int i = 0; i < (int) this.Wielkosc; i++)
            {
                if (this.OryginalneI[i] == 0)
                    return i;
            }
        }
        return wynik;
    }

    private Wynik ObliczWynik(int iloscPrzeksztalcen) throws Exception
    {
        ArrayList<Macierze> wszystkieMacierze = new ArrayList<Macierze>();
        wszystkieMacierze.add(new Macierze(KopiujG(this.OryginalneG), this.OryginalneI, 0));

        for (int przeksztalcenie = iloscPrzeksztalcen; przeksztalcenie < (int) this.Wielkosc; przeksztalcenie++)
        {
            Macierze macierzPoprzednia = wszystkieMacierze.get(przeksztalcenie - 1);
            float[][] macierz = KopiujG(macierzPoprzednia.G);
            float[] noweI = KopiujI(macierzPoprzednia.I);

            wszystkieMacierze.add(new Macierze(macierz, noweI, przeksztalcenie));
            for (int i = przeksztalcenie; i < (int) this.Wielkosc; i++)
            {
                for (int j = przeksztalcenie - 1; j < (int) this.Wielkosc; j++)
                {
                    macierz[i][j] = ObliczPrzeksztalcenie(i, j, przeksztalcenie, macierzPoprzednia.G);
                    System.out.printf("Modyfikacja " + przeksztalcenie + ":" + macierz[i][j]);
                }
                noweI[i] = ObliczNoweI(i, przeksztalcenie, macierzPoprzednia.I, macierzPoprzednia.G);
                System.out.printf("I " + przeksztalcenie + ":" + noweI[i]);
            }
        }

        return new Wynik(wszystkieMacierze, ObliczV(wszystkieMacierze.get(wszystkieMacierze.size() - 1) ));
    }


    private float[] ObliczV(Macierze ostatniaMacierz) throws Exception
    {
        if (ostatniaMacierz == null)
            throw new Exception("Nie obliczono wszystkich macierzy !");

        float[] wynik = new float[ostatniaMacierz.Id + 1];
        for (int i = ostatniaMacierz.Id; i >= 0; i--)
        {
            if (i == ostatniaMacierz.Id)
                wynik[i] = WyliczWynikV(ostatniaMacierz, i, wynik, true);
            else
                wynik[i] = WyliczWynikV(ostatniaMacierz, i, wynik, false);
        }
        return wynik;
    }

    private float WyliczWynikV(Macierze macierz, int id, float[] poprzednieV, boolean czyOstatnie )
    {
        if (czyOstatnie)
            return macierz.I[id] / macierz.G[id][id];

        float suma = 0f;

        for(int i = id; i < macierz.Id; i++)
        {
            suma += macierz.G[id][i + 1] * poprzednieV[i + 1];
        }
        return (macierz.I[id] - suma) / macierz.G[id][id];
    }

    private float ObliczNoweI(int i, int przeksztalcenie, float[] poprzednieI, float[][] poprzednia)
    {
        float mianownik = poprzednia[przeksztalcenie - 1][przeksztalcenie - 1];
        if (mianownik == 0)
            mianownik = 1;

        return poprzednieI[przeksztalcenie] - ( poprzednieI[przeksztalcenie - 1] * ((poprzednia[i][przeksztalcenie - 1]) / mianownik));
    }

    private float ObliczPrzeksztalcenie(int i, int j, int przeksztalcenie, float[][] poprzednia) throws Exception
    {
        //int pomniejszoneI = i - 1 >= 0 ? i - 1 : 0;
        if (i > this.OryginalneG.length)
            throw new Exception("Zbyt dużo przekształceń");

        float mianownik = poprzednia[przeksztalcenie - 1][przeksztalcenie - 1];
        if (mianownik == 0)
            mianownik = 1;

        return poprzednia[i][j] - ((poprzednia[przeksztalcenie - 1][j]) * ((poprzednia[i][przeksztalcenie - 1]) / mianownik));
    }

    private float[] KopiujI(float[] poprzednieI)
    {
        float[] newI = new float[(int)this.Wielkosc];
        for(int i = 0; i < this.OryginalneI.length ; i++)
        {
            newI[i] = poprzednieI[i];
        }
        return newI;
    }
    private float[][] KopiujG(float[][] macierzKopiowana)
{
    float[][] kopia = new float[(int)this.Wielkosc][(int)this.Wielkosc];
    for(int i = 0; i < (int) this.Wielkosc; i++)
    {
        for(int j = 0; j < (int) this.Wielkosc; j++)
        {
            kopia[i][j] = macierzKopiowana[i][j];
        }
    }
    return kopia;
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

    public static void rysujMacierz(float[][] macierz) {
        for (int wiersz = 0; wiersz < macierz.length; wiersz++) {
            for (int kolumna = 0; kolumna < macierz[wiersz].length; kolumna++) {
                System.out.printf("%8.2f", macierz[wiersz][kolumna]);
            }
            System.out.println();
        }
    }
}