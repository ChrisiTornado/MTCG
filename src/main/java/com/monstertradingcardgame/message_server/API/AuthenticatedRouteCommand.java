package com.monstertradingcardgame.message_server.API;

import com.monstertradingcardgame.message_server.Models.User.User;
import com.monstertradingcardgame.server_core.http.HttpResponse;

public abstract class AuthenticatedRouteCommand implements IRouteCommand {
    public final User identity;

    public AuthenticatedRouteCommand(User identity) {
        this.identity = identity;
    }

    public abstract HttpResponse execute();
}