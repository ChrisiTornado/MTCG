package com.monstertradingcardgame.http;

import com.monstertradingcardgame.message_server.DAL.DatabaseManager;
import com.monstertradingcardgame.server_core.http.HttpParser;
import com.monstertradingcardgame.server_core.httpserver.config.ConfigurationManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CardTest {
    private HttpParser httpParser;
    private DatabaseManager databaseManager = new DatabaseManager();

    @BeforeAll
    public void beforeClass() {
        httpParser = new HttpParser();
    }

    @Test
    public void addPackages() {
        System.out.println("update User fail:");
        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
        databaseManager.initializeDatabase();


    }
}
