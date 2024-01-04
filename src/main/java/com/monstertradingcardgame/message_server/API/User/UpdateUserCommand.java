package com.monstertradingcardgame.message_server.API.User;

import com.monstertradingcardgame.message_server.API.AuthenticatedRouteCommand;
import com.monstertradingcardgame.message_server.BLL.user.IUserManager;
import com.monstertradingcardgame.message_server.Models.User.User;
import com.monstertradingcardgame.message_server.Models.User.UserData;
import com.monstertradingcardgame.server_core.http.HttpResponse;
import com.monstertradingcardgame.server_core.http.HttpStatusCode;

public class UpdateUserCommand extends AuthenticatedRouteCommand {
    private final IUserManager _userManager;
    private final String username;
    private final User identity;
    private final UserData userData;

    public UpdateUserCommand(IUserManager userManager, String username, User identity, UserData userData) {
        super(identity);
        _userManager = userManager;
        this.username = username;
        this.identity = identity;
        this.userData = userData;
    }

    @Override
    public HttpResponse Execute() {
        HttpResponse response;
        if (identity.token != username + "-mtcgToken" && username != "admin") {
            response = new HttpResponse(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
            return response;
        }
        try {
            _userManager.updateUser(identity, userData);
            response = new HttpResponse(HttpStatusCode.SUCCESS_200_OK);
        } catch (RuntimeException e) {
            response = new HttpResponse(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
        }
        return response;
    }
}
