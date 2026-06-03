package il.cshaifasweng.OCSFMediatorExample.client;

import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LoginController {

	@FXML
	private TextField hostField;

	@FXML
	private TextField portField;

	@FXML
	private Button connectButton;

	@FXML
	private Label statusLabel;

	@FXML
	void initialize() {
		// ערכי ברירת מחדל
		hostField.setText("localhost");
		portField.setText("3000");
	}

	@FXML
	void onConnectClicked(ActionEvent event) {
		String host = hostField.getText().trim();
		String portStr = portField.getText().trim();

		if (host.isEmpty() || portStr.isEmpty()) {
			statusLabel.setText("Please enter host and port");
			return;
		}

		int port;
		try {
			port = Integer.parseInt(portStr);
		} catch (NumberFormatException e) {
			statusLabel.setText("Invalid port number");
			return;
		}

		connectButton.setDisable(true);
		statusLabel.setText("Connecting...");

		// התחברות לשרת ברקע
		new Thread(() -> {
			try {
				SimpleClient client = SimpleClient.getClient(host, port);
				client.openConnection();
				
				// לאחר החיבור המחוזק, עבור לצג המשחק
				Platform.runLater(() -> {
					try {
						App.setRoot("primary");
					} catch (IOException e) {
						statusLabel.setText("Error: " + e.getMessage());
						connectButton.setDisable(false);
					}
				});
			} catch (IOException e) {
				Platform.runLater(() -> {
					statusLabel.setText("Connection failed: " + e.getMessage());
					connectButton.setDisable(false);
				});
			}
		}).start();
	}
}

