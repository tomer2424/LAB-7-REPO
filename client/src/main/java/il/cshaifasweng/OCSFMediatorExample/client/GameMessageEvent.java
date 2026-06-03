package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.GameMessage;

/**
 * GameMessageEvent - אירוע EventBus להעברת GameMessage בין קומפוננטים
 */
public class GameMessageEvent {
    private GameMessage gameMessage;

    public GameMessageEvent(GameMessage gameMessage) {
        this.gameMessage = gameMessage;
    }

    public GameMessage getGameMessage() {
        return gameMessage;
    }
}

