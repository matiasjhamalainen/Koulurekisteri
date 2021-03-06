package fxLukio;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import Lukio.Kurssi;

/**
 * TableView:t� varten kenno jolla voidaan muokata kurssia.
 * Tieto otetaan editorista heti kun n�pp�in p��stet��n yl�s, jolloin
 * saadaan samanlainen k�yt�s kuin itse j�senen editoinnissa.
 * @author majuhama ja laolkorh
 * matias.j.hamalainen@student.jyu.fi, lassi.o.korhonen@student.jyu.fi
 * @version 20.4.2016
 */
public class KurssiCell extends TableCell<Kurssi, String> {
    
    /**
     * Rajapinta funktiolle, joka asettaa arvon kurssille 
     */
    interface KurssiAsetus {
        /**
         * Asettaa arvon kurssi
         * @param kur kurssi jolle arvo asetetaan
         * @param kenttaNro mihin kentt��n
         * @param uusiarvo mik� on uusi arvo
         * @return null jos ok, muuten virhe
         */
        public String aseta(Kurssi kur, int kenttaNro, String uusiarvo);
    }

    
    /**
     * Alustetaan TableView n�ytt�m��n kurssejaa.
     * @param tableKurssit valmiiksi luotu TableView
     * @param asettaja luokka joka suorittaa asetuksen. Mik�li t�t� ei anneta
     *                 asettaa itse vastaavaan paikkaan.
     */
    public static void alustaKurssiTable(TableView<Kurssi> tableKurssit, KurssiAsetus asettaja) {
        Kurssi apukurssi = new Kurssi();
        
        int eka = apukurssi.ekaKentta();
        int kenttia = apukurssi.getKenttia();
        
        for (int k=eka; k<kenttia; k++) {
            TableColumn<Kurssi, String> tc = new TableColumn<>(apukurssi.getKysymys(k));

            final int kenttaNro = k;
            tc.setCellFactory(column -> new KurssiCell(kenttaNro, asettaja));
            tc.setCellValueFactory((rivi) -> {
                // String s = ("0000"+rivi.getValue().anna(kenttaNro));
                // return new SimpleStringProperty(s.substring(s.length()-4));
                String s = rivi.getValue().anna(kenttaNro);
                if ( s.length() > 0 && s.charAt(0) == ' ') s = s.replace(' ','0'); // newChar) s = '!' + s.substring(1); 
                return new SimpleStringProperty(s);
            });

            tc.setPrefWidth(90);
            tc.setMaxWidth(300);
            
            tableKurssit.getColumns().add(tc);
            tableKurssit.setTableMenuButtonVisible(true);
        }
        
        ObservableList<Kurssi> data = FXCollections.observableArrayList();
        tableKurssit.setItems(data);
        tableKurssit.setEditable(true);
    }


    /**
     * Aktivoidaan viimeisin solu muokkaamista varten
     * @param table mist� taulukosta
     */
    public static void editLast(TableView<Kurssi> table) {
        int r = table.getItems().size()-1;
        if ( r < 0 ) return;
        @SuppressWarnings("unchecked")
        TableColumn<Kurssi, String> col = (TableColumn<Kurssi, String>)table.getColumns().get(0);
        table.requestFocus();
        table.getSelectionModel().select(r, col);
        // Platform.runLater(() -> table.edit(0, col));
        table.edit(r, col);
        //table.edit(table.getFocusModel().getFocusedCell().getRow(), col); 
        //table.getSelectionModel();
        
    }

    /**
     * Palautetaan valitun rivin kohdalla oleva kurssi
     * @param table talukko josta etsit��n
     * @return harrastus kohdalla
     */
    public static Kurssi getSelected(TableView<Kurssi> table) {
        return table.getFocusModel().getFocusedItem();
    }

    
    
    private TextField textField;
    private int kenttaNro;
    private KurssiAsetus asettaja;

    
    /**
     * Alustetaan kentt�
     * @param kenttaNro monettako kentt�� kenno edustaa
     * @param asettaja luokka joka suottaa arvon asettamisen.
     */
    public KurssiCell(int kenttaNro, KurssiAsetus asettaja) {
        this.kenttaNro = kenttaNro;
        this.asettaja = asettaja;
    }


