package com.monstertradingcardgame.message_server.DAL;

import com.monstertradingcardgame.message_server.API.Card.CardNotOwnedOrUnavailableException;
import com.monstertradingcardgame.message_server.API.Card.NoCardsException;
import com.monstertradingcardgame.message_server.API.Card.NotEnoughCardsInDeckException;
import com.monstertradingcardgame.message_server.Models.Card.Card;
import com.monstertradingcardgame.message_server.Models.Card.Card_Type;
import com.monstertradingcardgame.message_server.Models.Card.ElementType;
import com.monstertradingcardgame.message_server.Models.User.User;
import com.monstertradingcardgame.server_core.httpserver.config.Configuration;
import com.monstertradingcardgame.server_core.httpserver.config.ConfigurationManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class DatabaseCardDao implements ICardDao {

    String updateQuery = "UPDATE user_account SET deck = ? WHERE username = ?";
    String getCard = "SELECT * FROM card WHERE card_id = ?";
    String getUserStack = "SELECT stack FROM user_account WHERE username = ?";
    String getUserDeck = "SELECT deck FROM user_account WHERE username = ?";
    Configuration conf = ConfigurationManager.getInstance().getCurrentConfiguration();
    @Override
    public List<Card> getUserCards(String username) throws NoCardsException, SQLException {
        List<Card> cards = new ArrayList<>();

        List<UUID> allCardIds = new ArrayList<>();
        allCardIds.addAll(getUserStack(username));
        for (Card card : getUserDeck(username)) {
            allCardIds.add(card.id);
        }

        if (allCardIds.isEmpty()) {
            throw new NoCardsException();
        }

        for (UUID cardId : allCardIds) {
            cards.add(getCard(cardId));
        }

        return cards;
    }

    @Override
    public Card getCard(UUID cardId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(conf.getUrl(), conf.getDb_user(), conf.getDb_password());
            PreparedStatement preparedStatement = connection.prepareStatement(getCard)) {
            preparedStatement.setObject(1, cardId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (!resultSet.next()) {
                    return null;
                }

                Card_Type name;
                UUID id = (UUID) resultSet.getObject("card_id");
                float damage = resultSet.getFloat("damage");

                String[] parts = resultSet.getString("name").split("_");
                StringBuilder filteredType = new StringBuilder();
                for (String part : parts) {
                    filteredType.append(Character.toUpperCase(part.charAt(0))).append(part.substring(1));
                }

                try {
                    name = Card_Type.valueOf(filteredType.toString());
                } catch (IllegalArgumentException e) {
                    throw new RuntimeException(e);
                }

                ElementType element = ElementType.valueOf(resultSet.getString("element"));

                return new Card(name, damage, id);
            }
        }
    }

    @Override
    public List<Card> getUserDeck(String username) throws SQLException {
        try (Connection connection = DriverManager.getConnection(conf.getUrl(), conf.getDb_user(), conf.getDb_password());
             PreparedStatement preparedStatement = connection.prepareStatement(getUserDeck)) {
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<UUID> deck = new ArrayList<>();
                while (resultSet.next()) {
                    Object deckObj = resultSet.getObject("deck");
                    if (deckObj != null) {
                        deck.addAll(Arrays.asList((UUID[]) deckObj));
                    }
                }

                List<Card> cards = new ArrayList<>();
                for (UUID id : deck) {
                    Card card = getCard(id);
                    if (card != null) {
                        cards.add(card);
                    }
                }
                return cards;
            }
        }
    }

    @Override
    public List<UUID> getUserStack(String username) throws SQLException {
        try (Connection connection = DriverManager.getConnection(conf.getUrl(), conf.getDb_user(), conf.getDb_password());
             PreparedStatement preparedStatement = connection.prepareStatement(getUserStack)) {
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<UUID> stack = new ArrayList<>();
                if (resultSet.next()) {
                    Object stackObj = resultSet.getObject("stack");
                    if (stackObj != null) {
                        stack.addAll(Arrays.asList((UUID[]) stackObj));
                    }
                }
                return stack;
            }
        }
    }

    @Override
    public boolean configureNewDeck(User user, UUID[] cardIds) throws SQLException, NotEnoughCardsInDeckException, CardNotOwnedOrUnavailableException {
        try (Connection connection = DriverManager.getConnection(conf.getUrl(), conf.getDb_user(), conf.getDb_password())) {
            if (cardIds.length < 4) {
                throw new NotEnoughCardsInDeckException();
            }

            List<Card> userCards = getUserCards(user.getCredentials().getUsername());
            if (!userCards.containsAll(Arrays.asList(cardIds))) {
                throw new CardNotOwnedOrUnavailableException();
            }

            try (PreparedStatement stmt = connection.prepareStatement(updateQuery)) {
                stmt.setObject(1, cardIds);
                stmt.setString(2, user.getCredentials().getUsername());
                stmt.executeUpdate();
            }

            user.deck = Arrays.asList(cardIds);
            return true;
        } catch (NoCardsException e) {
            throw new RuntimeException(e);
        }
    }
}
