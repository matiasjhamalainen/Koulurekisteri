package fxLukio;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * @author majuhama ja laolkorh
 * @version 13.02.2016
 *
 */
public class LukioGUIController {//

	@FXML
	private MenuItem menuApua;
	
	@FXML 
	private TextField hakuehto;
	      
	@FXML private 
	ComboBox<String> cbKentat;
	
	@FXML
	private Label labelVirhe;

	@FXML
	private MenuItem menuAvaa;

	@FXML
	private Button buttonTallenna;

	@FXML
	private MenuItem menuTallenna;

	@FXML
	private MenuItem buttonTulosta;

	@FXML
	private MenuItem menuLisaakurssi;

	@FXML
	private MenuItem menuPoistaKurssi;

	@FXML
	private Button buttonUusiOppilas;

	@FXML
	private MenuItem menuPoistaOppilas;

	@FXML
	private MenuItem menuAbout;

	@FXML
	private MenuItem buttonSulje;

	@FXML
	private Button buttonLisaaKurssi;

	@FXML
	private Button buttonPoistaKurssi;

	
	/**
	 * N‰ytt‰‰ virheen henkilˆtietokenttien alapuolella.
	 * @param virhe
	 */
	private void naytaVirhe(String virhe) {
		if (virhe == null || virhe.isEmpty()) {
			labelVirhe.setText("");
			labelVirhe.getStyleClass().removeAll("virhe");
			return;
		}
		labelVirhe.setText(virhe);
		labelVirhe.getStyleClass().add("virhe");
	}

	/**
	 * Tarkastaa, onko annettu hakuehto virheellinen.
	 */
	@FXML
	private void handleHakuehto() {
		String hakukentta = cbKentat.getSelectionModel().getSelectedItem();
		String ehto = hakuehto.getText();
		if (ehto.isEmpty())
			naytaVirhe(null);
		else
			naytaVirhe("Ei osata viel‰ hakea " + hakukentta + ": " + ehto);
	}

	/**
	 * Avaa uuden tiedoston
	 */
	@FXML
	void avaa() {
		Dialogs.showMessageDialog("Ei osata viel‰ avata tiedostoja");
	}

	/**
	 * Lis‰‰ uuden kurssin
	 */
	@FXML
	void lisaaKurssi() {
		Dialogs.showMessageDialog("Ei osata viel‰ lis‰t‰ kurssia");
	}

	/**
	 * Poistaa kurssin
	 */
	@FXML
	void poistaKurssi() {
		Dialogs.showMessageDialog("Ei osata viel‰ poistaa kurssia");
	}

	/**
	 * Tallentaa tiedot
	 */
	@FXML
	void tallenna() {
		Dialogs.showMessageDialog("Ei osata viel‰ tallentaa");
	}

	@FXML
	/**
	 * Sulkee ohjelman ja tallentaa tiedot ensin.
	 */
	void sulje() {
		asulje();
	}

	private void asulje() {
		Dialogs.showQuestionDialog("Ohjelman lopetus...", "Haluatko sulkea ohjelman?", "Kyll‰", "Ei");
		Platform.exit();
	}

	/**
	 * Tulostaa valitut tiedot.
	 */
	@FXML
	void tulosta() {
		// TulostusController.tulosta(null);
		Dialogs.showMessageDialog("Ei osata viel‰ tulostaa");
	}

	/**
	 * Avaa apulinkin internetist‰.
	 */
	@FXML
	void apua(){
		Dialogs.showMessageDialog("Ei osata viel‰ auttaa");
	}
	
	/**
	 * Tuo n‰kyviin about-ikkunan, jossa tietoja ohjelmasta.
	 */
	@FXML
	void about() {
		ModalController.showModal(LukioGUIController.class.getResource("LukionAboutView.fxml"), "Lukio", null, "");
	}

	/**
	 * Poistaa oppilaan rekisterist‰.
	 */
	@FXML
	void poistaOppilas() {
		// Dialogs.showMessageDialog("Ei osata viel‰ lis‰t‰");
		// Dialogs.showMessageDialog("Tiedosto kelmit/nimet.dat ei aukea");
		Dialogs.showQuestionDialog("Poisto?", "Poistetaanko oppilas: Jurvanen Noora", "Kyll‰", "Ei");
	}

	/**
	 * Lis‰‰ rekisteriin uuden oppilaan.
	 */
	@FXML
	void uusiOppilas() {
		Dialogs.showMessageDialog("Ei osata viel‰ lis‰t‰ uutta oppilasta");
		// Dialogs.showMessageDialog("Tiedosto tiedot/opiskelijat.dat ei
		// aukea");
		// Dialogs.showQuestionDialog("Poisto?", "Poistetaanko oppilas: Jurvanen
		// Noora", "Kyll‰", "Ei");
	}

}
