package com.monstertradingcardgame.message_server.API.game;

import com.fasterxml.jackson.databind.JsonNode;
import com.monstertradingcardgame.message_server.API.AuthenticatedRouteCommand;
import com.monstertradingcardgame.message_server.BLL.game.IGameManager;
import com.monstertradingcardgame.message_server.Models.User.User;
import com.monstertradingcardgame.message_server.Models.User.UserStats;
import com.monstertradingcardgame.server_core.http.HttpResponse;
import com.monstertradingcardgame.server_core.http.HttpStatusCode;
import com.monstertradingcardgame.server_core.httpserver.util.Json;

import java.util.List;
import java.util.stream.Collectors;

public class GetScoreboardCommand extends AuthenticatedRouteCommand {
    private User identity;
    private IGameManager _gameManager;

    public GetScoreboardCommand(User identity, IGameManager gameManager) {
        super(identity);
        this.identity = identity;
        _gameManager = gameManager;
    }


    @Override
    public HttpResponse execute() {
        HttpResponse response;
        try {
            List<UserStats> stats = _gameManager.getScoreboard(identity);
            List<UserStats> statsInfo = stats.stream()
                    .map(stat -> new UserStats(stat.name, stat.elo, stat.wins, stat.losses))
                    .collect(Collectors.toList());

            JsonNode jsonNode = Json.toJson(statsInfo);
            String jsonContent = Json.stringify(jsonNode);
            response = new HttpResponse(HttpStatusCode.SUCCESS_200_OK);
            response.setContent(jsonContent);
        } catch (Exception e) {
            response = new HttpResponse(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
            response.setContent("Bad request");
        }

        return response;
    }
}
