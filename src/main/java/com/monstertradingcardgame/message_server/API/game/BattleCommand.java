package com.monstertradingcardgame.message_server.API.game;

import com.monstertradingcardgame.message_server.API.AuthenticatedRouteCommand;
import com.monstertradingcardgame.message_server.BLL.game.IGameManager;
import com.monstertradingcardgame.message_server.Models.User.User;
import com.monstertradingcardgame.server_core.http.HttpResponse;
import com.monstertradingcardgame.server_core.http.HttpStatusCode;

import java.util.List;

public class BattleCommand extends AuthenticatedRouteCommand {
    private User user;
    private IGameManager _gameManager;

    public BattleCommand(User user, IGameManager gameManager) {
        super(user);
        this.user = user;
        _gameManager = gameManager;
    }
    @Override
    public HttpResponse execute() {
        List<String> log = _gameManager.getInToBattle(user);
        HttpResponse response = new HttpResponse(HttpStatusCode.SUCCESS_200_OK);
        response.setContent(String.join(" ", log));
        return response;
    }
}
