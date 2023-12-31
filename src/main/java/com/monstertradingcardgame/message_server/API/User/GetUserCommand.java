package com.monstertradingcardgame.message_server.API.User;

import com.monstertradingcardgame.message_server.API.AuthenticatedRouteCommand;
import com.monstertradingcardgame.message_server.BLL.user.IUserManager;
import com.monstertradingcardgame.message_server.Models.User.User;
import com.monstertradingcardgame.message_server.Models.User.UserData;
import com.monstertradingcardgame.server_core.http.HttpResponse;
import com.monstertradingcardgame.server_core.http.HttpStatusCode;

public class GetUserCommand extends AuthenticatedRouteCommand {
    private final IUserManager _userManager;
    private final String username;
    private final User identity;

    public GetUserCommand(IUserManager userManager, String username, User identity) {
        super(identity);
        _userManager = userManager;
        this.username = username;
        this.identity = identity;
    }


    @Override
    public HttpResponse execute() {
        HttpResponse response;
        if (!identity.token.equals(username + "-mtcgToken")) {
            response = new HttpResponse(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
            return response;
        }
        User user;

        user = _userManager.getUserByAuthToken(identity.token);
        if (user == null) {
            response = new HttpResponse(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
            return response;
        }
        UserData userData = _userManager.getUserData(identity);
        if (userData.username.isEmpty()) {
            response = new HttpResponse(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
            response.setContent("UserData is empty");
            return response;
        }
        response = new HttpResponse(HttpStatusCode.SUCCESS_200_OK);
        response.setContent(userData.toString());
        return response;
    }
}
