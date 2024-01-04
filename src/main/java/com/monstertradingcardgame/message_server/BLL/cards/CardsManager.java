package com.monstertradingcardgame.message_server.BLL.cards;

import com.monstertradingcardgame.message_server.DAL.DatabaseCardDao;
import com.monstertradingcardgame.message_server.Models.Card.Card;
import com.monstertradingcardgame.message_server.Models.User.User;

import java.util.List;
import java.util.UUID;

public class CardsManager implements ICardsManager {
    private DatabaseCardDao cardDao = new DatabaseCardDao();

    @Override
    public List<Card> getUserCards(User identity) {
        return cardDao.getUserCards(identity.credentials.username);
    }

    @Override
    public List<Card> getUserDeck(User identity) {
        return cardDao.getUserDeck(identity.credentials.username);
    }

    @Override
    public boolean configureNewDeck(User identity, UUID[] cardIds) {
        return cardDao.configureNewDeck(identity, cardIds);
    }
}
