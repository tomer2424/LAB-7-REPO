package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.GameMessage;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import java.io.IOException;

public class PrimaryController {
	@FXML private GridPane gameBoard;
	@FXML private Label statusLabel;
	private final Button[][] boardButtons = new Button[3][3];
	private String playerSymbol = "";
	private String currentTurn = "X";

	@FXML
	void initialize() {
		EventBus.getDefault().register(this);
		createBoardButtons();
		try { SimpleClient.getClient().sendToServer("add client"); } catch (IOException e) { e.printStackTrace(); }
		updateStatus("Waiting for opponent...");
	}

	private void createBoardButtons() {
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				Button btn = new Button("");
				btn.setPrefSize(60, 60);
				final int r = row, c = col;
				btn.setOnAction(e -> onBoardButtonClicked(r, c));
				boardButtons[row][col] = btn;
				gameBoard.add(btn, col, row);
			}
		}
		setBoardState(false);
	}

	private void onBoardButtonClicked(int row, int col) {
		if (!playerSymbol.equals(currentTurn)) return;
		try {
			SimpleClient.getClient().sendToServer(new GameMessage("REQUEST_MOVE", row, col, playerSymbol, ""));
		} catch (IOException e) { e.printStackTrace(); }
	}

	@Subscribe
	public void onGameMessageReceived(GameMessageEvent event) {
		GameMessage msg = event.getGameMessage();
		Platform.runLater(() -> {
			switch (msg.getAction()) {
				case "START_GAME":
					this.playerSymbol = msg.getPlayer();
					updateStatus("Game started! You are: " + playerSymbol);
					clearBoard();
					currentTurn = "X";
					setBoardState(playerSymbol.equals(currentTurn));
					break;
				case "UPDATE_BOARD":
					boardButtons[msg.getRow()][msg.getCol()].setText(msg.getPlayer());
					currentTurn = msg.getNextTurn();
					updateStatus("Player " + currentTurn + "'s turn");
					setBoardState(playerSymbol.equals(currentTurn));
					break;
				case "GAME_OVER":
					setBoardState(false); // נעילה מוחלטת
					String result = msg.getPlayer();
					if (result.equals("DRAW")) updateStatus("Game Over - It's a DRAW!");
					else updateStatus(result.equals(playerSymbol) ? "🎉 You Won! 🎉" : "Game Over - You Lost!");
					break;
			}
		});
	}

	private void setBoardState(boolean active) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				boardButtons[i][j].setDisable(!active || !boardButtons[i][j].getText().isEmpty());
			}
		}
	}

	private void clearBoard() {
		for (int i = 0; i < 3; i++) for (int j = 0; j < 3; j++) boardButtons[i][j].setText("");
	}

	private void updateStatus(String s) { if (statusLabel != null) statusLabel.setText(s); }
}