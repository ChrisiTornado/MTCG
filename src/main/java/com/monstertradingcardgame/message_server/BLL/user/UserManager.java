package com.monstertradingcardgame.message_server.BLL.user;

import com.monstertradingcardgame.message_server.DAL.IUserDao;
import com.monstertradingcardgame.message_server.Models.User.Credentials;
import com.monstertradingcardgame.message_server.Models.User.User;
import com.monstertradingcardgame.message_server.Models.User.UserData;
import com.monstertradingcardgame.server_core.httpserver.HttpServer;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import com.monstertradingcardgame.server_core.httpserver.config.HttpConfigurationException;

import java.util.concurrent.ExecutionException;

public class UserManager implements IUserManager {

    private IUserDao _userDao;

    public UserManager(IUserDao _userDao) {
        this._userDao = _userDao;
    }

    @Override
    public User loginUser(Credentials credentials) {
        User user = _userDao.getUserByCredentials(credentials.username, credentials.password);
        if (user != null) {
            return user;
        } else {
            // TODO: exception
            throw new RuntimeException("User not found");
        }
    }

    @Override
    public void registerUser(Credentials credentials) {
        User user = new User(credentials.username, credentials.password, 20);
        if (_userDao.insertUser(user) == false)
            throw new RuntimeException("User already exists");
    }

    @Override
    public void updateUser(User identity, UserData userdata) {
        _userDao.updateUserData(identity, userdata);
    }

    @Override
    public UserData getUserData(User identity) {
        return _userDao.getUserData(identity);
    }

    @Override
    public User getUserByAuthToken(String authToken) {
        User user = _userDao.getUserByAuthToken(authToken);
        if (user != null) {
            return user;
        } else {
            return null;
        }
    }
}
