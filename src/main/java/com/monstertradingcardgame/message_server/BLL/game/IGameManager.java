package com.monstertradingcardgame.message_server.BLL.game;

import com.monstertradingcardgame.message_server.Models.User.User;
import com.monstertradingcardgame.message_server.Models.User.UserStats;

import java.util.List;

public interface IGameManager {
    List<String> getInToBattle(User user);
    List<UserStats> getScoreboard(User identity);
    UserStats getStats(User identity);
}
