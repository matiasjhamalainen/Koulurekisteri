package Lukio;

import fi.jyu.mit.ohj2.Mjonot;
import java.io.*;
import java.util.Comparator;

import static kanta.HetuTarkistus.*;

/**
 * Lukion oppilas, joka osaa huolehtia mm. id:staan, osoitteestaan.
 * @author majuhama ja laolkorh
 * matias.j.hamalainen@student.jyu.fi, lassi.o.korhonen@student.jyu.fi
 * @version 25.2.2016
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
     * Luokka joka vertaa kahta oppilasta kesken��n 
     */
    public static class Vertailija implements Comparator<Oppilas> {

        private final int kenttanro;


        /**
         * Alustetaan vertailija vertailemaan tietyn kent�n perusteella
         * @param k vertailtavan kent�n indeksi.
         */
        public Vertailija(int k) {
            this.kenttanro = k;
        }


        /**
         * Verrataan kahta oppilasta kesken��n.
         * @param o1 1. verrattava j�sen
         * @param o2 2. verrattava j�sen
         * @return <0 jos o1 < o2, == 0 jos o1 == o2 ja muuten >0
         */
        @Override
        public int compare(Oppilas o1, Oppilas o2) {
            String s1 = o1.getAvain(kenttanro);
            String s2 = o2.getAvain(kenttanro);

            return s1.compareTo(s2);

        }

    }
	
	/**
     * Palauttaa oppilaan kenttien lukum��r�n
     * @return kenttien lukum��r�
     */
    public int getKenttia() {
        return 9;
    }


    /**
     * Antaa k-kent�n sis�ll�n
     * @param k kent�n indeksi
     * @return kent�n sis�lt� merkkijonona
     */
    public String getAvain(int k) {
    	switch ( k ) {
    case 0:
        return "" + oid;
    case 1:
        return "" + nimi;
    case 2:
        return "" + hetu;
    case 3:
    	return "" + aloitusvuosi;
    case 4:
        return "" + katuosoite;
    case 5:
        return "" + postinumero;
    case 6:
        return "" + postiosoite;
    case 7:
        return "" + sahkoposti;
    case 8:
        return "" + puhelinnumero;
    
    default:
        return "Ayy lmao";
    }
    }



	/**
     * Eka kentt� joka on mielek�s kysytt�v�ksi
     * @return ekan kent�n indeksi
     */
    public int ekaKentta() {
        return 1;
    }
    
    /**
     * Asettaa k:n kent�n arvoksi parametrina tuodun merkkijonon arvon
     * @param k kuinka monennen kent�n arvo asetetaan
     * @param jono jono, joka asetetaan kent�n arvoksi
     * @return null jos asettaminen onnistuu, muuten vastaava virheilmoitus.
     */
    public String aseta(int k, String jono) {
        String tjono = jono.trim();
        StringBuffer sb = new StringBuffer(tjono);
        switch ( k ) {
        case 0:
            setId(Mjonot.erota(sb, '�', getId()));
            return null;
        case 1:
            nimi = tjono;
            return null;
        case 2:
            kanta.HetuTarkistus hetut = new kanta.HetuTarkistus();
            String virhe = hetut.tarkista(tjono);
            if ( virhe != null ) return virhe;
            hetu = tjono;
            return null;
        case 3:
        	try{
        	aloitusvuosi = Mjonot.erotaEx(sb, '�', aloitusvuosi);
        } catch ( NumberFormatException ex ) {
            return "Aloitusvuosi v��rin " + ex.getMessage();
        }
        	return null;
        case 4:
            katuosoite = tjono;
            return null;
        case 5:
            postinumero = tjono;
            return null;
        case 6:
            postiosoite = tjono;
            return null;
        case 7:
            sahkoposti = tjono;
            return null;
        case 8:
            puhelinnumero = tjono;
            return null;
        
        default:
            return "Ayy lmao";
        }
    }
    
    /**
     * Antaa k:n kent�n sis�ll�n merkkijonona
     * @param k monenenko kent�n sis�lt� palautetaan
     * @return kent�n sis�lt� merkkijonona
     */
    public String anna(int k) {
        switch ( k ) {
        case 0:
            return "" + oid;
        case 1:
            return "" + nimi;
        case 2:
            return "" + hetu;
        case 3:
        	return "" + aloitusvuosi;
        case 4:
            return "" + katuosoite;
        case 5:
            return "" + postinumero;
        case 6:
            return "" + postiosoite;
        case 7:
            return "" + sahkoposti;
        case 8:
            return "" + puhelinnumero;
        
        default:
            return "Ayy lmao";
        }
    }
    
    /**
     * Palauttaa k:tta oppilaan kentt�� vastaavan kysymyksen
     * @param k kuinka monennen kent�n kysymys palautetaan (0-alkuinen)
     * @return k:netta kentt�� vastaava kysymys
     */
    public String getKysymys(int k) {
        switch ( k ) {
        case 0:
            return "Id";
        case 1:
            return "nimi";
        case 2:
            return "hetu";
        case 3:
        	return "aloitusvuosi";
        case 4:
            return "katuosoite";
        case 5:
            return "postinumero";
        case 6:
            return "postiosoite";
        case 7:
            return "s�hk�posti";
        case 8:
            return "puhelinnumero";
        
        default:
            return "Ayy lmao";
        }
    }

	
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
     * Apumetodi, jolla saadaan t�ytetty� testiarvot oppilaalle.
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
     * Apumetodi, jolla saadaan t�ytetty� testiarvot oppilaalle.
     * Henkil�tunnus arvotaan, jotta kahdella oppilaalla ei olisi
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
     *   oppilas.toString().startsWith("3|Lmao Ayy|030201-111C|") === true; // on enemm�kin kuin 3 kentt��, siksi loppu |
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
     * Selvitt�� oppilaan tiedot "|" erotellusta merkkijonosta
     * Pit�� huolen ett� seuraavaNro on suurempi kuin tuleva oid.
     * @param rivi josta oppilaan tiedot otetaan
     * 
     * @example
     * <pre name="test">
     *   Oppilas oppilas = new Oppilas();
     *   oppilas.parse("   3  |  Lmao Ayy   | 030201-111C");
     *   oppilas.getId() === 3;
     *   oppilas.toString().startsWith("3|Lmao Ayy|030201-111C|") === true; // on enemm�kin kuin 3 kentt��, siksi loppu |
     *
     *   oppilas.rekisteroi();
     *   int n = oppilas.getId();
     *   oppilas.parse(""+(n+20));       // Otetaan merkkijonosta vain tunnusnumero
     *   oppilas.rekisteroi();           // ja tarkistetaan ett� seuraavalla kertaa tulee yht� isompi
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
     * Asettaa opiskelijalle id:n (oid) ja samalla varmistaa ett�
     * seuraava numero on aina suurempi kuin t�h�n menness� suurin.
     * @param uusiId asetettava oid
     */
    public void setId(int uusiId) {
		oid = uusiId;
		if (oid >= seuraavaNro) seuraavaNro = oid + 1;
		
	}
    
    /**
     * Tehd��n identtinen klooni oppilaasta
     * @return Object kloonattu oppilas
     * @example
     * <pre name="test">
     * #THROWS CloneNotSupportedException 
     *   Oppilas oppilas = new Oppilas();
     *   oppilas.parse("   3  |  Ankka Aku   | 123");
     *   Oppilas kopio = oppilas.clone();
     *   kopio.toString() === oppilas.toString();
     *   oppilas.parse("   4  |  Ankka Tupu   | 123");
     *   kopio.toString().equals(oppilas.toString()) === false;
     * </pre>
     */
    @Override
    public Oppilas clone() throws CloneNotSupportedException {
        Oppilas uusi = new Oppilas();
        uusi.setId(this.getId());
        uusi.nimi = this.anna(1);
        uusi.hetu = this.anna(2);
        uusi.katuosoite = this.anna(4);
        uusi.postinumero = this.anna(5);
        uusi.postiosoite = this.anna(6);
        uusi.puhelinnumero = this.anna(8);
        uusi.aloitusvuosi = this.getAloitusvuosi();
        uusi.sahkoposti = this.anna(7);
        return uusi;
    }
    
    /**
     * Palauttaa oppilaan aloitusvuoden
     * @return aloitusvuosi
     */
    private int getAloitusvuosi() {
		return aloitusvuosi;
	}


	

	/**
     * Tutkii onko oppilaan tiedot samat kuin parametrina tuodun oppilaan tiedot
     * @param oppilas oppilas johon verrataan
     * @return true jos kaikki tiedot samat, false muuten
     * @example
     * <pre name="test">
     *   Oppilas jasen1 = new Oppilas();
     *   jasen1.parse("   3  |  Ankka Aku   | 030201-111C");
     *   Oppilas jasen2 = new Oppilas();
     *   jasen2.parse("   3  |  Ankka Aku   | 030201-111C");
     *   Oppilas jasen3 = new Oppilas();
     *   jasen3.parse("   3  |  Ankka Aku   | 030201-115H");
     *   
     *   jasen1.equals(jasen2) === true;
     *   jasen2.equals(jasen1) === true;
     *   jasen1.equals(jasen3) === false;
     *   jasen3.equals(jasen2) === false;
     * </pre>
     */
    public boolean equals(Oppilas oppilas) {
        for (int k = 0; k < getKenttia(); k++)
            if ( !anna(k).equals(oppilas.anna(k)) ) return false;
        return true;
    }


    @Override
    public boolean equals(Object oppilas) {
        if ( oppilas instanceof Oppilas ) return equals((Oppilas)oppilas);
        return false;
    }

    /**
     * P��ohjelma testaamista varten.
     * @param args args
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
