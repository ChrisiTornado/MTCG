package com.monstertradingcardgame.message_server.API.Card;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.monstertradingcardgame.message_server.API.AuthenticatedRouteCommand;
import com.monstertradingcardgame.message_server.BLL.cards.ICardsManager;
import com.monstertradingcardgame.message_server.Models.Card.Card;
import com.monstertradingcardgame.message_server.Models.User.User;
import com.monstertradingcardgame.server_core.http.HttpResponse;
import com.monstertradingcardgame.server_core.http.HttpStatusCode;
import com.monstertradingcardgame.server_core.httpserver.util.Json;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import static javax.swing.UIManager.put;

public class GetCardsCommand extends AuthenticatedRouteCommand {
    private final ICardsManager _cardsManager;

    public GetCardsCommand(ICardsManager cardsManager, User identity) {
        super(identity);
        _cardsManager = cardsManager;
    }

    @Override
    public HttpResponse execute() {
        HttpResponse response;
        try {
            List<Card> userCards = _cardsManager.getUserCards(identity);
            response = new HttpResponse(HttpStatusCode.SUCCESS_200_OK);
            List<Card> cardInfos = userCards.stream()
                    .map(card -> new Card(card.id, card.name, card.damage))
                    .collect(Collectors.toList());

            JsonNode jsonNode = Json.toJson(cardInfos);
            String jsonContent = Json.stringify(jsonNode);
            response.setContent(jsonContent);
        } catch (JsonProcessingException | NoCardsException | SQLException e) {
            throw new RuntimeException(e);
        }
        return response;
    }
}
