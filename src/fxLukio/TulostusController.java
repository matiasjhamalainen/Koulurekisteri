package fxLukio;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

/**
 * Luokka tulostamista varten
 * @author majuhama ja laolkorh
 * matias.j.hamalainen@student.jyu.fi, lassi.o.korhonen@student.jyu.fi
 * @version 15.3.2016
 */
public class TulostusController  implements ModalControllerInterface<String> {
	
	 
	    @FXML TextArea tulostusAlue;
	    
	    @FXML private void handleOK() {
	        ModalController.closeStage(tulostusAlue);
	    }

	    
	    @FXML private void handleTulosta() {
	        Dialogs.showMessageDialog("Ei osata viel� tulostaa");
	    }

	    
	    @Override
	    public String getResult() {
	        return null;
	    } 

	    
	    @Override
	    public void setDefault(String oletus) {
	        // if ( oletus == null ) return;
	        tulostusAlue.setText(oletus);
	    }

	    
	    /**
	     * Mit� tehd��n kun dialogi on n�ytetty
	     */
	    @Override
	    public void handleShown() {
	        //
	    }
	    
	    
	    /**
	     * @return alue johon tulostetaan
	     */
	    public TextArea getTextArea() {
	        return tulostusAlue;
	    }
	    
	    
	    /**
	     * N�ytt�� tulostusalueessa tekstin
	     * @param tulostus tulostettava teskti
	     * @return kontrolleri, jolta voidaan pyyt�� lis�� tietoa
	     */
	    public static TulostusController tulosta(String tulostus) {
	        TulostusController tulostusCtrl = 
	        (TulostusController) ModalController.showModeless(TulostusController.class.getResource("LukionTulostusView.fxml"),
	                "Tulostus", tulostus);
	        return tulostusCtrl;
	    }

}

