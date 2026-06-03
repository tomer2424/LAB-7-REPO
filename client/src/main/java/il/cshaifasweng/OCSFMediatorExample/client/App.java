package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * JavaFX App - Tic Tac Toe בצד הקליינט
 */
public class App extends Application {

	private static final Logger LOGGER = Logger.getLogger(App.class.getName());

    private static Scene scene;
    private SimpleClient client;

    @Override
    public void start(Stage stage) throws IOException {
    	EventBus.getDefault().register(this);
    	
        // התחל עם login screen
        scene = new Scene(loadFXML("login"), 640, 480);
        stage.setTitle("Tic Tac Toe");
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    @Override
	public void stop() throws Exception {
		EventBus.getDefault().unregister(this);
        
        // נתק מהשרת
		if (client != null) {
			try {
				client.sendToServer("remove client");
				client.closeConnection();
			} catch (Exception e) {
				LOGGER.log(Level.WARNING, "Error closing connection", e);
			}
		}
		super.stop();
	}
    @Subscribe
    public void onEvent(Object event) {
    }

	public static void main(String[] args) {
        launch();
    }

}