package com.monstertradingcardgame.message_server.DAL;

import com.monstertradingcardgame.server_core.httpserver.config.Configuration;
import com.monstertradingcardgame.server_core.httpserver.config.ConfigurationManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {

    public void initializeDatabase() {
        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
        Configuration conf = ConfigurationManager.getInstance().getCurrentConfiguration();
        try (Connection conn = DriverManager.getConnection(conf.getUrl(), conf.getDb_user(), conf.getDb_password())) {
            if (conn != null) {
                System.out.println("Connected to the PostgreSQL server.");

                dropTables(conn);

                createTables(conn);
            } else {
                System.out.println("Failed to make connection to the database.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void dropTables(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DROP TABLE IF EXISTS user_account CASCADE;");
            stmt.executeUpdate("DROP TABLE IF EXISTS card CASCADE;");
            stmt.executeUpdate("DROP TABLE IF EXISTS package CASCADE;");
            stmt.executeUpdate("DROP TABLE IF EXISTS card_package CASCADE;");
            stmt.executeUpdate("DROP TABLE IF EXISTS stats CASCADE;");
        }
    }

    private void createTables(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS user_account (" +
                            "id SERIAL PRIMARY KEY," +
                            "username VARCHAR(50) UNIQUE NOT NULL," +
                            "password VARCHAR(100) NOT NULL," +
                            "coins INTEGER DEFAULT 0," +
                            "deck UUID[]," +
                            "stack UUID[]," +
                            "bio TEXT DEFAULT ''," +
                            "image VARCHAR(255) DEFAULT ''," +
                            "elo INTEGER DEFAULT 0," +
                            "wins INTEGER DEFAULT 0," +
                            "losses INTEGER DEFAULT 0" +
                            ");"
            );

            stmt.executeUpdate(
                    "CREATE TABLE card (" +
                            "card_id UUID PRIMARY KEY," +
                            "name VARCHAR(255)," +
                            "element VARCHAR(50)," +
                            "damage INTEGER" +
                            ")"
            );

            stmt.executeUpdate(
                    "CREATE TABLE package (" +
                            "package_id SERIAL PRIMARY KEY" +
                            ")"
            );

            stmt.executeUpdate(
                    "CREATE TABLE card_package (" +
                            "package_id INTEGER REFERENCES package(package_id)," +
                            "card_id UUID REFERENCES card(card_id)," +
                            "PRIMARY KEY (package_id, card_id)" +
                            ")"
            );

            stmt.executeUpdate(
                    "CREATE TABLE stats (" +
                            "username VARCHAR(50) UNIQUE NOT NULL," +
                            "elo INTEGER DEFAULT 0," +
                            "wins INTEGER DEFAULT 0," +
                            "losses INTEGER DEFAULT 0" +
                            ")"
            );
        }
    }
}
