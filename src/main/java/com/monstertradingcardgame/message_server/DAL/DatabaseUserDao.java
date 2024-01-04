package com.monstertradingcardgame.message_server.DAL;

import com.monstertradingcardgame.message_server.Models.User.User;
import com.monstertradingcardgame.message_server.Models.User.UserData;
import com.monstertradingcardgame.server_core.httpserver.config.Configuration;
import com.monstertradingcardgame.server_core.httpserver.config.ConfigurationManager;

import java.sql.*;
import java.util.List;

public class DatabaseUserDao implements IUserDao {
    private final String insertUserCommand = "INSERT INTO user_account(username, password, coins) VALUES(?, ?, ?)";
    private final String updateUserCommand = "UPDATE user_account SET username = ?, password = ? WHERE username = ?";
    private final String selectUserByCredentialsCommand = "SELECT username, password, coins FROM user_account WHERE username=? AND password=?";
    // private NpgsqlConnection connection;

    Configuration conf = ConfigurationManager.getInstance().getCurrentConfiguration();

    @Override
    public User getUserByAuthToken(String authToken) {
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
            // Handle constraint violation (user already exists)
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

            try {
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                // Handle constraint violation (user already exists)
                e.printStackTrace();
                return false;
            }

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

    }

    @Override
    public UserData getUserData(User identity) {
        return null;
    }

//    private List<User> GetAllUsers()
//    {
//        var users = new List<User>();
//        using var cmd = new NpgsqlCommand("SELECT * FROM user_account", connection);
//        using NpgsqlDataReader reader = cmd.ExecuteReader();
//
//        while (reader.Read())
//        {
//            users.Add(new User(Convert.ToString(reader["username"]), Convert.ToString(reader["password"]), (int)reader["coins"]));
//        }
//
//        return users;
//    }
}
