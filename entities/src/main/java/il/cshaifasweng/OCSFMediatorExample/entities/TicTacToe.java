package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;

/**
 * TicTacToe - המחלקה שמנהלת את לוח המשחק איקס-עיגול
 * זה היגיון המשחק: בדיקת מהלכים חוקיים, עדכון לוח, וזיהוי ניצחון
 */
public class TicTacToe implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * לוח המשחק 3x3
     * "" = ריק, "X" = שחקן X, "O" = שחקן O
     */
    private String[][] board;

    /**
     * מצב המשחק: "X", "O", "DRAW" או "CONTINUE"
     */
    private String gameState;

    /**
     * מי התור עכשיו: "X" או "O"
     */
    private String currentTurn;

    /**
     * בנאי - מאתחל לוח ריק
     */
    public TicTacToe() {
        board = new String[3][3];
        gameState = "CONTINUE";
        currentTurn = "X"; // X מתחיל תמיד
        initializeBoard();
    }

    /**
     * אתחול הלוח לתאים ריקים
     */
    private void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = "";
            }
        }
    }

    /**
     * בדיקה האם המהלך חוקי
     * @param row השורה (0-2)
     * @param col העמודה (0-2)
     * @param player השחקן ("X" או "O")
     * @return true אם המהלך חוקי
     */
    public boolean makeMove(int row, int col, String player) {
        // בדיקה שהמדדים בתוך התחום
        if (row < 0 || row > 2 || col < 0 || col > 2) {
            return false;
        }

        // בדיקה שהתא ריק
        if (!board[row][col].equals("")) {
            return false;
        }

        // בדיקה שזה התור של השחקן הזה
        if (!player.equals(currentTurn)) {
            return false;
        }

        // ביצוע המהלך
        board[row][col] = player;

        // בדיקה ניצחון
        if (checkWin(player)) {
            gameState = player; // השחקן המנצח
        }
        // בדיקה תיקו
        else if (isBoardFull()) {
            gameState = "DRAW";
        }
        // המשך המשחק
        else {
            // החלפת התור
            currentTurn = currentTurn.equals("X") ? "O" : "X";
        }

        return true;
    }

    /**
     * בדיקה אם השחקן ניצח
     * @param player השחקן ("X" או "O")
     * @return true אם ניצח
     */
    private boolean checkWin(String player) {
        // בדיקת שורות
        for (int i = 0; i < 3; i++) {
            if (board[i][0].equals(player) &&
                board[i][1].equals(player) &&
                board[i][2].equals(player)) {
                return true;
            }
        }

        // בדיקת עמודות
        for (int j = 0; j < 3; j++) {
            if (board[0][j].equals(player) &&
                board[1][j].equals(player) &&
                board[2][j].equals(player)) {
                return true;
            }
        }

        // בדיקת אלכסון ראשי
        if (board[0][0].equals(player) &&
            board[1][1].equals(player) &&
            board[2][2].equals(player)) {
            return true;
        }

        // בדיקת אלכסון משני
        if (board[0][2].equals(player) &&
            board[1][1].equals(player) &&
            board[2][0].equals(player)) {
            return true;
        }

        return false;
    }

    /**
     * בדיקה אם הלוח מלא (תיקו)
     * @return true אם הלוח מלא
     */
    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j].equals("")) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * קבלת ערך התא
     * @param row השורה
     * @param col העמודה
     * @return תוכן התא ("", "X" או "O")
     */
    public String getCell(int row, int col) {
        if (row < 0 || row > 2 || col < 0 || col > 2) {
            return "";
        }
        return board[row][col];
    }

    /**
     * קבלת כל הלוח
     * @return מערך התא 3x3
     */
    public String[][] getBoard() {
        return board;
    }

    /**
     * קבלת מצב המשחק
     * @return מצב המשחק
     */
    public String getGameState() {
        return gameState;
    }

    /**
     * בדיקה האם המשחק הסתיים
     * @return true אם המשחק הסתיים
     */
    public boolean isGameOver() {
        return !gameState.equals("CONTINUE");
    }

    /**
     * קבלת מי התור עכשיו
     * @return "X" או "O"
     */
    public String getCurrentTurn() {
        return currentTurn;
    }

    /**
     * קביעת מי התור עכשיו (טוב למטרות שיתוף)
     * @param turn סמל השחקן
     */
    public void setCurrentTurn(String turn) {
        this.currentTurn = turn;
    }

    /**
     * הצגת הלוח כטקסט
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                sb.append(board[i][j].isEmpty() ? "-" : board[i][j]);
                if (j < 2) sb.append("|");
            }
            if (i < 2) sb.append("\n-----\n");
        }
        return sb.toString();
    }

    /**
     * איפוס המשחק
     */
    public void resetGame() {
        board = new String[3][3];
        gameState = "CONTINUE";
        currentTurn = "X";
        initializeBoard();
    }
}

