package com.monstertradingcardgame.message_server.API;

import com.monstertradingcardgame.server_core.http.HttpResponse;

public interface IRouteCommand {
    HttpResponse Execute();
}
