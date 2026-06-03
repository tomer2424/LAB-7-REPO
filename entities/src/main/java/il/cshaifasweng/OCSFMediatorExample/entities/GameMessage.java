package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;

public class GameMessage implements Serializable {
	private static final long serialVersionUID = 1L;

	private String action;
	private int row;
	private int col;
	private String player;
	private String nextTurn;

	// בנאי ריק
	public GameMessage() {}


	// בנאי עם 5 פרמטרים (משמש את ה-Server ב-SimpleServer)
	public GameMessage(String action, int row, int col, String player, String nextTurn) {
		this.action = action;
		this.row = row;
		this.col = col;
		this.player = player;
		this.nextTurn = nextTurn;
	}

	// בנאי בסיסי
	public GameMessage(String action) {
		this.action = action;
	}

	// Getters & Setters
	public String getAction() { return action; }
	public void setAction(String action) { this.action = action; }
	public int getRow() { return row; }
	public void setRow(int row) { this.row = row; }
	public int getCol() { return col; }
	public void setCol(int col) { this.col = col; }
	public String getPlayer() { return player; }
	public void setPlayer(String player) { this.player = player; }
	public String getNextTurn() { return nextTurn; }
	public void setNextTurn(String nextTurn) { this.nextTurn = nextTurn; }
}