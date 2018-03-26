package fxLukio;

/**
 * Lukion oppilaslista, joka osaa mm. lis‰t‰ uuden oppilaan.
 * @author majuhama laolkorh
 *@version 25.2.2016
 */

public class Oppilaat {
	private static final int MAX_OPPILAITA   = 5;
    private int              lkm             = 0;
    private String           tiedostonNimi   = "";
    private Oppilas          alkiot[]        = new Oppilas[MAX_OPPILAITA];

    /**
     * Oletusmuodostaja
     */
    public Oppilaat() {
        // Attribuuttien oma alustus riitt‰‰
    }
    
    /**
     * Lis‰‰ uuden oppilaan tietorakenteeseen.  Ottaa oppilaan omistukseensa.
     * @param Oppilas lis‰tt‰v‰n oppilaan viite.  Huom tietorakenne muuttuu omistajaksi
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
     * oppilaat.lisaa(aku1);  #THROWS SailoException
     * </pre>
     */
    public void lisaa(Oppilas oppilas) throws SailoException {
        if (lkm >= alkiot.length) throw new SailoException("Liikaa alkioita");
        alkiot[lkm] = oppilas;
        lkm++;
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
     * Lukee oppilaslistan tiedostosta.  Kesken.
     * @param hakemisto tiedoston hakemisto
     * @throws SailoException jos lukeminen ep‰onnistuu
     */
    public void lueTiedostosta(String hakemisto) throws SailoException {
        tiedostonNimi = hakemisto + "/oppilaat.dat";
        throw new SailoException("Ei osata viel‰ lukea tiedostoa " + tiedostonNimi);
    }


    /**
     * Tallentaa oppilaslistan tiedostoon.  Kesken.
     * @throws SailoException jos talletus ep‰onnistuu
     */
    public void talleta() throws SailoException {
        throw new SailoException("Ei osata viel‰ tallettaa tiedostoa " + tiedostonNimi);
    }


    /**
     * Palauttaa lukion oppilaiden lukum‰‰r‰n
     * @return oppilaiden lukum‰‰r‰
     */
    public int getLkm() {
        return lkm;
    }

    
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
    
}
