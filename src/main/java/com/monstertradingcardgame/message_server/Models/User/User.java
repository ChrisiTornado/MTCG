package com.monstertradingcardgame.message_server.Models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User {
    public int coins;
    public String token;
    public Credentials credentials;
    public UserData userData;

    public List<UUID> deck = new ArrayList<>();

    public List<UUID> stack = new ArrayList<>();

    public User(String username, String password, int coins)
    {
        credentials = new Credentials(username, password);
        this.token = getToken();
        this.coins = coins;
    }

    @Override
    public String toString() {
        return userData == null ? "" : userData.toString();
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    public String getToken() {
        return credentials.getUsername() + "-mtcgToken";
    }
}
