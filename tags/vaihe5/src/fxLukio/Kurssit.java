package fxLukio;

import java.util.*;

/**
 * Luokka lukion kursseille, joka osaa mm. lis�t� uuden kurssin tietylle oppilaalle
 * @author laolkorh	ja majuhama
 * @version 1.0, 10.03.2016
 */
public class Kurssit implements Iterable<Kurssi> {

    private String tiedostonNimi = "";

    /** Taulukko kursseille */
    private final Collection<Kurssi> alkiot = new ArrayList<Kurssi>();


    /**
     * Kurssien alustaminen
     */
    public Kurssit() {
        // toistaiseksi ei tarvitse tehd� mit��n
    }


    /**
     * Lis�� uuden kurssin tietorakenteeseen. Ottaa kurssin omistukseensa.
     * @param kur lis�tt�v� kurssi. Huom. tietorakenne muuttuu omistajaksi
     */
    public void lisaa(Kurssi kur) {
        alkiot.add(kur);
    }


    /**
     * Lukee oppilaat tiedostosta.  
     * TODO Kesken.
     * @param hakemisto tiedoston hakemisto
     * @throws SailoException jos lukeminen ep�onnistuu
     */
    public void lueTiedostosta(String hakemisto) throws SailoException {
        tiedostonNimi = hakemisto + ".kur";
        throw new SailoException("Ei osata viel� lukea tiedostoa " + tiedostonNimi);
    }


    /**
     * Tallentaa oppilaat tiedostoon.  
     * TODO Kesken.
     * @throws SailoException jos talletus ep�onnistuu
     */
    public void talleta() throws SailoException {
        throw new SailoException("Ei osata viel� tallettaa tiedostoa " + tiedostonNimi);
    }


    /**
     * Palauttaa lukion kurssien lukum��r�n
     * @return kurssien lukum��r�
     */
    public int getLkm() {
        return alkiot.size();
    }


    /**
     * Iteraattori kaikkien kurssien l�pik�ymiseen
     * @return kurssi-iteraattori
     * 
     * @example
     * <pre name="test">
     * #PACKAGEIMPORT
     * #import java.util.*;
     * 
     *  Kurssit kurssit = new Kurssit();
     *  Kurssi matikka33 = new Kurssi(2); kurssit.lisaa(matikka33);
     *  Kurssi matikka44 = new Kurssi(1); kurssit.lisaa(matikka44);
     *  Kurssi matikka55 = new Kurssi(2); kurssit.lisaa(matikka55);
     *  Kurssi matikka66 = new Kurssi(1); kurssit.lisaa(matikka66);
     *  Kurssi matikka77 = new Kurssi(1); kurssit.lisaa(matikka77);
     * 
     *  Iterator<Kurssi> i2=kurssit.iterator();
     *  i2.next() === matikka33;
     *  i2.next() === matikka44;
     *  i2.next() === matikka55;
     *  i2.next() === matikka66;
     *  i2.next() === matikka77;
     *  
     *  int n = 0;
     *  int jnrot[] = {2,1,2,1,1};
     *  
     *  for ( Kurssi kur:kurssit ) { 
     *    kur.getOid() === jnrot[n]; n++;  
     *  }
     *  
     *  n === 5;
     *  
     * </pre>
     */
    @Override
    public Iterator<Kurssi> iterator() {
        return alkiot.iterator();
    }


    /**
     * Haetaan kaikki oppilaan kurssit
     * @param tunnusnro oppilaan id jolla kursseja haetaan
     * @return tietorakenne jossa viiteet l�ydetteyihin kursseihin
     * @example
     * <pre name="test">
     * #import java.util.*;
     * 
     *  Kurssit kurssit = new Kurssit();
     *  Kurssi matikka33 = new Kurssi(2); kurssit.lisaa(matikka33);
     *  Kurssi matikka44 = new Kurssi(1); kurssit.lisaa(matikka44);
     *  Kurssi matikka55 = new Kurssi(2); kurssit.lisaa(matikka55);
     *  Kurssi matikka66 = new Kurssi(1); kurssit.lisaa(matikka66);
     *  Kurssi matikka77 = new Kurssi(2); kurssit.lisaa(matikka77);
     *  Kurssi matikka88 = new Kurssi(5); kurssit.lisaa(matikka88);
     *  
     *  List<Kurssi> loytyneet;
     *  loytyneet = kurssit.annaKurssit(3);
     *  loytyneet.size() === 0; 
     *  loytyneet = kurssit.annaKurssit(1);
     *  loytyneet.size() === 2; 
     *  loytyneet.get(0) == matikka44 === true;
     *  loytyneet.get(1) == matikka66 === true;
     *  loytyneet = kurssit.annaKurssit(5);
     *  loytyneet.size() === 1; 
     *  loytyneet.get(0) == matikka88 === true;
     * </pre> 
     */
    public List<Kurssi> annaKurssit(int tunnusnro) {
        List<Kurssi> loydetyt = new ArrayList<Kurssi>();
        for (Kurssi kur : alkiot)
            if (kur.getOid() == tunnusnro) loydetyt.add(kur);
        return loydetyt;
    }


    /**
     * Testiohjelma kursseille
     * @param args ei k�yt�ss�
     */
    public static void main(String[] args) {
        Kurssit kurssit = new Kurssit();
        Kurssi matikka1 = new Kurssi();
        matikka1.vastaaMatematiikka(2);
        Kurssi matikka2 = new Kurssi();
        matikka2.vastaaMatematiikka(1);
        Kurssi matikka3 = new Kurssi();
        matikka3.vastaaMatematiikka(2);
        Kurssi matikka4 = new Kurssi();
        matikka4.vastaaMatematiikka(2);

        kurssit.lisaa(matikka1);
        kurssit.lisaa(matikka2);
        kurssit.lisaa(matikka3);
        kurssit.lisaa(matikka2);
        kurssit.lisaa(matikka4);

        System.out.println("============= Kurssit testi =================");

        List<Kurssi> kurssit2 = kurssit.annaKurssit(2);

        for (Kurssi kur : kurssit2) {
            System.out.print(kur.getOid() + " ");
            kur.tulosta(System.out);
        }

    }

}
