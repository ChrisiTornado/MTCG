package com.monstertradingcardgame.message_server.API.Card;

import com.monstertradingcardgame.message_server.API.AuthenticatedRouteCommand;
import com.monstertradingcardgame.message_server.BLL.cards.ICardsManager;
import com.monstertradingcardgame.message_server.Models.User.User;
import com.monstertradingcardgame.server_core.http.HttpResponse;
import com.monstertradingcardgame.server_core.http.HttpStatusCode;

import java.sql.SQLException;
import java.util.UUID;

public class ConfigureDeckCommand extends AuthenticatedRouteCommand {
    private final ICardsManager _cardsManager;
    private final UUID[] cardIds;

    public ConfigureDeckCommand(ICardsManager cardsManager, UUID[] cardIds, User identity) {
        super(identity);
        _cardsManager = cardsManager;
        this.cardIds = cardIds;
    }

    @Override
    public HttpResponse Execute() {
        HttpResponse response;
        try {
            _cardsManager.configureNewDeck(identity, cardIds);
            response = new HttpResponse(HttpStatusCode.SUCCESS_200_OK);
            return response;
        } catch (NotEnoughCardsInDeckException e) {

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (CardNotOwnedOrUnavailableException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
