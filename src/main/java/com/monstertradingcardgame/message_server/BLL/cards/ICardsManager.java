package com.monstertradingcardgame.message_server.BLL.cards;

import com.monstertradingcardgame.message_server.Models.Card.Card;
import com.monstertradingcardgame.message_server.Models.User.User;

import java.util.List;
import java.util.UUID;

public interface ICardsManager {
    List<Card> getUserCards(User identity);
    List<Card> getUserDeck(User identity);
    boolean configureNewDeck(User identity, UUID[] cardIds);
}
