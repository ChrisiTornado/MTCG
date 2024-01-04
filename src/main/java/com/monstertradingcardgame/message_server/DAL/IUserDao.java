package com.monstertradingcardgame.message_server.DAL;

import com.monstertradingcardgame.message_server.Models.User.User;
import com.monstertradingcardgame.message_server.Models.User.UserData;

public interface IUserDao {
    User getUserByAuthToken(String authToken);
    User getUserByCredentials(String username, String password);
    boolean insertUser(User user);
    void updateUserData(User identity, UserData userdata);
    UserData getUserData(User identity);
}
