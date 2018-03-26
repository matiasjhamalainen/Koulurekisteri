package fxLukio;
	
import Lukio.Lukio;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;

/**
 * P‰‰ohjelma k‰yttˆliittym‰lle
 * TODO: Oppilaan muokkaus ei toimi 
 * TODO: Ohjelman lopetuksessa voisi olla tarkastus "kyll‰/ei" ennen lopullista sulkemista 
 * TODO: voisi tehd‰ toiminnon, joka muodostaisi uuden koulun tiedostoineen (nyt tiedostot luodaan k‰sin)
 * @author majuhama ja laolkorh
 * matias.j.hamalainen@student.jyu.fi, lassi.o.korhonen@student.jyu.fi
 */
public class LukioMain extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader ldr = new FXMLLoader(getClass().getResource("LukioGUIVIEW.fxml"));
			final Pane root = (Pane)ldr.load();
			final LukioGUIController lukioCtrl = (LukioGUIController)ldr.getController();
			
			final Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("lukio.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Lukio");
			
			Lukio lukio = new Lukio();
			lukioCtrl.setLukio(lukio);
			primaryStage.show();
			Application.Parameters params = getParameters();
            if ( params.getRaw().size() > 0 )
                lukioCtrl.lueTiedosto(params.getRaw().get(0));
            else
                if ( !lukioCtrl.avaa() ) Platform.exit();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * P‰‰ohjelma k‰ynnistyst‰ varten
	 * @param args args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
