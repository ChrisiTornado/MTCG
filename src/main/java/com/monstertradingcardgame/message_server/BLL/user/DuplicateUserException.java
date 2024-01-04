package com.monstertradingcardgame.message_server.BLL.user;

public class DuplicateUserException extends Exception{

    public DuplicateUserException(String message) {
        super(message);
    }
}
