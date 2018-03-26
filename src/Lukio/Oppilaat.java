package Lukio;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import fi.jyu.mit.ohj2.WildChars;

/**
 * Lukion oppilaslista, joka osaa mm. lis‰t‰ uuden oppilaan.
 * @author majuhama ja laolkorh
 * matias.j.hamalainen@student.jyu.fi, lassi.o.korhonen@student.jyu.fi
 *@version 25.2.2016
 */

public class Oppilaat implements Iterable<Oppilas> {
	private static final int MAX_OPPILAITA   = 5;
    private int              lkm             = 0;
    private Oppilas          alkiot[]        = new Oppilas[MAX_OPPILAITA];
    private String			 oletusTiedosto  = "opiskelijat";
    private String 			 lukionNimi 	 = "";

    /**
     * Oletusmuodostaja
     */
    public Oppilaat() {
        // Attribuuttien oma alustus riitt‰‰
    }
    
    /**
     * Lis‰‰ uuden oppilaan tietorakenteeseen.  Ottaa oppilaan omistukseensa.
     * @param oppilas lis‰tt‰v‰n oppilaan viite.  Huom tietorakenne muuttuu omistajaksi
     * @throws SailoException jos tietorakenne on jo t‰ynn‰
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * Oppilaat oppilaat = new Oppilaat();
     * Oppilas aku1 = new Oppilas(), aku2 = new Oppilas();
     * oppilaat.getLkm() === 0;
     * oppilaat.lisaa(aku1); oppilaat.getLkm() === 1;
     * oppilaat.lisaa(aku2); oppilaat.getLkm() === 2;
     * oppilaat.lisaa(aku1); oppilaat.getLkm() === 3;
     * oppilaat.anna(0) === aku1;
     * oppilaat.anna(1) === aku2;
     * oppilaat.anna(2) === aku1;
     * oppilaat.anna(1) == aku1 === false;
     * oppilaat.anna(1) == aku2 === true;
     * oppilaat.anna(3) === aku1; #THROWS IndexOutOfBoundsException 
     * oppilaat.lisaa(aku1); oppilaat.getLkm() === 4;
     * oppilaat.lisaa(aku1); oppilaat.getLkm() === 5;
     * </pre>
     */
    public void lisaa(Oppilas oppilas) throws SailoException {
        if (lkm >= alkiot.length) {
        	Oppilas vara[] = new Oppilas[lkm + 5];
        	for(int i = 0; i < alkiot.length; i++) {
        		vara[i] = alkiot[i];
        	}
        	alkiot = vara;
        }
        alkiot[lkm] = oppilas;
        lkm++;
    }
    
    /** 
	* Korvaa oppilaan tietorakenteessa.  Ottaa oppilaan omistukseensa. 
	* Etsit‰‰n samalla id:lla oleva oppilas. Jos ei lˆydy, niin lis‰t‰‰n uutena oppilaana. 
	* @param oppilas lis‰tt‰v‰n oppilaan viite. 
	* @throws SailoException jos tietorakenne on jo t‰ynn‰ 
	*/
	public void korvaaTaiLisaa(Oppilas oppilas) throws SailoException {
		int id = oppilas.getId();
		for (int i = 0; i < lkm; i++) {
			if (alkiot[i].getId() == id) {
				alkiot[i] = oppilas;

				return;
			}
		}
		lisaa(oppilas);
	}
  


    /**
     * Palauttaa viitteen i:teen j‰seneen.
     * @param i monennenko j‰senen viite halutaan
     * @return viite j‰seneen, jonka indeksi on i
     * @throws IndexOutOfBoundsException jos i ei ole sallitulla alueella  
     */
    public Oppilas anna(int i) throws IndexOutOfBoundsException {
        if (i < 0 || lkm <= i)
            throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        return alkiot[i];
    }


    /**
     * Lukee oppilaslistan tiedostosta. 
     * @param tied tiedoston oletusnimi
     * @throws SailoException jos lukeminen ep‰onnistuu
     */
    public void lueTiedostosta(String tied) throws SailoException {
        setOletusTiedosto(tied);
        String rivi;
        try ( BufferedReader fi = new BufferedReader(new FileReader(getTiedostonNimi())) ) {
            

            while ( (rivi = fi.readLine()) != null ) {
                rivi = rivi.trim();
                if ( "".equals(rivi) || rivi.charAt(0) == ';' ) continue;
                Oppilas oppilas = new Oppilas();
                oppilas.parse(rivi); 
                lisaa(oppilas);
            }
            
        } catch ( FileNotFoundException e ) {
            throw new SailoException("Tiedosto " + getTiedostonNimi() + " ei aukea");
        } catch ( IOException e ) {
            throw new SailoException("Ongelmia tiedoston kanssa: " + e.getMessage());
        }
    }
    
    /**
     * Luetaan aikaisemmin annetun nimisest‰ tiedostosta
     * @throws SailoException jos tulee poikkeus
     */
    public void lueTiedostosta() throws SailoException {
        lueTiedostosta(getOletusTiedosto());
    }


