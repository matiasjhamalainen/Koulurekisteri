package fxLukio;
	
import Lukio.Lukio;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;

/**
 * P��ohjelma k�ytt�liittym�lle
 * @author majuhama laolkorh
 *
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
	 * P��ohjelma k�ynnistyst� varten
	 * @param args 
	 */
	public static void main(String[] args) {
		launch(args);
	}
}