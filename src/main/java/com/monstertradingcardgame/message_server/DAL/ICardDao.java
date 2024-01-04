package com.monstertradingcardgame.message_server.DAL;

import com.monstertradingcardgame.message_server.Models.Card.Card;
import com.monstertradingcardgame.message_server.Models.User.User;

import java.util.List;
import java.util.UUID;

public interface ICardDao {
    public List<Card> getUserCards(String username);
    public Card getCard(UUID card_id);
    public List<Card> getUserDeck(String username);
    public List<UUID> getUserStack(String username);
    public boolean configureNewDeck(User user, UUID[] cardId);
}
