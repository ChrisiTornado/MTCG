package com.monstertradingcardgame.message_server.API.User;

public class DuplicatedUserException extends Exception{
    public DuplicatedUserException() {
        super("User exists already");
    }
}
