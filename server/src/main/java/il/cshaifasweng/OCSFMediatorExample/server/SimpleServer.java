package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.server.ocsf.AbstractServer;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;
import il.cshaifasweng.OCSFMediatorExample.entities.GameMessage;
import il.cshaifasweng.OCSFMediatorExample.entities.TicTacToe;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.SubscribedClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class SimpleServer extends AbstractServer {
	private static ArrayList<SubscribedClient> SubscribersList = new ArrayList<>();
	private TicTacToe tictactoe = new TicTacToe();
	private Random random = new Random();

	public SimpleServer(int port) {
		super(port);
	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		if (msg instanceof GameMessage) {
			GameMessage gameMsg = (GameMessage) msg;
			if ("REQUEST_MOVE".equals(gameMsg.getAction())) {
				handleMove(gameMsg);
			}
		} else if (msg.toString().startsWith("add client")) {
			SubscribersList.add(new SubscribedClient(client));
			if (SubscribersList.size() == 2) initializeGame();
		}
	}

	private void handleMove(GameMessage gameMsg) {
		if (tictactoe.makeMove(gameMsg.getRow(), gameMsg.getCol(), gameMsg.getPlayer())) {
			// עדכון הלוח לכולם - מעביר גם את התור הבא
			sendToAllClients(new GameMessage("UPDATE_BOARD", gameMsg.getRow(), gameMsg.getCol(), gameMsg.getPlayer(), tictactoe.getCurrentTurn()));

			// בדיקת ניצחון/תיקו
			if (tictactoe.isGameOver()) {
				GameMessage gameOverMsg = new GameMessage("GAME_OVER");
				gameOverMsg.setPlayer(tictactoe.getGameState()); // מחזיר "X", "O" או "DRAW"
				sendToAllClients(gameOverMsg);
			}
		}
	}

	private void initializeGame() {
		tictactoe.resetGame();
		String[] symbols = random.nextBoolean() ? new String[]{"X", "O"} : new String[]{"O", "X"};
		for (int i = 0; i < 2; i++) {
			GameMessage startMsg = new GameMessage("START_GAME");
			startMsg.setPlayer(symbols[i]); // שליחת הסמל הייחודי לכל לקוח
			try { SubscribersList.get(i).getClient().sendToClient(startMsg); } catch (IOException e) { e.printStackTrace(); }
		}
	}

	public void sendToAllClients(Object message) {
		try { for (SubscribedClient sc : SubscribersList) sc.getClient().sendToClient(message); } catch (IOException e) { e.printStackTrace(); }
	}
}