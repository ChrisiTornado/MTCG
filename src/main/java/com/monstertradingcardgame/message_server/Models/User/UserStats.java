package com.monstertradingcardgame.message_server.Models.User;

public class UserStats {
    public static final int START_ELO = 100;
    public static final int START_WINS = 0;
    public static final int START_LOSES = 0;

    public String name;
    public int elo;
    public int wins;
    public int losses;

    public UserStats(String name, int elo, int wins, int losses)
    {
        // check if name == null
        this.name = name;
        this.elo = elo;
        this.wins = wins;
        this.losses = losses;
    }

    @Override
    public String toString() {
        return String.format("\tName: %s\n\tElo: %d\n\tWins: %d\n\tLosses: %d", name, elo, wins, losses);
    }
}
