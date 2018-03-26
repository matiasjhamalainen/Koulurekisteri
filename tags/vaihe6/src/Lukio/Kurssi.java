package Lukio;

import java.io.*;

import fi.jyu.mit.ohj2.Mjonot;

import static kanta.HetuTarkistus.rand;

/**
 * Kurssi joka osaa mm. itse huolehtia tunnus_nro:staan.
 * @author laolkorh ja majuhama
 * @version 1.0, 10.03.2016
 */
public class Kurssi {
    private int kid;
    private int oid;
    private String oppiaine;
    private int kurssinro;


    private static int seuraavaNro = 1;


    /**
     * Alustetaan kurssi.  Toistaiseksi ei tarvitse tehd‰ mit‰‰n
     */
    public Kurssi() {
        // Viel‰ ei tarvita mit‰‰n
    }


    /**
     * Alustetaan tietyn oppilaan kurssi.  
     * @param oid oppilaan viitenumero 
     */
    public Kurssi(int oid) {
        this.oid = oid;
    }


    /**
     * Apumetodi, jolla saadaan t‰ytetty‰ testiarvot Kurssille.
     * Kurssin numero arvotaan, jotta kahdella kurssilla ei v‰ltt‰m‰tt‰ olisi
     * samoja tietoja.
     * @param nro viite oppilaaseen, jonka kurssista on kyse
     */
    public void vastaaMatematiikka(int nro) {
    	oid = nro;
    	oppiaine = "Matematiikka";
    	kurssinro = rand(1, 10);
    }


    /**
     * Tulostetaan kurssin tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println("Kurssi: " + oppiaine + " " + kurssinro);
    }


    /**
     * Tulostetaan oppilaan tiedot
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }


    /**
     * Antaa kurssille seuraavan kurssin numeron "kid".
     * @return kurssin uusi "kid"
     * @example
     * <pre name="test">
     *   Kurssi matikka = new Kurssi();
     *   matikka.getKid() === 0;
     *   matikka.rekisteroi();
     *   Kurssi matikka1 = new Kurssi();
     *   matikka1.rekisteroi();
     *   int n1 = matikka.getKid();
     *   int n2 = matikka1.getKid();
     *   n1 === n2-1;
     * </pre>
     */
    public int rekisteroi() {
        kid = seuraavaNro;
        seuraavaNro++;
        return kid;
    }
    
    /**
     * Asettaa kid:n ja samalla varmistaa ett‰
     * seuraava numero on aina suurempi kuin t‰h‰n menness‰ suurin.
     * @param nro asetettava kid
     */
    private void setKid(int nro) {
        kid = nro;
        if ( kid >= seuraavaNro ) seuraavaNro = kid + 1;
    }


    /**
     * Palautetaan kurssin oma id
     * @return kurssin id
     */
    public int getKid() {
        return kid;
    }


    /**
     * Palautetaan mille oppilaalle kurssi kuuluu
     * @return oppilaan id
     */
    public int getOid() {
        return oid;
    }
    
    /**
     * Selvitt‰‰ kurssin tiedot |-merkill‰ erotellusta merkkijonosta.
     * Pit‰‰ huolen ett‰ seuraavaNro on suurempi kuin tuleva kid.
     * @param rivi josta kurssin tiedot otetaan
     * @example
     * <pre name="test">
     *   Kurssi kurssi = new Kurssi();
     *   kurssi.parse("   2   |  matematiikka | 3 | 1  ");
     *   kurssi.getOid() === 2;
     *   kurssi.toString()    === "2|matematiikka|3|1";
     * </pre>
     */
    public void parse(String rivi) {
        StringBuffer sb = new StringBuffer(rivi);
        oid = Mjonot.erota(sb, '|', oid);
        oppiaine = Mjonot.erota(sb, '|', oppiaine);
        kurssinro = Mjonot.erota(sb, '|', kurssinro);
        setKid(Mjonot.erota(sb, '|', getKid()));
    }

    
    /**
     * Palauttaa kurssin tiedot merkkijonona, jonka voi tallentaa tiedostoon.
     * @return kurssi |-merkill‰ eriteltyn‰ merkkijonona 
     */
    @Override
    public String toString() {
        return "" + getOid() + "|" + oppiaine + "|" + kurssinro + "|" + kid;
    }

    /**
     * Testiohjelma Harrastukselle.
     * @param args ei k‰ytˆss‰
     */
    public static void main(String[] args) {
        Kurssi kur = new Kurssi();
        kur.vastaaMatematiikka(2);
        kur.tulosta(System.out);
    }

}