    /** 
     * Oma asetus, joka asettaa joko kutsumalla annettua asettajaa tai kurssin omaa
     * @param kur mille kurssille asetetaan
     * @param kenttanro mihin kentt��n
     * @param uusiarvo mik� arvo
     * @return null jos ei virhett�, muuten virhe
     */
    protected String aseta(Kurssi kur, int kenttanro, String uusiarvo) {
        String arvo = "";
        if ( uusiarvo != null ) arvo = uusiarvo;
        if ( asettaja != null ) return asettaja.aseta(kur, kenttanro, arvo);
        if ( kur == null ) return "Ei harrastusta";
        return kur.aseta(kenttaNro, arvo);

    }
    
    
    /**
     * @return antaa kohdalla olevan kurssin
     */
    protected Kurssi getObject() {
        @SuppressWarnings("unchecked")
        TableRow<Kurssi> row = getTableRow();
        if ( row == null ) return null;
        Kurssi kur = row.getItem();
        return kur;
    }


    /**
     * @return antaa kohdalla olevan kurssin kent�n sis�ll�n merkkijonona
     */
    protected String getObjectItem() {
        Kurssi kur = getObject();
        if ( kur == null ) return getItem();
        return kur.anna(kenttaNro);
    }


    /**
     * Editointi kentt��n aloitetaan
     */
    @Override
    public void startEdit() {
        if ( isEmpty()) return;
        super.startEdit();
        createTextField();
        setText(null);
        setGraphic(textField);
        textField.setText(getObjectItem());
        Platform.runLater(() -> textField.requestFocus());
        textField.selectAll();
    }


    /**
     * Peruutetaan kent�n editointi
     */
    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setText(getObjectItem());
        setGraphic(null);
    }


    
    @Override
    public void updateItem(String itm, boolean empty) {
        Kurssi kur = getObject();
        String item = itm;
        if ( kur != null ) item = kur.anna(kenttaNro);


        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setStyle("");
            setGraphic(null);
            return;
        } 
        if (isEditing()) {
            if (textField != null) textField.setText(getObjectItem());
            setText(null);
            setGraphic(textField);
            return;
        }              
        setText(getObjectItem());
        setGraphic(null);
    }


    /**
     * Luodaan tableen uusi textfield
     */
    @SuppressWarnings("unchecked")
    private void createTextField() {
        if ( textField != null ) return;
        textField = new TextField();
        textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
        textField.focusedProperty().addListener( (arg0, arg1,arg2) -> {
            if (!arg2) {
                commitEdit(textField.getText());
            }
        });
        textField.setOnAction(e -> commitEdit(textField.getText()));
        textField.setOnKeyReleased(e -> {
            if ( e == null ) return;
            if ( textField == null ) return;
            if ( kenttaNro == 0 ) return;
            Kurssi kur = getObject();
            String s = textField.getText();
            String virhe = aseta(kur, kenttaNro, s);
            if ( virhe != null ) textField.setStyle("-fx-background-color: yellow");
            else textField.setStyle("");
        });
        textField.setOnKeyPressed(t -> {
            if (t.getCode() == KeyCode.ENTER) {
                // commitEdit(textField.getText());
                cancelEdit();
                t.consume();
            } else if (t.getCode() == KeyCode.ESCAPE) {
                cancelEdit();
                t.consume();
            } else if (t.getCode() == KeyCode.TAB) {
                // commitEdit(textField.getText()); // ei tarvii kun talletetaan muutenkin
                cancelEdit();
                t.consume();
                // if ( getObject() != null ) return;
                TableView<Kurssi> table = getTableView();
                if ( t.isShiftDown() )
                    table.getFocusModel().focusLeftCell();
                else 
                    table.getFocusModel().focusRightCell();
                
                table.edit(getTableRow().getIndex(), table.getFocusModel().getFocusedCell().getTableColumn());
              
            }
        });
    }
}

 


