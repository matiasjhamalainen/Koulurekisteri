package fxLukio;


import java.io.PrintStream;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.TextAreaOutputStream;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import fxLukio.Kurssi;
import fxLukio.Oppilas;
import fxLukio.Lukio;
import fxLukio.SailoException;
import fxLukio.TulostusController;

/**
 * @author majuhama ja laolkorh
 * @version 15.03.2016
 *
 */
public class LukioGUIController implements Initializable {

	@FXML private MenuItem menuApua;
	@FXML private TextField hakuehto;
	@FXML private ComboBox<String> cbKentat;
	@FXML private Label labelVirhe;
	@FXML private MenuItem menuAvaa;
	@FXML private Button buttonTallenna;
	@FXML private MenuItem menuTallenna;
    @FXML private MenuItem buttonTulosta;
	@FXML private MenuItem menuLisaakurssi;
	@FXML private MenuItem menuPoistaKurssi;
	@FXML private Button buttonUusiOppilas;
	@FXML private MenuItem menuPoistaOppilas;
	@FXML private MenuItem menuAbout;
 	@FXML private MenuItem buttonSulje;
    @FXML private Button buttonLisaaKurssi;
	@FXML private Button buttonPoistaKurssi;
	@FXML private ScrollPane panelOppilas;
	@FXML private ListChooser chooserOppilaat;
	
	private String lukionnimi = "Per�hiki�n lukio";
	
	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		alusta();
		
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
			naytaVirhe("Ei osata viel� hakea " + hakukentta + ": " + ehto);
	}
	
	 @FXML private void handleTallenna() {
	        tallenna();
	    }
	    
	    
	    @FXML private void handleAvaa() {
	        avaa();
	    }
	    
	    
	    @FXML private void handleTulosta() {
	    	 TulostusController tulostusCtrl = TulostusController.tulosta(null);
	         tulostaValitut(tulostusCtrl.getTextArea());

	    	//tulosta();
	    } 

	    
	    @FXML private void handleLopeta() {
	        sulje();
	    } 

	    
	    @FXML private void handleUusiOppilas() {
	        uusiOppilas();
	    }
	    
	    
	    @FXML private void handlePoistaOppilas() {
	        poistaOppilas();
	    }
	    
	     
	    @FXML private void handleLisaaKurssi() {
	        lisaaKurssi();
	    }
	    

	    @FXML private void handlePoistaKurssi() {
	        poistaKurssi();
	    }
	    

	    @FXML private void handleApua() {
	        apua();
	    }
	    

	    @FXML private void handleTietoja() {
	        about();
	    }
	    

