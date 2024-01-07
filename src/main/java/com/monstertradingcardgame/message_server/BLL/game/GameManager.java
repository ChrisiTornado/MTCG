package com.monstertradingcardgame.message_server.BLL.game;

import com.monstertradingcardgame.message_server.DAL.DataBaseGameDao;
import com.monstertradingcardgame.message_server.DAL.DatabaseCardDao;
import com.monstertradingcardgame.message_server.Models.User.User;
import com.monstertradingcardgame.message_server.Models.User.UserStats;

import java.util.List;

public class GameManager implements IGameManager{
    DataBaseGameDao _dataBaseGameDao;
    DatabaseCardDao _databaseCardDao;

    public GameManager(DataBaseGameDao dataBaseGameDao, DatabaseCardDao databaseCardDao) {
        this._dataBaseGameDao = dataBaseGameDao;
        this._databaseCardDao = databaseCardDao;
    }

    @Override
    public List<String> getInToBattle(User user) {
        return null;
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
