package com.monstertradingcardgame.message_server.DAL;

import com.monstertradingcardgame.message_server.API.Card.CardNotOwnedOrUnavailableException;
import com.monstertradingcardgame.message_server.API.Card.NoCardsException;
import com.monstertradingcardgame.message_server.API.Card.NotEnoughCardsInDeckException;
import com.monstertradingcardgame.message_server.Models.Card.Card;
import com.monstertradingcardgame.message_server.Models.User.User;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public interface ICardDao {
    public List<Card> getUserCards(String username) throws NoCardsException, SQLException;
    public Card getCard(UUID card_id) throws SQLException;
    public List<Card> getUserDeck(String username) throws SQLException;
    public List<UUID> getUserStack(String username) throws SQLException;
    public boolean configureNewDeck(User user, UUID[] cardId) throws SQLException, NotEnoughCardsInDeckException, CardNotOwnedOrUnavailableException;
}
