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

import static javax.swing.UIManager.put;

public class GetDeckCommand extends AuthenticatedRouteCommand {
    private final ICardsManager _cardsManager;
    private final String format;

    public GetDeckCommand(User identity, ICardsManager cardsManager, String format) {
        super(identity);
        this._cardsManager = cardsManager;
        this.format = format;
    }

    @Override
    public HttpResponse Execute() {
        HttpResponse response;
        if (!("json".equals(format) || "plain".equals(format))) {
            response = new HttpResponse(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
            return response;
        }

        List<Card> cards;
        try {
            cards = _cardsManager.getUserCards(identity);
            if (cards.size() > 0) {
                response = new HttpResponse(HttpStatusCode.SUCCESS_200_OK);
                switch (format) {
                    case "json":
                        JsonNode jsonNode = Json.toJson(cards.stream()
                                .map(card -> new Object() {{
                                    put("id", card.id);
                                    put("name", card.name);
                                    put("damage", card.damage);
                                    put("element", card.elementType);
                                }})
                                .toArray());

                        String jsonContent = Json.stringify(jsonNode);
                        response.setContent(jsonContent);
                        return response;
                    case "plain":
                        StringBuilder messageBuilder = new StringBuilder("Your deck includes:\n");
                        for (Card card : cards) {
                            messageBuilder.append("\t").append(card.toString()).append("\n");
                        }
                        response.setContent(messageBuilder.toString());
                        return response;
                }
            }
        } catch (JsonProcessingException | NoCardsException | SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
