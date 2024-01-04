package com.monstertradingcardgame.message_server.BLL.user;

import com.monstertradingcardgame.message_server.Models.User.Credentials;
import com.monstertradingcardgame.message_server.Models.User.User;
import com.monstertradingcardgame.message_server.Models.User.UserData;

public interface IUserManager {
    User loginUser(Credentials credentials);
    void registerUser(Credentials credentials);
    void updateUser(User identity, UserData userdata);
    UserData getUserData(User identity);
    User getUserByAuthToken(String authToken);
}
