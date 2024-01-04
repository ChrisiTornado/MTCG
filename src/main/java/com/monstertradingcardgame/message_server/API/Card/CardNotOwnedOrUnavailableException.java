package com.monstertradingcardgame.message_server.API.Card;

public class CardNotOwnedOrUnavailableException extends Exception {
    public CardNotOwnedOrUnavailableException() {
        super("Card not owned or unavailable");
    }

    public CardNotOwnedOrUnavailableException(String message) {
        super(message);
    }

    public CardNotOwnedOrUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }
}
