package il.cshaifasweng.OCSFMediatorExample.client;

import org.greenrobot.eventbus.EventBus;

import il.cshaifasweng.OCSFMediatorExample.client.ocsf.AbstractClient;
import il.cshaifasweng.OCSFMediatorExample.entities.Warning;
import il.cshaifasweng.OCSFMediatorExample.entities.GameMessage;

public class SimpleClient extends AbstractClient {
	
	private static SimpleClient client = null;
	private String playerSymbol = ""; // X או O

	private SimpleClient(String host, int port) {
		super(host, port);
	}

	@Override
	protected void handleMessageFromServer(Object msg) {
		// עיבוד GameMessage
		if (msg instanceof GameMessage) {
			GameMessage gameMsg = (GameMessage) msg;
			EventBus.getDefault().post(new GameMessageEvent(gameMsg));
		}
		// עיבוד Warning
		else if (msg instanceof Warning) {
			EventBus.getDefault().post(new WarningEvent((Warning) msg));
		}
		// כל הודעה אחרת
		else {
			String message = msg.toString();
			System.out.println(message);
			if (message.equals("WAITING")) {
				EventBus.getDefault().post(new GameMessageEvent(new GameMessage("WAITING")));
			}
		}
	}
	
	public static SimpleClient getClient() {
		if (client == null) {
			client = new SimpleClient("localhost", 3000);
		}
		return client;
	}
	
	public static SimpleClient getClient(String host, int port) {
		if (client == null) {
			client = new SimpleClient(host, port);
		}
		return client;
	}


	public String getPlayerSymbol() {
		return playerSymbol;
	}


	public void setPlayerSymbol(String symbol) {
		this.playerSymbol = symbol;
	}
}
