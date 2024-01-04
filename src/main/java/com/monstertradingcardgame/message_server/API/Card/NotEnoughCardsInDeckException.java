package com.monstertradingcardgame.message_server.API.Card;

public class NotEnoughCardsInDeckException extends Exception {

    public NotEnoughCardsInDeckException() {
        super("Not enough cards in the deck.");
    }

    public NotEnoughCardsInDeckException(String message) {
        super(message);
    }

    public NotEnoughCardsInDeckException(String message, Throwable cause) {
        super(message, cause);
    }
}
