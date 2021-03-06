package Lukio;

import java.io.File;
import java.util.Collection;
import java.util.List;
import Lukio.SailoException;

/**
 * Lukio-luokka, joka huolehtii oppilaista.  P��osin kaikki metodit
 * ovat vain "v�litt�j�metodeja" oppilaisiin.
 * @author laolkorh ja majuhama
 * matias.j.hamalainen@student.jyu.fi, lassi.o.korhonen@student.jyu.fi
 * @version 1.0
 * 10.03.2016
 */
public class Lukio {
    private Oppilaat oppilaat = new Oppilaat();
    private Kurssit kurssit = new Kurssit();


    /**
     * Palautaa lukion oppilaat
     * @return oppilaiden m��r�
     */
    public int getOppilaat() {
        return oppilaat.getLkm();
    }
    
    /**
     * Lis�t��n uusi kurssi lukioon
     * @param kur lis�tt�v� kurssi 
     */
    public void lisaa(Kurssi kur) {
        kurssit.lisaa(kur);
    }

    /**
     * Poistaa oppilaista ja kursseista ne joilla on nro.
     * @param nro viitenumero, jonka mukaan poistetaan
     * @return montako oppilasta poistettiin
     */
    public int poista(int nro) {
        int poistettava = oppilaat.poista(nro);
        kurssit.poista(nro);
        return poistettava;
    }

    /** 
     * Poistaa kurssin
     * @param kurssi poistettava harrastus 
     */ 
    public void poistaKurssi(Kurssi kurssi) { 
        kurssit.poista(kurssi); 
    } 

    
    
    /**
     * Lis�� lukioon uuden oppilaan
     * @param oppilas lis�tt�v� oppilas
     * @throws SailoException jos lis�yst� ei voida tehd�
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
     * </pre>
     */
    public void lisaa(Oppilas oppilas) throws SailoException {
        oppilaat.lisaa(oppilas);
    }
    
    
    
    /** 
 	* Korvaa oppilaan tietorakenteessa.  Ottaa oppilaan omistukseensa. 
 	* Etsit��n samalla id:lla oleva oppilas. Jos ei l�ydy, niin lis�t��n uutena oppilaana. 
 	* @param oppilas lis�tt�v�n oppilaan viite. 
 	* @throws SailoException jos tietorakenne on jo t�ynn� 
 	*/
 	public void korvaaTaiLisaa(Oppilas oppilas) throws SailoException {
 		oppilaat.korvaaTaiLisaa(oppilas);
 	}
   
    /**
     * Palauttaa i:n oppilaan
     * @param i monesko oppilas palautetaan
     * @return viite i:teen oppilaaseen
     * @throws IndexOutOfBoundsException jos i v��rin
     */
    public Oppilas annaOppilas(int i) throws IndexOutOfBoundsException {
        return oppilaat.anna(i);
    }
    
    /**
     * Haetaan kaikki oppilaan kurssit
     * @param oppilas oppilas, jonka kursseja haetaan
     * @return tietorakenne, jossa viiteet l�ydetteyihin kursseihin
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
    * Asettaa tiedostojen oletusnimet
    * @param nimi uusi nimi
    */
    public void setTiedosto(String nimi) {
        File dir = new File(nimi);
        dir.mkdirs();
        String hakemistonNimi = "";
        if ( !nimi.isEmpty() ) hakemistonNimi = nimi +"/";
        oppilaat.setOletusTiedosto(hakemistonNimi + "opiskelijat");
        kurssit.setOletusTiedosto(hakemistonNimi + "kurssit");
    }
    
    /**
     * Lukee lukion tiedot tiedostosta
     * @param nimi jota k�yte��n lukemisessa
     * @throws SailoException jos lukeminen ep�onnistuu
     * 
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #import java.io.*;
     * #import java.util.*;
     * 
     *  Lukio lukio = new Lukio();
     *  
     *  Oppilas aku1 = new Oppilas(); aku1.vastaaAkuAnkka(); aku1.rekisteroi();
     *  Oppilas aku2 = new Oppilas(); aku2.vastaaAkuAnkka(); aku2.rekisteroi();
     *   
     *  String hakemisto = "testiopiskelijat";
     *  File dir = new File(hakemisto);
     *  File ftied  = new File(hakemisto+"/opiskelijat.dat");
     *  dir.mkdir();  
     *  ftied.delete();
     *  lukio.lueTiedostosta(hakemisto); #THROWS SailoException
     *  lukio.lisaa(aku1);
     *  lukio.lisaa(aku2);
     *  lukio.tallenna();
     *  lukio = new Lukio();
     *  lukio.lueTiedostosta(hakemisto);
     *  lukio.tallenna();
     *  ftied.delete()  === true;
     *  File fbak = new File(hakemisto+"/opiskelijat.bak");
     * </pre>
     */
    public void lueTiedostosta(String nimi) throws SailoException {
        oppilaat = new Oppilaat(); // jos luetaan olemassa olevaan niin helpoin tyhjent�� n�in
        kurssit = new Kurssit();

        setTiedosto(nimi);
        oppilaat.lueTiedostosta();
        kurssit.lueTiedostosta();
    }


    /**
     * Tallentaa lukion tiedot tiedostoon.  
     * @throws SailoException jos tallettamisessa ongelmia
     */
    public void tallenna() throws SailoException {
        String virhe = "";
        try {
            oppilaat.talleta();
        } catch ( SailoException ex ) {
            virhe = ex.getMessage();
        }
        
        try {
            kurssit.talleta();
        } catch ( SailoException ex ) {
            virhe = ex.getMessage();
        }
        if ( !"".equals(virhe) ) throw new SailoException(virhe);
    }
    
    /** 
     * Palauttaa "taulukossa" hakuehtoon vastaavien oppilaiden viitteet 
     * @param hakuehto hakuehto  
     * @param k etsitt�v�n kent�n indeksi  
     * @return tietorakenteen l�ytyneist� oppilaista
     * @throws SailoException Jos jotakin menee v��rin
     */ 
    public Collection<Oppilas> etsi(String hakuehto, int k) throws SailoException { 
        return oppilaat.etsi(hakuehto, k); 
    } 



    /**
     * Testiohjelma lukiosta
     * @param args ei k�yt�ss�
     */
    public static void main(String args[]) {
        Lukio lukio = new Lukio();

        try {
           

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
