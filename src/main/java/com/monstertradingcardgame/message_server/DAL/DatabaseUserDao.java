package com.monstertradingcardgame.message_server.DAL;

import com.monstertradingcardgame.message_server.API.User.UserNotFoundException;
import com.monstertradingcardgame.message_server.Models.User.User;
import com.monstertradingcardgame.message_server.Models.User.UserData;
import com.monstertradingcardgame.server_core.httpserver.config.Configuration;
import com.monstertradingcardgame.server_core.httpserver.config.ConfigurationManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseUserDao implements IUserDao {
    private final String insertUserCommand = "INSERT INTO user_account(username, password, coins) VALUES(?, ?, ?)";
    private final String updateUserCommand = "UPDATE user_account SET username=?, bio=?, image=? WHERE username=?";
    private final String selectUserByCredentialsCommand = "SELECT username, password, coins FROM user_account WHERE username=? AND password=?";
    private final String getAllUsers = "SELECT * FROM user_account";

    private final String getUserData = "SELECT username, bio, image FROM user_account WHERE username=?";
    // private NpgsqlConnection connection;

    Configuration conf = ConfigurationManager.getInstance().getCurrentConfiguration();

    @Override
    public User getUserByAuthToken(String authToken) {
        List<User> userList = getAllUsers();

        for (User user : userList) {
            if (user.getToken().equals(authToken)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public User getUserByCredentials(String username, String password) {
        try (Connection connection = DriverManager.getConnection(conf.getUrl(), conf.getDb_user(), conf.getDb_password());
             PreparedStatement preparedStatement = connection.prepareStatement(selectUserByCredentialsCommand)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String fetchedUsername = resultSet.getString("username");
                    String fetchedPassword = resultSet.getString("password");
                    int fetchedCoins = resultSet.getInt("coins");

                    return new User(fetchedUsername, fetchedPassword, fetchedCoins);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public boolean insertUser(User user) {
        try (Connection connection = DriverManager.getConnection(conf.getUrl(), conf.getDb_user(), conf.getDb_password());
             PreparedStatement preparedStatement = connection.prepareStatement(insertUserCommand)) {

            preparedStatement.setString(1, user.getCredentials().getUsername());
            preparedStatement.setString(2, user.getCredentials().getPassword());
            preparedStatement.setInt(3, user.getCoins());

            preparedStatement.executeUpdate();
            //insertStats(user); // Assuming you have a method to insert stats
            return true;

        } catch (SQLException e) {
            // Handle SQLException
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void updateUserData(User identity, UserData userdata) {
        try (Connection connection = DriverManager.getConnection(conf.getUrl(), conf.getDb_user(), conf.getDb_password());
             PreparedStatement preparedStatement = connection.prepareStatement(getAllUsers)) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (!resultSet.next()) {
                    throw new UserNotFoundException();
                }
            } catch (UserNotFoundException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (Connection connection = DriverManager.getConnection(conf.getUrl(), conf.getDb_user(), conf.getDb_password());
             PreparedStatement preparedStatement = connection.prepareStatement(updateUserCommand)) {
                preparedStatement.setString(1, userdata.username);
                preparedStatement.setString(2, userdata.bio);
                preparedStatement.setString(3, userdata.image);
                preparedStatement.setString(4, identity.getCredentials().getUsername());

                preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserData getUserData(User identity) {
        UserData userData = null;
        try (Connection connection = DriverManager.getConnection(conf.getUrl(), conf.getDb_user(), conf.getDb_password());
             PreparedStatement preparedStatement = connection.prepareStatement(getUserData)) {
            preparedStatement.setString(1, identity.getCredentials().username);

            System.out.println(identity.getCredentials().username);
            System.out.println("Executing query: " + getUserData);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (!rs.next()) {
                    throw new UserNotFoundException();
                }

                System.out.println(rs.getString("username"));
                userData = new UserData(rs.getString("username"), rs.getString("bio"), rs.getString("image"));
            } catch (UserNotFoundException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userData;
    }

    private List<User> getAllUsers()
    {
        List<User> users = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(conf.getUrl(), conf.getDb_user(), conf.getDb_password());
             PreparedStatement preparedStatement = connection.prepareStatement(getAllUsers)) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String username = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    int coins = resultSet.getInt("coins");

                    User user = new User(username, password, coins);
                    users.add(user);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
}
