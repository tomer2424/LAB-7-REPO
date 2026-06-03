package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;

/**
 * GameState - מחלקה שמייצגת את מצב המשחק הכולל
 * משמשת להעברת מידע בין Server ל-Client על מצב המשחק
 */
public class GameState implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * לוח המשחק
     */
    private String[][] board;

    /**
     * מצב המשחק: "WAITING_FOR_PLAYER", "IN_PROGRESS", "X_WIN", "O_WIN", "DRAW"
     */
    private String status;

    /**
     * מי התור עכשיו: "X" או "O"
     */
    private String currentTurn;

    /**
     * המשחקנים
     * players[0] = סמל X
     * players[1] = סמל O
     */
    private String[] players;

    /**
     * עיתוי היצירה
     */
    private long timestamp;

    /**
     * בנאי - משחק חדש ממתין לשחקנים
     */
    public GameState() {
        board = new String[3][3];
        status = "WAITING_FOR_PLAYER";
        currentTurn = "X";
        players = new String[]{"", ""};
        timestamp = System.currentTimeMillis();
        initializeBoard();
    }

    /**
     * אתחול לוח ריק
     */
    private void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = "";
            }
        }
    }

    // ============= Getters =============

    /**
     * קבלת הלוח
     */
    public String[][] getBoard() {
        return board;
    }

    /**
     * קבלת ערך תא מסוים
     */
    public String getCell(int row, int col) {
        if (row < 0 || row > 2 || col < 0 || col > 2) {
            return "";
        }
        return board[row][col];
    }

    /**
     * קבלת מצב המשחק
     */
    public String getStatus() {
        return status;
    }

    /**
     * קבלת מי התור עכשיו
     */
    public String getCurrentTurn() {
        return currentTurn;
    }

    /**
     * קבלת סמלי השחקנים
     */
    public String[] getPlayers() {
        return players;
    }

    /**
     * קבלת סמל שחקן מסוים
     * @param index 0 = X, 1 = O
     */
    public String getPlayer(int index) {
        return (index == 0 || index == 1) ? players[index] : "";
    }

    /**
     * קבלת העיתוי
     */
    public long getTimestamp() {
        return timestamp;
    }

    // ============= Setters =============

    /**
     * קביעת הלוח
     */
    public void setBoard(String[][] newBoard) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.board[i][j] = newBoard[i][j];
            }
        }
    }

    /**
     * קביעת ערך תא מסוים
     */
    public void setCell(int row, int col, String value) {
        if (row >= 0 && row < 3 && col >= 0 && col < 3) {
            board[row][col] = value;
        }
    }

    /**
     * קביעת מצב המשחק
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * קביעת מי התור עכשיו
     */
    public void setCurrentTurn(String turn) {
        this.currentTurn = turn;
    }

    /**
     * קביעת סמלי השחקנים
     */
    public void setPlayers(String[] players) {
        if (players != null && players.length == 2) {
            this.players = players;
        }
    }

    /**
     * קביעת סמל שחקן מסוים
     */
    public void setPlayer(int index, String symbol) {
        if ((index == 0 || index == 1) && symbol != null) {
            players[index] = symbol;
        }
    }

    // ============= Helper Methods =============

    /**
     * בדיקה האם המשחק הסתיים
     */
    public boolean isGameOver() {
        return !status.equals("WAITING_FOR_PLAYER") && !status.equals("IN_PROGRESS");
    }

    /**
     * בדיקה האם שני שחקנים התחברו
     */
    public boolean hasPlayers() {
        return !players[0].isEmpty() && !players[1].isEmpty();
    }

    /**
     * ריסט המשחק
     */
    public void resetGame() {
        board = new String[3][3];
        status = "WAITING_FOR_PLAYER";
        currentTurn = "X";
        players = new String[]{"", ""};
        timestamp = System.currentTimeMillis();
        initializeBoard();
    }

    /**
     * הצגת מצב המשחק כטקסט
     */
    @Override
    public String toString() {
        return "GameState{" +
                "status='" + status + '\'' +
                ", currentTurn='" + currentTurn + '\'' +
                ", playerX='" + players[0] + '\'' +
                ", playerO='" + players[1] + '\'' +
                '}';
    }
}

