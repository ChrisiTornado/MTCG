package com.monstertradingcardgame.message_server.DAL;

import com.monstertradingcardgame.message_server.Models.User.User;
import com.monstertradingcardgame.message_server.Models.User.UserStats;

import java.util.List;

public interface IGameDao {
    UserStats getStats(User identity);
    List<UserStats> getScoreBoard(User identity);
    void updateStats(User user, boolean won);
}
