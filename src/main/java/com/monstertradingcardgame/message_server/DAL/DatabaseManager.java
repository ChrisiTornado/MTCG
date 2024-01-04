package com.monstertradingcardgame.message_server.DAL;

import com.monstertradingcardgame.server_core.httpserver.config.Configuration;
import com.monstertradingcardgame.server_core.httpserver.config.ConfigurationManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    Configuration conf = ConfigurationManager.getInstance().getCurrentConfiguration();

    public void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection(conf.getUrl(), conf.getDb_user(), conf.getDb_password())) {
            if (conn != null) {
                System.out.println("Connected to the PostgreSQL server.");

                 //Hier löschen wir alle Tabellen
                dropTables(conn);

                // Hier erstellen wir die Tabellen
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
            // Füge weitere DROP TABLE-Statements für andere Tabellen hinzu, falls erforderlich
        }
    }

    private void createTables(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS user_account (" +
                            "id SERIAL PRIMARY KEY," +
                            "username VARCHAR(50) UNIQUE NOT NULL," +
                            "password VARCHAR(100) NOT NULL," +
                            "coins INTEGER DEFAULT 0" +
                            ");"
            );
            // Füge weitere CREATE TABLE-Statements für andere Tabellen hinzu, falls erforderlich
        }
    }
}
