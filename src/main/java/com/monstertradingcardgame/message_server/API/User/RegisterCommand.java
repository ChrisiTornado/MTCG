package com.monstertradingcardgame.message_server.API.User;

import ch.qos.logback.classic.turbo.DuplicateMessageFilter;
import com.monstertradingcardgame.message_server.API.IRouteCommand;
import com.monstertradingcardgame.message_server.BLL.user.IUserManager;
import com.monstertradingcardgame.message_server.Models.User.Credentials;
import com.monstertradingcardgame.server_core.http.HttpRequest;
import com.monstertradingcardgame.server_core.http.HttpResponse;
import com.monstertradingcardgame.server_core.http.HttpStatusCode;

public class RegisterCommand implements IRouteCommand {
    private final Credentials _credentials;
    private final IUserManager _userManager;

    public RegisterCommand(IUserManager _userManager, Credentials _credentials) {
        this._userManager = _userManager;
        this._credentials = _credentials;
    }

    @Override
    public HttpResponse Execute() {
        HttpResponse response;
        try {
            _userManager.RegisterUser(_credentials);
            response = new HttpResponse(HttpStatusCode.SUCCESS_201_CREATED);
        } catch (RuntimeException e) {
            response = new HttpResponse(HttpStatusCode.CLIENT_ERROR_409_DUPLICATED_USER);
        }
        return response;
    }
}