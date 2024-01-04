package com.monstertradingcardgame.message_server.BLL.cards;

import com.monstertradingcardgame.message_server.API.Card.CardNotOwnedOrUnavailableException;
import com.monstertradingcardgame.message_server.API.Card.NoCardsException;
import com.monstertradingcardgame.message_server.API.Card.NotEnoughCardsInDeckException;
import com.monstertradingcardgame.message_server.DAL.DatabaseCardDao;
import com.monstertradingcardgame.message_server.Models.Card.Card;
import com.monstertradingcardgame.message_server.Models.User.User;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class CardsManager implements ICardsManager {
    private DatabaseCardDao cardDao;

    public CardsManager(DatabaseCardDao cardDao) {
        this.cardDao = cardDao;
    }

    @Override
    public List<Card> getUserCards(User identity) throws NoCardsException, SQLException {
        return cardDao.getUserCards(identity.credentials.username);
    }

    @Override
    public List<Card> getUserDeck(User identity) throws SQLException {
        return cardDao.getUserDeck(identity.credentials.username);
    }

    @Override
    public boolean configureNewDeck(User identity, UUID[] cardIds) throws SQLException, CardNotOwnedOrUnavailableException, NotEnoughCardsInDeckException {
        return cardDao.configureNewDeck(identity, cardIds);
    }
}
