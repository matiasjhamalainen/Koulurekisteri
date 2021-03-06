package fxLukio;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL; 

import java.util.ResourceBundle; 

import fi.jyu.mit.fxgui.Dialogs; 
import fi.jyu.mit.fxgui.ListChooser;

import java.awt.Desktop;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Collection;
import java.util.List;
import Lukio.Kurssi;
import Lukio.Lukio;
import Lukio.Oppilas;
import Lukio.SailoException;

import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.StringGrid;
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
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane; 

import static fxLukio.OppilasDialogController.*; 

/**
 * @author majuhama ja laolkorh
 * matias.j.hamalainen@student.jyu.fi, lassi.o.korhonen@student.jyu.fi
 * @version 15.03.2016
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
	@FXML private MenuItem menuMuokkaaOppilas;
	@FXML private MenuItem menuAbout;
 	@FXML private MenuItem buttonSulje;
    @FXML private Button buttonLisaaKurssi;
	@FXML private Button buttonPoistaKurssi;
	@FXML private ScrollPane panelOppilas;
	@FXML private GridPane gridOppilas; 
	@FXML private ListChooser chooserOppilaat;
	@FXML private StringGrid<TextField> tableKurssit;
	@FXML private MenuItem menuMuuta;
	
	
	@Override
	/**
	 * Ohjelma kutsuu aliohjelmaa alusta.
	 */
	public void initialize(URL url, ResourceBundle bundle) {
		alusta();
		
	}
		

	@FXML
	private void handleMuuta(){
		muutaNakyma();
	}
		

	@FXML
	private void handleHakuehto() {
		 hae(0);
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
	    
	    
	    @FXML private void handleMuokkaa() { 
	    	muokkaa(kentta); 
	     } 

//===========================================================================================    
// T�st� eteenp�in ei k�ytt�liittym��n suoraan liittyv�� koodia    

	private Lukio lukio;
	private Oppilas oppilasKohdalla;
	private ListView<Oppilas> listOppilaat = new ListView<Oppilas>();
	private ObservableList<Oppilas> listdataOppilaat = FXCollections.observableArrayList();
	private String lukionnimi = "Per�hiki�";
	private TextField[] edits; 
	private int kentta; 
	private TableView<Kurssi> tableViewKurssit = new TableView<>();
	private Oppilas apuoppilas = new Oppilas();
	private boolean muutos = false;
	
	/**
     * Luokka, jolla hoidellaan miten oppilas n�ytet��n listassa 
     */
    public static class CellOppilas extends ListCell<Oppilas> {
        @Override protected void updateItem(Oppilas item, boolean empty) {
            super.updateItem(item, empty); // ilman t�t� ei n�y valinta
            setText(empty ? "" : item.getNimi());
        }
    }
	
    /**
     * Tekee tarvittavat muut alustukset, jotta voidaan n�ytt�� oppilaslista ja
     * kunkin oppilaan kurssit. 
     */
	private void alusta() {
		panelOppilas.setFitToHeight(true);
        BorderPane parent = (BorderPane)chooserOppilaat.getParent();
        parent.setCenter(listOppilaat);
        listOppilaat.setCellFactory( p -> new CellOppilas() );
        listOppilaat.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            naytaOppilas();
        });
        edits = OppilasDialogController.luoKentat(gridOppilas); 
     	for (TextField edit: edits) 
     	  if ( edit != null ) { 
     		  edit.setEditable(false); 
     		  edit.setOnMouseClicked(e -> { if ( e.getClickCount() > 1 ) muokkaa(getFieldId(e.getSource(),0)); }); 
     		  edit.focusedProperty().addListener((a,o,n) -> kentta = getFieldId(edit,0)); 
     	  } 
     	
     	
     	
     	parent = (BorderPane)tableKurssit.getParent();
        parent.setCenter(tableViewKurssit);
        tableViewKurssit.setPlaceholder(new Label("Oppilaalla ei ole kursseja"));
        tableKurssit = null;
        KurssiCell.alustaKurssiTable(tableViewKurssit, (kur, kenttanro, arvo) -> {
            String virhe = kur.aseta(kenttanro, arvo);
            naytaVirhe(virhe);
            return virhe;
        });
	}
	
	/** 
	 * Oppilaan tietojen muokkaus 
	 * @param k mit� kentt�� muokataan 
	 */
	private void muokkaa(int k) {
		if (oppilasKohdalla == null) return;
			try {
			Oppilas oppilas;
			oppilas = OppilasDialogController.kysyOppilas(null, oppilasKohdalla.clone(), k);
			if (oppilas == null)
				return;
			lukio.korvaaTaiLisaa(oppilas);
			hae(oppilas.getId());
		} catch (CloneNotSupportedException e) {
			// 
		} catch (SailoException e) {
			Dialogs.showMessageDialog(e.getMessage());
		}
	}
	
	 /**
     * N�ytt�� listasta valitun oppilaan tiedot, tilap�isesti yhteen isoon edit-kentt��n
     */
    protected void naytaOppilas() {
        oppilasKohdalla = listOppilaat.getSelectionModel().getSelectedItem();

        if (oppilasKohdalla == null) return;
        OppilasDialogController.naytaOppilas(edits, oppilasKohdalla); 
        naytaKurssit(oppilasKohdalla); 
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
     * @param textArea alue johon tulostetaan
     */
    public void tulostaValitut(javafx.scene.control.TextArea textArea) {
        try (PrintStream os = TextAreaOutputStream.getTextPrintStream(textArea)) {
        	os.println("Valitut oppilaat");
            for (Oppilas oppilas:listdataOppilaat) {
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
        int onro = oid; // onro oppilaan numero, joka aktivoidaan haun j�lkeen
        if ( oid <= 0 ) {
            Oppilas kohdalla = getOppilasKohdalla();
            if ( kohdalla != null ) onro = kohdalla.getId();
        }
        
        int k = cbKentat.getSelectionModel().getSelectedIndex() + apuoppilas.ekaKentta();
        String ehto = hakuehto.getText(); 
        if (ehto.indexOf('*') < 0) ehto = "*" + ehto + "*";
        
        naytaVirhe(null);
        
        listdataOppilaat.clear();
        listOppilaat.setItems(null);

        int index = 0;
        Collection<Oppilas> oppilaat;
        try {
            oppilaat = lukio.etsi(ehto, k);
            int i = 0;
            for (Oppilas oppilas:oppilaat) {
                if (oppilas.getId() == onro) index = i;
                listdataOppilaat.add(oppilas);
                i++;
            }
        } catch (SailoException ex) {
            Dialogs.showMessageDialog("Oppilaan hakemisessa ongelmia! " + ex.getMessage());
        }
        listOppilaat.setItems(listdataOppilaat);
        listOppilaat.getSelectionModel().select(index); // t�st� tulee muutosviesti joka n�ytt�� j�senen
    }
    

    /**
     * N�ytet��n kurssit taulukkoon.  Tyhjennet��n ensin taulukko ja sitten
     * lis�t��n siihen kaikki kurssit
     * @param oppilas oppilas, jonka harrastukset n�ytet��n
     */
    private void naytaKurssit(Oppilas oppilas) {
    	ObservableList<Kurssi> items = tableViewKurssit.getItems();
        items.clear();
        if ( oppilas == null ) return;
        
        List<Kurssi> kurssit;
        kurssit = lukio.annaKurssit(oppilas);
		if ( kurssit.size() == 0 ) return;
		items.addAll(kurssit); 
    }
	

	/**
	 * Lis�� uuden tyhj�n kurssin
	 */
	void lisaaKurssi() {
		  Oppilas oppilasKohdalla = getOppilasKohdalla();
	        if ( oppilasKohdalla == null ) return; 
	        Kurssi kur = new Kurssi(oppilasKohdalla.getId()); 
	        kur.rekisteroi(); 
	        lukio.lisaa(kur);
	        
	        naytaKurssit(oppilasKohdalla); 
	        KurssiCell.editLast(tableViewKurssit);
	 }
		


	/**
	 * Poistaa kurssin
	 */
	void poistaKurssi() {
		int rivi = tableViewKurssit.getFocusModel().getFocusedCell().getRow();
        if ( rivi < 0 ) return;
        Kurssi kurssi = KurssiCell.getSelected(tableViewKurssit);
        if ( kurssi == null ) return;
        lukio.poistaKurssi(kurssi);
        naytaKurssit(getOppilasKohdalla());
        int kursseja = tableViewKurssit.getItems().size(); 
        if ( rivi >= kursseja ) rivi = kursseja -1;
        tableViewKurssit.getFocusModel().focus(rivi);
        tableViewKurssit.getSelectionModel().select(rivi);
    }
	

	/**
     * Tallentaa tiedot
     * @return null jos onnistuu, muuten virhe tekstin�
     */
    private String tallenna() {
        try {
            lukio.tallenna();
            return null;
        } catch (SailoException ex) {
            Dialogs.showMessageDialog(ex.getMessage());
            return ex.getMessage();
        }
    }

	
    /**
     * Sulkee ohjelman.
     */
	private void sulje() {
		tallenna();
		Platform.exit();
		
	}

	

	/**
	 * Avaa apulinkin internetist�. 
	 */
	void apua(){
		Desktop desktop = Desktop.getDesktop();
	        try {
	            URI uri = new URI("https://trac.cc.jyu.fi/projects/ohj2ht/wiki/k2016/suunnitelmat/laolkorh");
	            desktop.browse(uri);
	        } catch (URISyntaxException e) {
	            return;
	        } catch (IOException e) {
	            return;
	        }
	    
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
		Oppilas oppilas = getOppilasKohdalla();
        if ( oppilas == null ) return;
        if ( !Dialogs.showQuestionDialog("Poisto", "Poistetaanko oppilas: " + oppilas.getNimi(), "Kyll�", "Ei") )
            return;
        lukio.poista(oppilas.getId());
        int index = listOppilaat.getSelectionModel().getSelectedIndex();
        hae(0);
        listOppilaat.getSelectionModel().select(index);
	}

	/**
	 * Aliohjelma palauttaa valitun oppilaan
	 * @return oppilas
	 */
	private Oppilas getOppilasKohdalla() {
		return listOppilaat.getSelectionModel().getSelectedItem();
	}


	/**
	 * Lis�� rekisteriin uuden oppilaan.
	 */
	void uusiOppilas() {
		try {
            Oppilas uusi = new Oppilas();
            uusi = OppilasDialogController.kysyOppilas(null,  uusi,  0);
            if ( uusi == null) return;
            uusi.rekisteroi();
            lukio.lisaa(uusi);
            hae(uusi.getId());
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Ongelmia uuden luomisessa " + e.getMessage());
            return;
        }
        
    }
	
	
      
	
	 
    /**
     * Alustaa kerhon lukemalla sen valitun nimisest� tiedostosta
     * @param nimi tiedosto josta kerhon tiedot luetaan
     * @return null jos onnistuu, muuten virhe tekstin�
     */
    protected String lueTiedosto(String nimi) {
        lukionnimi = nimi;
        setTitle(lukionnimi);
        try {
            lukio.lueTiedostosta(nimi);
            hae(0);
            return null;
        } catch (SailoException e) {
            hae(0);
            String virhe = e.getMessage(); 
            if ( virhe != null ) Dialogs.showMessageDialog(virhe);
            return virhe;
        }
     }

    
    /**
     * Kysyt��n tiedoston nimi ja luetaan se
     * @return true jos onnistui, false jos ei
     */
    public boolean avaa() {
        String uusinimi = LukionNimiController.kysyNimi(null, lukionnimi);
        //if (uusinimi == null) return false;
    	//String uusinimi = lukionnimi;
        //lueTiedosto("Per�hiki�");
    	if (uusinimi == null) return false;
        lueTiedosto(uusinimi);
        return true;
    	
    }

    /**
     * Muuttaa alareunan nappuloiden v�ri�.
     */
    private void muutaNakyma() {
    	if (!muutos){ 
    		
    	buttonUusiOppilas.setStyle("-fx-background-color: cyan;");
    	buttonTallenna.setStyle("-fx-background-color: cyan;");
    	buttonLisaaKurssi.setStyle("-fx-background-color: cyan;");
    	buttonPoistaKurssi.setStyle("-fx-background-color: cyan;");
    	this.muutos = true;
    	return;
    	}
    	if (muutos) {
    		buttonUusiOppilas.setStyle("default");
        	buttonTallenna.setStyle("default");
        	buttonLisaaKurssi.setStyle("default");
        	buttonPoistaKurssi.setStyle("default");
        	this.muutos = false;
        	return;
        	
    	}
    	muutos = true;
    	
	}
	

}
