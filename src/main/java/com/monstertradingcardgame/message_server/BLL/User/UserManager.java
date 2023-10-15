package com.monstertradingcardgame.message_server.BLL.User;

import com.monstertradingcardgame.message_server.Models.User.Credentials;
import com.monstertradingcardgame.message_server.Models.User.User;
import com.monstertradingcardgame.message_server.Models.User.UserData;

public class UserManager extends IUserManager {
    User LoginUser(Credentials credentials);
    void RegisterUser(Credentials credentials);
    void UpdateUser(User identity, UserData userdata);
    UserData GetUserData(User identity);
    User GetUserByAuthToken(String authToken);
}
