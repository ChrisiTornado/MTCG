package com.monstertradingcardgame.message_server.API.game;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.monstertradingcardgame.message_server.API.AuthenticatedRouteCommand;
import com.monstertradingcardgame.message_server.BLL.game.IGameManager;
import com.monstertradingcardgame.message_server.Models.User.User;
import com.monstertradingcardgame.message_server.Models.User.UserStats;
import com.monstertradingcardgame.server_core.http.HttpResponse;
import com.monstertradingcardgame.server_core.http.HttpStatusCode;
import com.monstertradingcardgame.server_core.httpserver.util.Json;

public class GetStatsCommand extends AuthenticatedRouteCommand {
    private User identity;
    private IGameManager _gameManager;

    public GetStatsCommand(User identity, IGameManager _gameManager) {
        super(identity);
        this.identity = identity;
        this._gameManager = _gameManager;
    }

    @Override
    public HttpResponse execute() {
        HttpResponse response;
        try {
            UserStats stats = _gameManager.getStats(identity);
            JsonNode jsonNode = Json.toJson(stats);
            response = new HttpResponse(HttpStatusCode.SUCCESS_200_OK);
            response.setContent(Json.stringify(jsonNode));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return response;
    }
}
