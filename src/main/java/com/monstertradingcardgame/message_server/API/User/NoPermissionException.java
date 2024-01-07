package com.monstertradingcardgame.message_server.API.User;

public class NoPermissionException extends Exception {
    public NoPermissionException() {
        super("Permission denied");
    }
}
