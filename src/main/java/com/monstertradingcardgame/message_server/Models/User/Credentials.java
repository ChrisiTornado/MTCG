package com.monstertradingcardgame.message_server.Models.User;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Credentials {
    public String username;
    public String password;

    public Credentials(@JsonProperty("Username") String username, @JsonProperty("Password") String password)
    {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
