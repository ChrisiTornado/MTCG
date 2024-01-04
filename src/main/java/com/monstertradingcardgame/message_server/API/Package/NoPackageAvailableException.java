package com.monstertradingcardgame.message_server.API.Package;

public class NoPackageAvailableException extends Throwable {
    public NoPackageAvailableException() {
        super("No package available.");
    }

    public NoPackageAvailableException(String message) {
        super(message);
    }

    public NoPackageAvailableException(String message, Throwable cause) {
        super(message, cause);
    }
}
