package Lukio;

import java.io.*;

import fi.jyu.mit.ohj2.Mjonot;

import static kanta.HetuTarkistus.rand;

/**
 * Kurssi joka osaa mm. itse huolehtia tunnus_nro:staan.
 * @author laolkorh ja majuhama
 * matias.j.hamalainen@student.jyu.fi, lassi.o.korhonen@student.jyu.fi
 * @version 1.0, 10.03.2016
 */
public class Kurssi {
    private int kid;
    private int oid;
    private String oppiaine;
    private int kurssinro;


    private static int seuraavaNro = 1;

    
    /**
     * Palauttaa kurssin kenttien lukum��r�n
     * @return kenttien lukum��r�
     */
    public int getKenttia() {
        return 4;
    }


    /**
     * Eka kentt� joka on mielek�s kysytt�v�ksi
     * @return ekan kent�n indeksi
     */
    public int ekaKentta() {
        return 2;
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
            oid = (Mjonot.erota(sb, '�', getOid()));
            return null;
        case 1:
       	 setKid(Mjonot.erota(sb, '$', getKid()));
            return null;
        case 2:
            oppiaine = tjono;
            return null;
        case 3:
        	try {
            kurssinro = Mjonot.erotaEx(sb, '�', kurssinro);
        } catch ( NumberFormatException ex ) {
            return "Kurssinumero v��rin " + ex.getMessage();
        }
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
        	return "" + kid;
        case 2:
            return "" + oppiaine;
        case 3:
            return "" + kurssinro;
        
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
            return "oppilaan id";
        case 1:
            return "kurssin id";
        case 2:
            return "oppiaine";
        case 3:
        	return "kurssinumero";
              
        default:
            return "Ayy lmao";
        }
    }
    
	/**
	* Tehd��n identtinen klooni kurssista
	* @return Object kloonattu kurssi
	*/
	@Override
	public Kurssi clone() throws CloneNotSupportedException {
		return (Kurssi) super.clone();
	}
        

    /**
     * Alustetaan kurssi.  Toistaiseksi ei tarvitse tehd� mit��n
     */
    public Kurssi() {
       
        
    }


    /**
     * Alustetaan tietyn oppilaan kurssi.  
     * @param oid oppilaan viitenumero 
     */
    public Kurssi(int oid) {
        this.oid = oid;
    }


    /**
     * Apumetodi, jolla saadaan t�ytetty� testiarvot Kurssille.
     * Kurssin numero arvotaan, jotta kahdella kurssilla ei v�ltt�m�tt� olisi
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
     * Asettaa kid:n ja samalla varmistaa ett�
     * seuraava numero on aina suurempi kuin t�h�n menness� suurin.
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
     * Selvitt�� kurssin tiedot |-merkill� erotellusta merkkijonosta.
     * Pit�� huolen ett� seuraavaNro on suurempi kuin tuleva kid.
     * @param rivi josta kurssin tiedot otetaan
     * @example
     * <pre name="test">
     *   Kurssi kurssi = new Kurssi();
     *   kurssi.parse("   2   | 1 | matematiikka | 3   ");
     *   kurssi.getOid() === 2;
     *   kurssi.toString()    === "2|1|matematiikka|3";
     * </pre>
     */
    public void parse(String rivi) {
        StringBuffer sb = new StringBuffer(rivi);
        oid = Mjonot.erota(sb, '|', oid);
        setKid(Mjonot.erota(sb, '|', getKid()));
        oppiaine = Mjonot.erota(sb, '|', oppiaine);
        kurssinro = Mjonot.erota(sb, '|', kurssinro);
        
    }

    
    /**
     * Palauttaa kurssin tiedot merkkijonona, jonka voi tallentaa tiedostoon.
     * @return kurssi |-merkill� eriteltyn� merkkijonona 
     */
    @Override
    public String toString() {
        return "" + getOid() + "|" + kid + "|" + oppiaine + "|" + kurssinro;
    }
    
    @Override
    public boolean equals(Object obj) {
           if ( obj == null ) return false;
          return this.toString().equals(obj.toString());
    }
       
    
    @Override
    public int hashCode() {
          return kid;
    }

    /**
     * Testiohjelma Harrastukselle.
     * @param args ei k�yt�ss�
     */
    public static void main(String[] args) {
        Kurssi kur = new Kurssi();
        kur.vastaaMatematiikka(2);
        kur.tulosta(System.out);
    }

}
