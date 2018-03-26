package Lukio;

import fi.jyu.mit.ohj2.Mjonot;
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
     * Tulostetaan henkilön tiedot
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    
    /**
     * Tulostetaan henkilön tiedot
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
     * Apumetodi, jolla saadaan täytettyä testiarvot jäsenelle.
     * @param apuhetu hetu joka annetaan henkilölle 
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
     * Apumetodi, jolla saadaan täytettyä testiarvot jäsenelle.
     * Henkilötunnus arvotaan, jotta kahdella jäsenellä ei olisi
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
    
    /**
     * Palauttaa oppilaan tiedot merkkijonona jonka voi tallentaa tiedostoon.
     * @return oppilas tolppaeroteltuna merkkijonona 
     * @example
     * <pre name="test">
     *   Oppilas oppilas = new Oppilas();
     *   oppilas.parse("   3  |  Lmao Ayy   | 030201-111C");
     *   oppilas.toString().startsWith("3|Lmao Ayy|030201-111C|") === true; // on enemmäkin kuin 3 kenttää, siksi loppu |
     * </pre>  
     */
    @Override
    public String toString() {
        return "" +
                getId() + "|" +
                nimi + "|" +
                hetu + "|" +
                aloitusvuosi + "|" +
                katuosoite + "|" +
                postinumero + "|" +
                postiosoite + "|" +
                sahkoposti + "|" +
                puhelinnumero;
                  
    }
    
    /**
     * Selvittää oppilaan tiedot "|" erotellusta merkkijonosta
     * Pitää huolen että seuraavaNro on suurempi kuin tuleva oid.
     * @param rivi josta oppilaan tiedot otetaan
     * 
     * @example
     * <pre name="test">
     *   Oppilas oppilas = new Oppilas();
     *   oppilas.parse("   3  |  Lmao Ayy   | 030201-111C");
     *   oppilas.getId() === 3;
     *   oppilas.toString().startsWith("3|Lmao Ayy|030201-111C|") === true; // on enemmäkin kuin 3 kenttää, siksi loppu |
     *
     *   oppilas.rekisteroi();
     *   int n = oppilas.getId();
     *   oppilas.parse(""+(n+20));       // Otetaan merkkijonosta vain tunnusnumero
     *   oppilas.rekisteroi();           // ja tarkistetaan että seuraavalla kertaa tulee yhtä isompi
     *   oppilas.getId() === n+20+1;
     *     
     * </pre>
     */
    public void parse(String rivi) {
        StringBuffer sb = new StringBuffer(rivi);
        setId(Mjonot.erota(sb, '|', getId()));
        nimi = Mjonot.erota(sb, '|', nimi);
        hetu = Mjonot.erota(sb, '|', hetu);
        aloitusvuosi = Mjonot.erota(sb, '|', aloitusvuosi);
        katuosoite = Mjonot.erota(sb, '|', katuosoite);
        postinumero = Mjonot.erota(sb, '|', postinumero);
        postiosoite = Mjonot.erota(sb, '|', postiosoite);
        sahkoposti = Mjonot.erota(sb, '|', sahkoposti);
        puhelinnumero = Mjonot.erota(sb, '|', puhelinnumero);
        
    }

    /**
     * Asettaa opiskelijalle id:n (oid) ja samalla varmistaa että
     * seuraava numero on aina suurempi kuin tähän mennessä suurin.
     * @param uusiId asetettava oid
     */
    public void setId(int uusiId) {
		oid = uusiId;
		if (oid >= seuraavaNro) seuraavaNro = oid + 1;
		
	}

    /**
     * Pääohjelma testaamista varten.
     * @param args
     */
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
