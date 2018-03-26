package fxLukio;

import java.util.List;

/**
 * Lukio-luokka, joka huolehtii oppilaista.  P‰‰osin kaikki metodit
 * ovat vain "v‰litt‰j‰metodeja" oppilaisiin.
 * @author laolkorh ja majuhama
 * @version 1.0
 * 10.03.2016
 */
public class Lukio {
    private final Oppilaat oppilaat = new Oppilaat();
    private final Kurssit kurssit = new Kurssit();


    /**
     * Palautaa lukion oppilaat
     * @return oppilaiden m‰‰r‰
     */
    public int getOppilaat() {
        return oppilaat.getLkm();
    }
    
    /**
     * Lis‰t‰‰n uusi kurssi lukioon
     * @param kur lis‰tt‰v‰ kurssi 
     */
    public void lisaa(Kurssi kur) {
        kurssit.lisaa(kur);
    }

    /**
     * Poistaa oppilaista ja kursseista ne joilla on nro. Kesken.
     * @param nro viitenumero, jonka mukaan poistetaan
     * @return montako oppilasta poistettiin
     */
    public int poista(int nro) {
        return 0;
    }


    /**
     * Lis‰‰ lukioon uuden oppilaan
     * @param oppilas lis‰tt‰v‰ oppilas
     * @throws SailoException jos lis‰yst‰ ei voida tehd‰
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * Lukio lukio = new Lukio();
     * Oppilas aku1 = new Oppilas(), aku2 = new Oppilas();
     * aku1.rekisteroi(); aku2.rekisteroi();
     * lukio.getOppilaat() === 0;
     * lukio.lisaa(aku1); lukio.getOppilaat() === 1;
     * lukio.lisaa(aku2); lukio.getOppilaat() === 2;
     * lukio.lisaa(aku1); lukio.getOppilaat() === 3;
     * lukio.getOppilaat() === 3;
     * lukio.annaOppilas(0) === aku1;
     * lukio.annaOppilas(1) === aku2;
     * lukio.annaOppilas(2) === aku1;
     * lukio.annaOppilas(3) === aku1; #THROWS IndexOutOfBoundsException 
     * lukio.lisaa(aku1); lukio.getOppilaat() === 4;
     * lukio.lisaa(aku1); lukio.getOppilaat() === 5;
     * lukio.lisaa(aku1);            #THROWS SailoException
     * </pre>
     */
    public void lisaa(Oppilas oppilas) throws SailoException {
        oppilaat.lisaa(oppilas);
    }


    /**
     * Palauttaa i:n oppilaan
     * @param i monesko oppilas palautetaan
     * @return viite i:teen oppilaaseen
     * @throws IndexOutOfBoundsException jos i v‰‰rin
     */
    public Oppilas annaOppilas(int i) throws IndexOutOfBoundsException {
        return oppilaat.anna(i);
    }
    
    /**
     * Haetaan kaikki oppilaan kurssit
     * @param tunnusnro oppilaan id jolla kursseja haetaan
     * @return tietorakenne jossa viiteet lˆydetteyihin kursseihin
     * @example
     * <pre name="test">
     * #import java.util.*;
     * 
     *  Lukio lukio = new Lukio();
     *  Oppilas aku1 = new Oppilas();
     *  Oppilas aku2 = new Oppilas(); 
     *  Oppilas aku3 = new Oppilas();
     *  aku1.rekisteroi(); aku2.rekisteroi(); aku3.rekisteroi();
     *  int id1 = aku1.getId();
     *  int id2 = aku2.getId();
     *  Kurssit kurssit = new Kurssit();
     *  Kurssi matikka100 = new Kurssi(id1); lukio.lisaa(matikka100);
     *  Kurssi matikka101 = new Kurssi(id1); lukio.lisaa(matikka101);
     *  Kurssi matikka55 = new Kurssi(id2); lukio.lisaa(matikka55);
     *  Kurssi matikka66 = new Kurssi(id2); lukio.lisaa(matikka66);
     *  Kurssi matikka77 = new Kurssi(id2); lukio.lisaa(matikka77);
     *  
     *  List<Kurssi> loytyneet;
     *  loytyneet = lukio.annaKurssit(aku3);
     *  loytyneet.size() === 0;
     *  loytyneet = lukio.annaKurssit(aku1);
     *  loytyneet.size() === 2; 
     *  loytyneet.get(0) == matikka100 === true;
     *  loytyneet.get(1) == matikka101 === true;
     *  loytyneet = lukio.annaKurssit(aku2);
     *  loytyneet.size() === 3; 
     *  loytyneet.get(0) == matikka55 === true;
     * </pre> 
     */
    public List<Kurssi> annaKurssit(Oppilas oppilas) {
        return kurssit.annaKurssit(oppilas.getId());
    }


    /**
     * Lukee lukion tiedot tiedostosta
     * @param nimi jota k‰yte‰‰n lukemisessa
     * @throws SailoException jos lukeminen ep‰onnistuu
     */
    public void lueTiedostosta(String nimi) throws SailoException {
        oppilaat.lueTiedostosta(nimi);
    }


    /**
     * Tallettaa lukion tiedot tiedostoon
     * @throws SailoException jos tallettamisessa ongelmia
     */
    public void talleta() throws SailoException {
        oppilaat.talleta();
        kurssit.talleta();
        
    }


    /**
     * Testiohjelma lukiosta
     * @param args ei k‰ytˆss‰
     */
    public static void main(String args[]) {
        Lukio lukio = new Lukio();

        try {
            // lukio.lueTiedostosta("kelmit");

            Oppilas aku1 = new Oppilas(), aku2 = new Oppilas();
            aku1.rekisteroi();
            aku1.vastaaAkuAnkka();
            aku2.rekisteroi();
            aku2.vastaaAkuAnkka();

            lukio.lisaa(aku1);
            lukio.lisaa(aku2);
            
            int id1 = aku1.getId();
            int id2 = aku2.getId();
            Kurssi pitsi11 = new Kurssi(id1); pitsi11.vastaaMatematiikka(id1); lukio.lisaa(pitsi11);
            Kurssi pitsi12 = new Kurssi(id1); pitsi12.vastaaMatematiikka(id1); lukio.lisaa(pitsi12);
            Kurssi pitsi21 = new Kurssi(id2); pitsi21.vastaaMatematiikka(id2); lukio.lisaa(pitsi21);
            Kurssi pitsi22 = new Kurssi(id2); pitsi22.vastaaMatematiikka(id2); lukio.lisaa(pitsi22);
            Kurssi pitsi23 = new Kurssi(id2); pitsi23.vastaaMatematiikka(id2); lukio.lisaa(pitsi23);

            System.out.println("============= lukion testi =================");

            for (int i = 0; i < lukio.getOppilaat(); i++) {
                Oppilas oppilas = lukio.annaOppilas(i);
                System.out.println("Oppilas paikassa: " + i);
                oppilas.tulosta(System.out);
                List<Kurssi> loytyneet = lukio.annaKurssit(oppilas);
                for (Kurssi kurssi : loytyneet)
                    kurssi.tulosta(System.out);
            }

        } catch (SailoException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
