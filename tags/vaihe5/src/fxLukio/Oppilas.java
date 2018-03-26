package fxLukio;

import java.io.*;
import static kanta.HetuTarkistus.*;

/**
 * Lukion oppilas, joka osaa huolehtia mm. id:staan, osoitteestaan.
 * 
 * @author majuhama, laolkorh
 * @version 25.2.2016
 *
 */
public class Oppilas {

	private int oid;
	private String nimi = "";
	private String hetu = "";
	private String katuosoite = "";
	private String postinumero = "";
	private String postiosoite = "";
	private String puhelinnumero = "";
	private int aloitusvuosi = 0;
	private String sahkoposti = "";
	
	private static int seuraavaNro    = 1;
	
	/**
     * @return oppilaan nimi
     * @example
     * <pre name="test">
     *   Oppilas pertti = new Oppilas();
     *   pertti.vastaaAkuAnkka();
     *   pertti.getNimi() =R= "Ankka Aku .*";
     * </pre>
     */
    public String getNimi() {
        return nimi;
    }
    
    /**
     * Tulostetaan henkil�n tiedot
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    
    /**
     * Tulostetaan henkil�n tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(String.format("%03d", oid, 3) + "  " + nimi + "  " + hetu);
        out.println("  Osoite: " + katuosoite + "  " + postinumero + " " + postiosoite);
        out.println("  Puhelin: " + puhelinnumero);
        out.println("  Sposti: " + sahkoposti);
        out.println("  Aloittanut " + aloitusvuosi + ".");
        
    }


    
    /**
     * Apumetodi, jolla saadaan t�ytetty� testiarvot j�senelle.
     * @param apuhetu hetu joka annetaan henkil�lle 
     */
    public void vastaaAkuAnkka(String apuhetu) {
        nimi = "Ankka Aku " + rand(1000, 9999);
        hetu = apuhetu;
        katuosoite = "Ankkakuja 6";
        postinumero = "12345";
        postiosoite = "ANKKALINNA";
        puhelinnumero = "12-1234";
        aloitusvuosi = 1996;
        sahkoposti = "aku.ankka@ankka24.fi";
        
    }


    /**
     * Apumetodi, jolla saadaan t�ytetty� testiarvot j�senelle.
     * Henkil�tunnus arvotaan, jotta kahdella j�senell� ei olisi
     * samoja tietoja.
     */
    public void vastaaAkuAnkka() {
        String apuhetu = arvoHetu();
        vastaaAkuAnkka(apuhetu);
    }
    
    /**
     * Antaa oppilaalle seuraavan rekisterinumeron.
     * @return oppilaan uusi oid
     * @example
     * <pre name="test">
     *   Oppilas aku1 = new Oppilas();
     *   aku1.getId() === 0;
     *   aku1.rekisteroi();
     *   Oppilas aku2 = new Oppilas();
     *   aku2.rekisteroi();
     *   int n1 = aku1.getId();
     *   int n2 = aku2.getId();
     *   n1 === n2-1;
     * </pre>
     */
    public int rekisteroi() {
        oid = seuraavaNro;
        seuraavaNro++;
        return oid;
    }


    /**
     * Palauttaa oppilaan id:n.
     * @return oppilaan id
     */
    public int getId() {
        return oid;
    }

    public static void main(String args[]) {
        Oppilas aku = new Oppilas(), aku2 = new Oppilas();
        aku.rekisteroi();
        aku2.rekisteroi();
        aku.tulosta(System.out);
        aku.vastaaAkuAnkka();
        aku.tulosta(System.out);
      
        aku2.vastaaAkuAnkka();
        aku2.tulosta(System.out);
      
        aku2.vastaaAkuAnkka();
        aku2.tulosta(System.out);
    }

}