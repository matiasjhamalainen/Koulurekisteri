package Lukio;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * Luokka lukion kursseille, joka osaa mm. lisätä uuden kurssin tietylle oppilaalle
 * @author laolkorh	ja majuhama
 * matias.j.hamalainen@student.jyu.fi, lassi.o.korhonen@student.jyu.fi
 * @version 1.0, 10.03.2016
 */
public class Kurssit implements Iterable<Kurssi> {

	
    private String oletusTiedosto = "kurssit";
    

    /** Taulukko kursseille */
    private final Collection<Kurssi> alkiot = new ArrayList<Kurssi>();


    /**
     * Kurssien alustaminen
     */
    public Kurssit() {
        // toistaiseksi ei tarvitse tehdä mitään
    }


    /**
     * Lisää uuden kurssin tietorakenteeseen. Ottaa kurssin omistukseensa.
     * @param kur lisättävä kurssi. Huom. tietorakenne muuttuu omistajaksi
     */
    public void lisaa(Kurssi kur) {
        alkiot.add(kur);
    }


    /**
     * Lukee kurssit tiedostosta.
     * @param tied tiedoston nimen alkuosa
     * @throws SailoException jos lukeminen epäonnistuu
     */
    public void lueTiedostosta(String tied) throws SailoException {
        setOletusTiedosto(tied);
        try ( BufferedReader fi = new BufferedReader(new FileReader(getTiedostonNimi())) ) {

            String rivi;
            while ( (rivi = fi.readLine()) != null ) {
                rivi = rivi.trim();
                if ( "".equals(rivi) || rivi.charAt(0) == ';' ) continue;
                Kurssi kur = new Kurssi();
                kur.parse(rivi); // voisi olla virhekäsittely
                lisaa(kur);
            }
           

        } catch ( FileNotFoundException e ) {
            throw new SailoException("Tiedosto " + getTiedostonNimi() + " ei aukea");
        } catch ( IOException e ) {
            throw new SailoException("Ongelmia tiedoston kanssa: " + e.getMessage());
        }
    }
    
    /**
     * Luetaan aikaisemmin annetun nimisestä tiedostosta
     * @throws SailoException jos tulee poikkeus
     */
    public void lueTiedostosta() throws SailoException {
        lueTiedostosta(getOletusTiedosto());
    }
    
    /**
     * Palauttaa tiedoston nimen, jota käytetään tallennukseen
     * @return tallennustiedoston nimi
     */
    public String getOletusTiedosto() {
        return oletusTiedosto;
    }


    /**
     * Asettaa tiedoston oletusnimen ilman tarkenninta
     * @param nimi tallennustiedoston oletusnimi
     */
    public void setOletusTiedosto(String nimi) {
        oletusTiedosto = nimi;
    }
    
    /**
     * Palauttaa tiedoston nimen, jota käytetään tallennukseen
     * @return tallennustiedoston nimi
     */
    public String getTiedostonNimi() {
        return getOletusTiedosto() + ".dat";
    }
    
    /**
     * Palauttaa varakopiotiedoston nimen
     * @return varakopiotiedoston nimi
     */
    public String getBackUpNimi() {
        return oletusTiedosto + ".bak";
    }


    /**
     * Tallentaa kurssit tiedostoon.  
     * @throws SailoException jos talletus epäonnistuu
     */
    public void talleta() throws SailoException {
    	File fbak = new File(getBackUpNimi());
        File ftied = new File(getTiedostonNimi());
        fbak.delete(); //  if ... System.err.println("Ei voi tuhota");
        ftied.renameTo(fbak); //  if ... System.err.println("Ei voi nimetä");

        try ( PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath())) ) {
            for (Kurssi kur : this) {
                fo.println(kur.toString());
            }
        } catch ( FileNotFoundException ex ) {
            throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");
        } catch ( IOException ex ) {
            throw new SailoException("Tiedoston " + ftied.getName() + " kirjoittamisessa ongelmia");
        }

        
    }
    


    /**
     * Palauttaa lukion kurssien lukumäärän
     * @return kurssien lukumäärä
     */
    public int getLkm() {
        return alkiot.size();
    }


    /**
     * Iteraattori kaikkien kurssien läpikäymiseen
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
     * @return tietorakenne jossa viiteet löydetteyihin kursseihin
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
     * Poistaa valitun kurssin 
     * @param kurssi poistettava kurssi 
     * @return tosi jos löytyi poistettava kurssi  
     */ 
    public boolean poista(Kurssi kurssi) { 
        boolean ret = alkiot.remove(kurssi); 
         
        return ret; 
    } 


    /** 
     * Poistaa kaikki tietyn oppilaan kurssit
     * @param id viite siihen, minkä oppilaan kurssit poistetaan 
     * @return montako poistettiin  
     * @example 
     */ 
    public int poista(int id) { 
        int n = 0; 
        for (Iterator<Kurssi> iteraattori = alkiot.iterator(); iteraattori.hasNext();) { 
            Kurssi kur = iteraattori.next(); 
            if (kur.getOid() == id) { 
                iteraattori.remove(); 
                n++; 
            } 
        }
        return n; 
    } 

    /**
     * Testiohjelma kursseille
     * @param args ei käytössä
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