//===========================================================================================    
// T�st� eteenp�in ei k�ytt�liittym��n suoraan liittyv�� koodia    

	private Lukio lukio = new Lukio();
	private Oppilas oppilasKohdalla;
	private TextArea areaOppilas = new TextArea();
	private ListView<Oppilas> listOppilaat = new ListView<Oppilas>();
	private ObservableList<Oppilas> listdataOppilaat = FXCollections.observableArrayList();

	/**
     * Luokka, jolla hoidellaan miten oppilas n�ytet��n listassa 
     */
    public static class CellOppilas extends ListCell<Oppilas> {
        @Override protected void updateItem(Oppilas item, boolean empty) {
            super.updateItem(item, empty); // ilman t�t� ei n�y valinta
            setText(empty ? "" : item.getNimi());
        }
    }
	
	private void alusta() {
		panelOppilas.setContent(areaOppilas);
        areaOppilas.setFont(new Font("Times New Roman", 12));
        panelOppilas.setFitToHeight(true);
        BorderPane parent = (BorderPane)chooserOppilaat.getParent();
        parent.setCenter(listOppilaat);
        listOppilaat.setCellFactory( p -> new CellOppilas() );
        listOppilaat.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            naytaOppilas();
        });

	}
	
	 /**
     * N�ytt�� listasta valitun j�senen tiedot, tilap�isesti yhteen isoon edit-kentt��n
     */
    protected void naytaOppilas() {
        oppilasKohdalla = listOppilaat.getSelectionModel().getSelectedItem();

        if (oppilasKohdalla == null) return;

        areaOppilas.setText("");
        try (PrintStream os = TextAreaOutputStream.getTextPrintStream(areaOppilas)) {
            tulosta(os,oppilasKohdalla);
        }
    }
    
    /**
     * Tulostaa oppilaan tiedot
     * @param os tietovirta johon tulostetaan
     * @param oppilas tulostettava oppilas
     */
    public void tulosta(PrintStream os, final Oppilas oppilas) {
    	os.println("Oppilaan tiedot");
        os.println("----------------------------------------------");
        oppilas.tulosta(os);
        os.println("----------------------------------------------");
        os.println("Oppilaan kurssit");
        os.println("----------------------------------------------");
        List<Kurssi> kurssit = lukio.annaKurssit(oppilas);  
        for (Kurssi kur:kurssit)  
            kur.tulosta(os);     
    }
    
    
    /**
     * Tulostaa listassa olevat oppilaat tekstialueeseen
     * @param text alue johon tulostetaan
     */
    public void tulostaValitut(TextArea text) {
        try (PrintStream os = TextAreaOutputStream.getTextPrintStream(text)) {
            os.println("Tulostetaan kaikki oppilaat");
            os.println("----------------------------------------------");
            for (int i = 0; i < lukio.getOppilaat(); i++) {
                Oppilas oppilas = lukio.annaOppilas(i);
                tulosta(os, oppilas);
                os.println("\n\n");
            }
        }
    }
    
    /**
     * @param lukio Lukio, jota k�ytet��n t�ss� k�ytt�liittym�ss�
     */
    public void setLukio(Lukio lukio) {
        this.lukio = lukio;
        naytaOppilas();
    }
    
	
	/**
	 * N�ytt�� virheen henkil�tietokenttien alapuolella.
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
	
	private void setTitle(String title) {
        ModalController.getStage(hakuehto).setTitle(title);
    }

	
	/**
     * Hakee oppilaiden tiedot listaan
     * @param oid oppilaan id, joka aktivoidaan haun j�lkeen
     */
    protected void hae(int oid) {
        listdataOppilaat.clear();
        listOppilaat.setItems(listdataOppilaat);

        int index = 0;
        for (int i = 0; i < lukio.getOppilaat(); i++) {
            Oppilas oppilas = lukio.annaOppilas(i);
            if (oppilas.getId() == oid) index = i;
            listdataOppilaat.add(oppilas);
        }
        listOppilaat.getSelectionModel().select(index); // t�st� tulee muutosviesti joka n�ytt�� j�senen
    }

	

	/**
	 * Lis�� uuden kurssin
	 */
	void lisaaKurssi() {
		if ( oppilasKohdalla == null ) return; 
        Kurssi kur = new Kurssi(); 
        kur.rekisteroi(); 
        kur.vastaaMatematiikka(oppilasKohdalla.getId()); 
        lukio.lisaa(kur); 
        hae(oppilasKohdalla.getId()); 
		//Dialogs.showMessageDialog("Ei osata viel� lis�t� kurssia");
	}

	/**
	 * Poistaa kurssin
	 */
	void poistaKurssi() {
		Dialogs.showMessageDialog("Ei osata viel� poistaa kurssia");
	}

	/**
	 * Tallentaa tiedot
	 */
	void tallenna() {
		Dialogs.showMessageDialog("Ei osata viel� tallentaa");
	}

	
    /**
     * Sulkee ohjelman promptilla.
     */
	private void sulje() {
		Dialogs.showQuestionDialog("Ohjelman lopetus...", "Haluatko sulkea ohjelman?", "Kyll�", "Ei");
		Platform.exit();
	}

	/**
	 * Tulostaa valitut tiedot.
	 */
	void tulosta() {
		//TulostusController tulostusCtrl = TulostusController.tulosta(null);
        //tulostaValitut(tulostusCtrl.getTextArea());
		Dialogs.showMessageDialog("Ei osata viel� tulostaa");
	}

	/**
	 * Avaa apulinkin internetist�. 
	 */
	void apua(){
		Dialogs.showMessageDialog("Ei osata viel� auttaa");
	}
	
	/**
	 * Tuo n�kyviin about-ikkunan, jossa tietoja ohjelmasta.
	 */
	void about() {
		ModalController.showModal(LukioGUIController.class.getResource("LukionAboutView.fxml"), "Lukio", null, "");
	}

	/**
	 * Poistaa oppilaan rekisterist�.
	 */
	void poistaOppilas() {
		// Dialogs.showMessageDialog("Ei osata viel� lis�t�");
		// Dialogs.showMessageDialog("Tiedosto kelmit/nimet.dat ei aukea");
		Dialogs.showQuestionDialog("Poisto?", "Poistetaanko oppilas: Jurvanen Noora", "Kyll�", "Ei");
	}

	/**
	 * Lis�� rekisteriin uuden oppilaan.
	 */
	void uusiOppilas() {
		Oppilas uusi = new Oppilas();
        uusi.rekisteroi();
        uusi.vastaaAkuAnkka();
        try {
            lukio.lisaa(uusi);
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Ongelmia uuden luomisessa " + e.getMessage());
            return;
        }
        hae(uusi.getId());
        
		//Dialogs.showMessageDialog("Ei osata viel� lis�t� uutta oppilasta");
		// Dialogs.showMessageDialog("Tiedosto tiedot/opiskelijat.dat ei
		// aukea");
		// Dialogs.showQuestionDialog("Poisto?", "Poistetaanko oppilas: Jurvanen
		// Noora", "Kyll�", "Ei");
	}
	
	/**
     * Alustaa kerhon lukemalla sen valitun nimisest� tiedostosta
     * @param nimi tiedosto josta kerhon tiedot luetaan
     */
    protected void lueTiedosto(String nimi) {
        lukionnimi = nimi;
        setTitle("" + lukionnimi);
        String virhe = "Ei osata lukea viel�";  // TODO: t�h�n oikea tiedoston lukeminen
        // if (virhe != null) 
            Dialogs.showMessageDialog(virhe);
    }
    
    /**
     * Kysyt��n tiedoston nimi ja luetaan se
     * @return true jos onnistui, false jos ei
     */
    public void avaa() {
        /*String uusinimi = lukionNimiController.kysyNimi(null, lukionnnimi);
        if (uusinimi == null) return false;
        lueTiedosto(uusinimi);
        return true;*/
    	Dialogs.showMessageDialog("Ei osata viel� avata tiedostoja");
    }

	

}
