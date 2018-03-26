package Lukio;

/**
 * Poikkeusluokka tietorakenteesta aiheutuville poikkeuksille.
 * @author laolkorh	ja mujuhama
 * @version 1.0, 22.04.2016
 */
public class SailoException extends Exception {
    private static final long serialVersionUID = 1L;


    /**
     * Poikkeuksen muodostaja jolle tuodaan poikkeuksessa
     * käytettävä viesti
     * @param viesti Poikkeuksen viesti
     */
    public SailoException(String viesti) {
        super(viesti);
    }
}
