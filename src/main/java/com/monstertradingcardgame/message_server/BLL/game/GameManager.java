package com.monstertradingcardgame.message_server.BLL.game;

import com.monstertradingcardgame.message_server.DAL.DataBaseGameDao;
import com.monstertradingcardgame.message_server.DAL.DatabaseCardDao;
import com.monstertradingcardgame.message_server.Models.Card.Card;
import com.monstertradingcardgame.message_server.Models.User.User;
import com.monstertradingcardgame.message_server.Models.User.UserStats;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;

public class GameManager implements IGameManager{
    DataBaseGameDao _dataBaseGameDao;
    DatabaseCardDao _databaseCardDao;

    Queue<User> users = new ConcurrentLinkedQueue<>();
    ConcurrentMap<String, List<String>> log = new ConcurrentHashMap<>();
    List<String> battleslogs = new ArrayList<>();
    List<Card> user_deck;
    List<Card> otherUser_deck;
    static Random random = new Random();
    int roundCount = 1;

    public GameManager(DataBaseGameDao dataBaseGameDao, DatabaseCardDao databaseCardDao) {
        this._dataBaseGameDao = dataBaseGameDao;
        this._databaseCardDao = databaseCardDao;
    }

    @Override
    public List<String> getInToBattle(User user) {
        System.out.println(user.credentials.username + " entered battle");
        User otherUser = null;
        boolean result = users.peek() != null;
        if (result) {
            otherUser = users.poll();
            battle(user, otherUser);
            return battleslogs;
        } else {
            users.offer(user);
            List<String> value;
            String username = user.getCredentials().getUsername();

            while (!log.containsKey(username)) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            value = log.get(username);
            return value;
        }
    }

    private void battle(User user, User otherUser) {
        System.out.println(user.userData.username + " vs. " + otherUser.userData.username);
    }

    @Override
    public List<UserStats> getScoreboard(User identity) {
        return _dataBaseGameDao.getScoreBoard(identity);
    }

    @Override
    public UserStats getStats(User identity) {
        return _dataBaseGameDao.getStats(identity);
    }
}
