package com.monstertradingcardgame.message_server.DAL;

import com.monstertradingcardgame.message_server.Models.User.User;
import com.monstertradingcardgame.message_server.Models.User.UserStats;
import com.monstertradingcardgame.server_core.httpserver.config.Configuration;
import com.monstertradingcardgame.server_core.httpserver.config.ConfigurationManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBaseGameDao implements IGameDao {
    private String getUser = "SELECT * FROM stats WHERE username=?";
    private String getStats = "SELECT * FROM stats";
    Configuration conf = ConfigurationManager.getInstance().getCurrentConfiguration();

    @Override
    public UserStats getStats(User identity) {
        UserStats userStats = null;
        try (Connection connection = DriverManager.getConnection(conf.getUrl(), conf.getDb_user(), conf.getDb_password());
             PreparedStatement preparedStatement = connection.prepareStatement(getUser)) {
            preparedStatement.setString(1, identity.getCredentials().getUsername());

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }

                String name = rs.getString("username");
                int elo = rs.getInt("elo");
                int wins = rs.getInt("wins");
                int losses = rs.getInt("losses");
                userStats = new UserStats(name, elo, wins, losses);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userStats;
    }

    @Override
    public List<UserStats> getScoreBoard(User identity) {
        List<UserStats> scoreboard = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(conf.getUrl(), conf.getDb_user(), conf.getDb_password());
             PreparedStatement preparedStatement = connection.prepareStatement(getStats)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String name = resultSet.getString("username");
                    int elo = resultSet.getInt("elo");
                    int wins = resultSet.getInt("wins");
                    int losses = resultSet.getInt("losses");

                    UserStats userStats = new UserStats(name, elo, wins, losses);
                    scoreboard.add(userStats);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return scoreboard;
    }

    @Override
    public void updateStats(User user, boolean won) {

    }
}
