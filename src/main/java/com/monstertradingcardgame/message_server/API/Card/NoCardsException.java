package com.monstertradingcardgame.message_server.API.Card;

public class NoCardsException extends Exception {
    public NoCardsException() {
        super("No cards.");
    }

    public NoCardsException(String message) {
        super(message);
    }

    public NoCardsException(String message, Throwable cause) {
        super(message, cause);
    }
}
