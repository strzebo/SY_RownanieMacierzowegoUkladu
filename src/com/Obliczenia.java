package com;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

public class Obliczenia
{
    private double[][] OryginalneG;
    private double[] OryginalneI;
    private double Wielkosc;

    public Obliczenia(double[] oryginalneI, double[][] oryginalneG)
    {
        this.OryginalneG = oryginalneG;
        this.OryginalneI = oryginalneI;
        this.Wielkosc = oryginalneG.length;
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
            double[][] macierz = KopiujG(macierzPoprzednia.G);
            double[] noweI = KopiujI(macierzPoprzednia.I);

            wszystkieMacierze.add(new Macierze(macierz, noweI, przeksztalcenie));
            for (int i = przeksztalcenie; i < (int) this.Wielkosc; i++)
            {
                for (int j = przeksztalcenie - 1; j < (int) this.Wielkosc; j++)
                {
                    macierz[i][j] = ObliczPrzeksztalcenie(i, j, przeksztalcenie, macierzPoprzednia.G);
                }
                noweI[i] = ObliczNoweI(i, przeksztalcenie, macierzPoprzednia.I, macierzPoprzednia.G);
            }
        }

        return new Wynik(wszystkieMacierze, ObliczV(wszystkieMacierze.get(wszystkieMacierze.size() - 1) ));
    }


    private double[] ObliczV(Macierze ostatniaMacierz) throws Exception
    {
        if (ostatniaMacierz == null)
            throw new Exception("Nie obliczono wszystkich macierzy !");

        double[] wynik = new double[ostatniaMacierz.Id + 1];
        for (int i = ostatniaMacierz.Id; i >= 0; i--)
        {
            if (i == ostatniaMacierz.Id)
                wynik[i] = WyliczWynikV(ostatniaMacierz, i, wynik, true);
            else
                wynik[i] = WyliczWynikV(ostatniaMacierz, i, wynik, false);
        }
        return wynik;
    }

    private double WyliczWynikV(Macierze macierz, int id, double[] poprzednieV, boolean czyOstatnie )
    {
        if (czyOstatnie)
            return macierz.I[id] / macierz.G[id][id];

        double suma = 0f;

        for(int i = id; i < macierz.Id; i++)
        {
            suma += macierz.G[id][i + 1] * poprzednieV[i + 1];
        }
        return (macierz.I[id] - suma) / macierz.G[id][id];
    }

    private double ObliczNoweI(int i, int przeksztalcenie, double[] poprzednieI, double[][] poprzednia)
    {
        double mianownik = poprzednia[przeksztalcenie - 1][przeksztalcenie - 1];
        if (mianownik == 0)
            mianownik = 1;

        return poprzednieI[przeksztalcenie] - ( poprzednieI[przeksztalcenie - 1] * ((poprzednia[i][przeksztalcenie - 1]) / mianownik));
    }

    private double ObliczPrzeksztalcenie(int i, int j, int przeksztalcenie, double[][] poprzednia) throws Exception
    {
        //int pomniejszoneI = i - 1 >= 0 ? i - 1 : 0;
        if (i > this.OryginalneG.length)
            throw new Exception("Zbyt dużo przekształceń");

        double mianownik = poprzednia[przeksztalcenie - 1][przeksztalcenie - 1];
        if (mianownik == 0)
            mianownik = 1;

        return poprzednia[i][j] - ((poprzednia[przeksztalcenie - 1][j]) * ((poprzednia[i][przeksztalcenie - 1]) / mianownik));
    }

    private double[] KopiujI(double[] poprzednieI)
    {
        double[] newI = new double[(int)this.Wielkosc];
        for(int i = 0; i < this.OryginalneI.length ; i++)
        {
            newI[i] = poprzednieI[i];
        }
        return newI;
    }
    private double[][] KopiujG(double[][] macierzKopiowana)
{
    double[][] kopia = new double[(int)this.Wielkosc][(int)this.Wielkosc];
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
        public double[] V;

        public Wynik(List<Macierze> macierze, double[] v)
        {
            this.WszystkieMacierze = macierze;
            this.V = v;
        }
    }

    class Macierze
    {
        public double[][] G;
        public double[] I;
        public int Id;

        public Macierze(double[][] g, double[] i, int id)
        {
            this.G = g;
            this.I = i;
            this.Id = id;
        }
    }

    public static void rysujMacierz(double[][] macierz) {
        for (int wiersz = 0; wiersz < macierz.length; wiersz++) {
            for (int kolumna = 0; kolumna < macierz[wiersz].length; kolumna++) {
                System.out.printf("%8.2f", macierz[wiersz][kolumna]);
            }
            System.out.println();
        }
    }
}