    /**
     * Tallentaa oppilaslistan tiedostoon.  
     * Tiedoston muoto:
     * <pre>
     * Lukio Lukio
     * 20
     * ; kommenttirivi
     * 2|Ankka Aku|121103-706Y|2007|Ankkakuja 6|12345|ANKKALINNA|ankka@ankka.fi|1122
     * 3|Ankka Tupu|121153-706Y|2007|Ankkakuja 6|12345|ANKKALINNA|tupu@ankka.fi|32545
     * </pre>
     * @throws SailoException jos talletus ep‰onnistuu
     */
    public void talleta() throws SailoException {
        

        File fbak = new File(getBackUpNimi());
        File ftied = new File(getTiedostonNimi());
        fbak.delete(); // if .. System.err.println("Ei voi tuhota");
        ftied.renameTo(fbak); // if .. System.err.println("Ei voi nimet‰");

        try ( PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath())) ) {
            fo.println(";" + getLukionNimi());
            fo.println("; Kenttien j‰rjestys on seuraava:");
            fo.println("; oid |sukunimi etunimi	   |sotu		|aloitusvuosi	|katuosoite		  	|postinumero	|postiosoite    |s‰hkˆposti            |puhelinnumero ");
            for (Oppilas oppilas : this) {
                fo.println(oppilas.toString());
            }
            //} catch ( IOException e ) { // ei heit‰ poikkeusta
            //  throw new SailoException("Tallettamisessa ongelmia: " + e.getMessage());
        } catch ( FileNotFoundException ex ) {
            throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");
        } catch ( IOException ex ) {
            throw new SailoException("Tiedoston " + ftied.getName() + " kirjoittamisessa ongelmia");
        }

       
    }
    
    /**
     * Palauttaa lukion nimen
     * @return lukion nimi merkkijononna
     */
    public String getLukionNimi() {
        return lukionNimi;
    }

    /**
     * Palauttaa lukion oppilaiden lukum‰‰r‰n
     * @return oppilaiden lukum‰‰r‰
     */
    public int getLkm() {
        return lkm;
    }
    
    /**
     * Palauttaa tiedoston nimen, jota k‰ytet‰‰n tallennukseen
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
     * Palauttaa tiedoston nimen, jota k‰ytet‰‰n tallennukseen
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
     * Luokka oppilaiden iteroimiseksi.
     */
    public class OppilaatIterator implements Iterator<Oppilas> {
        private int kohdalla = 0;


        /**
         * Onko olemassa viel‰ seuraavaa oppilasta
         * @see java.util.Iterator#hasNext()
         * @return true jos on viel‰ oppilaita
         */
        @Override
        public boolean hasNext() {
            return kohdalla < getLkm();
        }


        /**
         * Annetaan seuraava oppilas
         * @return seuraava oppilas
         * @throws NoSuchElementException jos seuraava alkiota ei en‰‰ ole
         * @see java.util.Iterator#next()
         */
        @Override
        public Oppilas next() throws NoSuchElementException {
            if ( !hasNext() ) throw new NoSuchElementException("Ei oo");
            return anna(kohdalla++);
        }


        /**
         * Tuhoamista ei ole toteutettu
         * @throws UnsupportedOperationException aina
         * @see java.util.Iterator#remove()
         */
        @Override
        public void remove() throws UnsupportedOperationException {
            throw new UnsupportedOperationException("Me ei poisteta");
        }
    }


    /**
     * Palautetaan iteraattori oppilaistaan.
     * @return j‰sen iteraattori
     */
    @Override
    public Iterator<Oppilas> iterator() {
        return new OppilaatIterator();
    }


    /** 
     * Palauttaa "taulukossa" hakuehtoon vastaavien j‰senten viitteet 
     * @param hakuehto hakuehto 
     * @param k etsitt‰v‰n kent‰n indeksi  
     * @return tietorakenteen lˆytyneist‰ oppilaista 
     */ 
    public Collection<Oppilas> etsi(String hakuehto, int k) { 
    	 String ehto = "*";
         if ( hakuehto != null && hakuehto.length() > 0 ) ehto = hakuehto;
         int hk = k;
         if ( k < 0 ) hk = 1;
         List<Oppilas> loytyneet = new ArrayList<Oppilas>(); 
         for (Oppilas oppilas : this) { 
             //        if ( oppilas.anna(k).toUpperCase().startsWith(hakuehto.toUpperCase()) ) loytyneet.add(jasen); 
             if (WildChars.onkoSamat(oppilas.anna(hk), ehto)) loytyneet.add(oppilas);  
         } 
         Collections.sort(loytyneet, new Oppilas.Vertailija(k));   
         return loytyneet; 
    } 

    

    /**
     * P‰‰ohjelma testaamista varten.
     * @param args args
     */
    public static void main(String args[]) {
        Oppilaat oppilaat = new Oppilaat();

        Oppilas aku = new Oppilas(), aku2 = new Oppilas();
        aku.rekisteroi();    aku.vastaaAkuAnkka();
        aku2.rekisteroi();   aku2.vastaaAkuAnkka();

        try {
            oppilaat.lisaa(aku);
            oppilaat.lisaa(aku2);
        
            System.out.println("========== Oppilaat testi ==============");
        
            for (int i=0; i<oppilaat.getLkm(); i++) {
                Oppilas oppilas = oppilaat.anna(i);
                System.out.println("Oppilas id: " + i);
                oppilas.tulosta(System.out);
            }

        } catch (SailoException ex) {
            System.out.println(ex.getMessage());
        }
    }

    
    /**
     * Poistaa oppilaan, jolla on tietty id
     * @param nro poistettavan oppilaan id
     * @return 1 jos poisto onnistui, 0 jos id:ta vastaavaa oppilasta ei lˆydy
     */
	public int poista(int nro) {
		 int id = etsiId(nro); 
	        if (id < 0) return 0; 
	        lkm--; 
	        for (int i = id; i < lkm; i++) 
	            alkiot[i] = alkiot[i + 1]; 
	        alkiot[lkm] = null; 
	        return 1; 
	}

	/**
	 * Etsii oppilaan id:n perusteella
	 * @param id oppilaan id
	 * @return lˆytyneen oppilaan indeksi tai -1 jos ei lˆydy
	 */
	private int etsiId(int id) {
		  for (int i = 0; i < lkm; i++) 
	            if (id == alkiot[i].getId()) return i; 
	        return -1; 
	}
    
}
