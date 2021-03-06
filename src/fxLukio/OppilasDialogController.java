package fxLukio;

import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import fi.jyu.mit.ohj2.Mjonot;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import Lukio.Oppilas;

/**
 * Kysyt��n oppilaan tiedot luomalla sille uusi dialogi
 * @author laolkorh ja majuhama
 * matias.j.hamalainen@student.jyu.fi, lassi.o.korhonen@student.jyu.fi
 * @version 11.4.2016
 */
public class OppilasDialogController implements ModalControllerInterface<Oppilas>,Initializable  {

    @FXML private ScrollPane panelOppilas;
    @FXML private GridPane gridOppilas;
    @FXML private Label labelVirhe;

    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta();  
    }
    
    @FXML private void handleOK() {
        if ( oppilasKohdalla != null && oppilasKohdalla.getNimi().trim().equals("") ) {
            naytaVirhe("Nimi ei saa olla tyhj�");
            return;
        }
        ModalController.closeStage(labelVirhe);
    }

    
    @FXML private void handleCancel() {
        oppilasKohdalla = null;
        ModalController.closeStage(labelVirhe);
    }

// ========================================================    
    private Oppilas oppilasKohdalla;
    private static Oppilas apuoppilas = new Oppilas(); // J�sen jolta voidaan kysell� tietoja.
    private TextField[] edits;
    private int kentta = 0;
    

    /**
     * Luodaan GridPaneen oppilaan tiedot
     * @param gridOppilas mihin tiedot luodaan
     * @return luodut tekstikent�t
     */
    public static TextField[] luoKentat(GridPane gridOppilas) {
        gridOppilas.getChildren().clear();
        TextField[] edits = new TextField[apuoppilas.getKenttia()];
        
        for (int i=0, k = apuoppilas.ekaKentta(); k < apuoppilas.getKenttia(); k++, i++) {
            Label label = new Label(apuoppilas.getKysymys(k));
            gridOppilas.add(label, 0, i);
            TextField edit = new TextField();
            edits[k] = edit;
            edit.setId("e"+k);
            gridOppilas.add(edit, 1, i);
        }
        return edits;
    }
    

    /**
     * Palautetaan komponentin indeksist� saatava luku
     * @param obj tutkittava komponentti
     * @param oletus mik� arvo jos id ei ole kunnollinen
     * @return komponentin indeksi lukuna 
     */
    public static int getFieldId(Object obj, int oletus) {
        if ( !( obj instanceof Node)) return oletus;
        Node node = (Node)obj;
        return Mjonot.erotaInt(node.getId().substring(1),oletus);
    }
    
    
    /**
     * Tekee tarvittavat muut alustukset, nyt vaihdetaan GridPanen tilalle
     * yksi iso tekstikentt�, johon voidaan tulostaa j�senten tiedot.
     */
    protected void alusta() {
        edits = luoKentat(gridOppilas);
        for (TextField edit : edits)
            if ( edit != null )
                edit.setOnKeyReleased( e -> kasitteleMuutosOppilas((TextField)(e.getSource())));
        panelOppilas.setFitToHeight(true);
    }
    
    
    @Override
    public void setDefault(Oppilas oletus) {
        oppilasKohdalla = oletus;
        naytaOppilas(edits, oppilasKohdalla);
    }

    
    @Override
    public Oppilas getResult() {
        return oppilasKohdalla;
    }
    
    
    private void setKentta(int kentta) {
        this.kentta = kentta;
    }
    
    
    /**
     * Mit� tehd��n kun dialogi on n�ytetty
     */
    @Override
    public void handleShown() {
        kentta = Math.max(apuoppilas.ekaKentta(), Math.min(kentta, apuoppilas.getKenttia()-1));
        edits[kentta].requestFocus();
    }
    
    
    private void naytaVirhe(String virhe) {
        if ( virhe == null || virhe.isEmpty() ) {
            labelVirhe.setText("");
            labelVirhe.getStyleClass().removeAll("virhe");
            return;
        }
        labelVirhe.setText(virhe);
        labelVirhe.getStyleClass().add("virhe");
    }

    
    /**
     * K�sitell��n oppilaaseen tullut muutos
     * @param edit muuttunut kentt�
     */
    protected void kasitteleMuutosOppilas(TextField edit) {
        if (oppilasKohdalla == null) return;
        int k = getFieldId(edit,apuoppilas.ekaKentta());
        String s = edit.getText();
        String virhe = null;
        virhe = oppilasKohdalla.aseta(k,s); 
        if (virhe == null) {
            Dialogs.setToolTipText(edit,"");
            edit.getStyleClass().removeAll("virhe");
            naytaVirhe(virhe);
        } else {
            Dialogs.setToolTipText(edit,virhe);
            edit.getStyleClass().add("virhe");
            naytaVirhe(virhe);
        }
    }
    
    
    /**
     * N�ytet��n oppilaan tiedot TextField komponentteihin
     * @param edits taulukko TextFieldeist� johon n�ytet��n
     * @param oppilas n�ytett�v� oppilas
     */
    public static void naytaOppilas(TextField[] edits, Oppilas oppilas) {
        if (oppilas == null) return;
        for (int k = oppilas.ekaKentta(); k < oppilas.getKenttia(); k++) {
            edits[k].setText(oppilas.anna(k));
        }
    }
    
    
    /**
     * Luodaan oppilaan kysymisdialogi ja palautetaan sama tietue muutettuna tai null
     * @param modalityStage mille ollaan modaalisia, null = sovellukselle
     * @param oletus mit� dataan n�ytet��n oletuksena
     * @param kentta mik� kentt� saa fokuksen kun n�ytet��n
     * @return null jos painetaan Cancel, muuten t�ytetty tietue
     */
    public static Oppilas kysyOppilas(Stage modalityStage, Oppilas oletus, int kentta) {
    	 return ModalController.<Oppilas, OppilasDialogController>showModal(
                OppilasDialogController.class.getResource("OppilasDialogView.fxml"),
                "Lukio",
                modalityStage, oletus, 
        		ctrl -> ((OppilasDialogController)ctrl).setKentta(kentta));
    }

}
