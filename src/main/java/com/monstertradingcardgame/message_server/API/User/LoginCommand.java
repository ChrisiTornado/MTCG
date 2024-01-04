package com.monstertradingcardgame.message_server.API.User;

import com.monstertradingcardgame.message_server.API.IRouteCommand;
import com.monstertradingcardgame.message_server.BLL.user.IUserManager;
import com.monstertradingcardgame.message_server.Models.User.Credentials;
import com.monstertradingcardgame.message_server.Models.User.User;
import com.monstertradingcardgame.server_core.http.HttpResponse;
import com.monstertradingcardgame.server_core.http.HttpStatusCode;

public class LoginCommand implements IRouteCommand {
    private final IUserManager _userManager;
    private final Credentials _credentials;


    public LoginCommand(IUserManager userManager, Credentials credentials) {
        _userManager = userManager;
        _credentials = credentials;
    }

    @Override
    public HttpResponse Execute() {
        User user;
        try {
            user = _userManager.LoginUser(_credentials);
        } catch (RuntimeException e) {
            user = null;
        }

        HttpResponse response;
        if (user == null)
            response = new HttpResponse(HttpStatusCode.CLIENT_ERROR_401_UNAUTHORIZED);
        else {
            response = new HttpResponse(HttpStatusCode.SUCCESS_200_OK);
            response.setContent(user.token);
        }

        return response;
    }
}
