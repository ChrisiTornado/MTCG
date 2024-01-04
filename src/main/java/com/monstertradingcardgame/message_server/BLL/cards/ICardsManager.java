package com.monstertradingcardgame.message_server.BLL.cards;

import com.monstertradingcardgame.message_server.API.Card.CardNotOwnedOrUnavailableException;
import com.monstertradingcardgame.message_server.API.Card.NoCardsException;
import com.monstertradingcardgame.message_server.API.Card.NotEnoughCardsInDeckException;
import com.monstertradingcardgame.message_server.Models.Card.Card;
import com.monstertradingcardgame.message_server.Models.User.User;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public interface ICardsManager {
    List<Card> getUserCards(User identity) throws NoCardsException, SQLException;
    List<Card> getUserDeck(User identity) throws SQLException;
    boolean configureNewDeck(User identity, UUID[] cardIds) throws SQLException, CardNotOwnedOrUnavailableException, NotEnoughCardsInDeckException;
}
