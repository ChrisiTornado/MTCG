package com.monstertradingcardgame.message_server.DAL;

import com.monstertradingcardgame.message_server.API.Package.NoPackageAvailableException;
import com.monstertradingcardgame.message_server.Models.Card.Card;
import com.monstertradingcardgame.message_server.Models.User.User;
import com.monstertradingcardgame.server_core.httpserver.config.Configuration;
import com.monstertradingcardgame.server_core.httpserver.config.ConfigurationManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DatabasePackageDao implements IPackageDao {
    private DatabaseCardDao _cardDao = new DatabaseCardDao();
    private final String createNewCardPackage = "INSERT INTO card_package (package_id, card_id) VALUES (?, ?)";
    private final String createNewPackage = "INSERT INTO package DEFAULT VALUES RETURNING package_id";
    private final String queryGetDistinctPackageIds = "SELECT DISTINCT package_id FROM card_package ORDER BY package_id ASC;";
    private final String queryGetCardIds = "SELECT card_id FROM card_package WHERE package_id = ?;";
    private final String queryDeletePackageId = "DELETE FROM card_package WHERE package_id = ?";
    private final String insertCardToUserStack = "UPDATE user_account SET stack = array_cat(stack, ?) WHERE username = ?";
    private final String insertIntoTableCard = "INSERT INTO card VALUES (?, ?, ?, ?)";
    private final String boughtPackage = "UPDATE user_account SET coins = coins - 5 WHERE username = ?";

    Configuration conf = ConfigurationManager.getInstance().getCurrentConfiguration();
    @Override
    public void createPackage(Card[] cards) {
        int packageId;
        try (Connection connection = DriverManager.getConnection(conf.getUrl(), conf.getDb_user(), conf.getDb_password());
             PreparedStatement preparedStatement = connection.prepareStatement(createNewPackage)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    packageId = resultSet.getInt("package_id");
                } else {
                    throw new SQLException("Failed to create a new package");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try (Connection connection = DriverManager.getConnection(conf.getUrl(), conf.getDb_user(), conf.getDb_password());
             PreparedStatement preparedStatement = connection.prepareStatement(createNewCardPackage)) {
            for (int i = 0; i < cards.length; i++) {
                _cardDao.addCard(cards[i]);
                preparedStatement.setInt(1, packageId);
                preparedStatement.setObject(2, cards[i].id);
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
    public List<Card> addPackageToPlayer(User user) throws SQLException, NoPackageAvailableException {
        int packageId = -1;
        List<UUID> cardIds = new ArrayList<>();
        List<Integer> packageIds = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(conf.getUrl(), conf.getDb_user(), conf.getDb_password());
             PreparedStatement preparedStatement = connection.prepareStatement(queryGetDistinctPackageIds)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    packageIds.add(resultSet.getInt("package_id"));
                }
                if (packageIds.isEmpty()) {
                    throw new NoPackageAvailableException();
                }
                packageId = packageIds.get(0);
            }
        }
        try (Connection connection = DriverManager.getConnection(conf.getUrl(), conf.getDb_user(), conf.getDb_password());
             PreparedStatement preparedStatement = connection.prepareStatement(queryGetCardIds)) {
            preparedStatement.setInt(1, packageId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    cardIds.add((UUID) resultSet.getObject("card_id"));
                }
            }
        }
            insertCardToUserStack(user.getCredentials().getUsername(), cardIds.toArray(new UUID[0]));
            deletePackageId(packageId);
            boughtPackage(user);

            DatabaseCardDao cardDao = new DatabaseCardDao();
            List<Card> cards = new ArrayList<>();
            for (UUID cardId : cardIds) {
                cards.add(cardDao.getCard(cardId));
            }
            return cards;
    }

    @Override
    public void deletePackageId(int package_id) {
        try (Connection connection = DriverManager.getConnection(conf.getUrl(), conf.getDb_user(), conf.getDb_password());
             PreparedStatement preparedStatement = connection.prepareStatement(queryDeletePackageId)) {
            preparedStatement.setInt(1, package_id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insertCardToUserStack(String username, UUID[] cardIds) {
        try (Connection connection = DriverManager.getConnection(conf.getUrl(), conf.getDb_user(), conf.getDb_password());
             PreparedStatement preparedStatement = connection.prepareStatement(insertCardToUserStack)) {
            preparedStatement.setObject(1, cardIds);
            preparedStatement.setString(2, username);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insertIntoTableCard(Card card) {
        try (Connection connection = DriverManager.getConnection(conf.getUrl(), conf.getDb_user(), conf.getDb_password());
             PreparedStatement preparedStatement = connection.prepareStatement(insertIntoTableCard)) {
            preparedStatement.setObject(1, card.id);
            preparedStatement.setString(2, card.name.toString());
            preparedStatement.setString(3, card.elementType.toString());
            preparedStatement.setDouble(4, card.damage);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void boughtPackage(User user) {
        try (Connection connection = DriverManager.getConnection(conf.getUrl(), conf.getDb_user(), conf.getDb_password());
             PreparedStatement preparedStatement = connection.prepareStatement(boughtPackage)) {
            preparedStatement.setString(1, user.getCredentials().getUsername());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
