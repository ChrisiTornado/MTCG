package com.monstertradingcardgame.message_server.DAL;

import com.monstertradingcardgame.message_server.Models.Card.Card;
import com.monstertradingcardgame.message_server.Models.User.User;

import java.util.List;
import java.util.UUID;

public class DatabaseCardDao implements ICardDao {
    @Override
    public List<Card> getUserCards(String username) {
        return null;
    }

    @Override
    public Card getCard(UUID card_id) {
        return null;
    }

    @Override
    public List<Card> getUserDeck(String username) {
        return null;
    }

    @Override
    public List<UUID> getUserStack(String username) {
        return null;
    }

    @Override
    public boolean configureNewDeck(User user, UUID[] cardId) {
        return false;
    }
}
