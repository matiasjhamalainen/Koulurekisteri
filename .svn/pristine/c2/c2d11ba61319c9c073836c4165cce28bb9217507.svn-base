package fxLukio;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import fi.jyu.mit.fxgui.Dialogs;

/**
 * @author majuhama
 * @version 19.1.2016
 *
 */
public class LukioGUIController {//
	
    @FXML
    private Button buttonUusiOppilas;
    
    @FXML
    private MenuItem buttonPoistaOppilas;

    @FXML
    void poistaOppilas() {
    	// Dialogs.showMessageDialog("Ei osata vielä lisätä");
    	// Dialogs.showMessageDialog("Tiedosto kelmit/nimet.dat ei aukea");
    	Dialogs.showQuestionDialog("Poisto?", "Poistetaanko oppilas: Jurvanen Noora", "Kyllä", "Ei");
    }

    @FXML
    void uusiOppilas() {
    	// Dialogs.showMessageDialog("Ei osata vielä lisätä");
    	Dialogs.showMessageDialog("Tiedosto tiedot/opiskelijat.dat ei aukea");
    	//Dialogs.showQuestionDialog("Poisto?", "Poistetaanko oppilas: Jurvanen Noora", "Kyllä", "Ei");
    }

}
