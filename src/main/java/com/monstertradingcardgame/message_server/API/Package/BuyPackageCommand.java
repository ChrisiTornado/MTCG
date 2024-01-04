package com.monstertradingcardgame.message_server.API.Package;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.monstertradingcardgame.message_server.API.AuthenticatedRouteCommand;
import com.monstertradingcardgame.message_server.BLL.Package.IPackageManager;
import com.monstertradingcardgame.message_server.Models.Card.Card;
import com.monstertradingcardgame.message_server.Models.User.User;
import com.monstertradingcardgame.server_core.http.HttpResponse;
import com.monstertradingcardgame.server_core.http.HttpStatusCode;
import com.monstertradingcardgame.server_core.httpserver.util.Json;

import java.sql.SQLException;
import java.util.List;

import static javax.swing.UIManager.put;


public class BuyPackageCommand extends AuthenticatedRouteCommand {
    private final IPackageManager _packageManager;
    private final User identity;

    public BuyPackageCommand(IPackageManager packageManager, User identity) {
        super(identity);
        _packageManager = packageManager;
        this.identity = identity;
    }

    @Override
    public HttpResponse Execute() {
        HttpResponse response;
        if (identity.coins < 5) {
            response = new HttpResponse(HttpStatusCode.CLIENT_ERROR_403_FORBIDDEN);
            return response;
        }

        try {
            List<Card> cards = _packageManager.acquireNewPackage(identity);
            JsonNode jsonNode = Json.toJson(cards.stream()
                    .map(card -> new Object() {{
                        put("id", card.id);
                        put("name", card.name);
                        put("damage", card.damage);
                        put("element", card.elementType);
                    }})
                    .toArray());

            String jsonContent = Json.stringify(jsonNode);
            response = new HttpResponse(HttpStatusCode.SUCCESS_200_OK);
            response.setContent(jsonContent);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (NoPackageAvailableException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return response;
    }
}
