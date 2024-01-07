package com.monstertradingcardgame.server_core.httpserver;

import com.monstertradingcardgame.message_server.DAL.DatabaseManager;
import com.monstertradingcardgame.server_core.httpserver.config.Configuration;
import com.monstertradingcardgame.server_core.httpserver.config.ConfigurationManager;
import com.monstertradingcardgame.server_core.httpserver.core.ServerListenerThread;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.io.IOException;


public class HttpServer {
    private final static Logger LOGGER = LoggerFactory.getLogger(HttpServer.class);

    public static void main(String[] args) {
        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
        Configuration conf = ConfigurationManager.getInstance().getCurrentConfiguration();

        DatabaseManager dbManager = new DatabaseManager();
        dbManager.initializeDatabase();

        LOGGER.info("Server starting...");

        LOGGER.info("Using Port: " + conf.getPort());
        LOGGER.info("Using WebRoot: " + conf.getWebroot());
        LOGGER.info("DB_User: " + conf.getDb_user());

        try {
            ServerListenerThread serverListenerThread = new ServerListenerThread(conf.getPort(), conf.getWebroot());
            serverListenerThread.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
            // TODO handle later
        }
    }
}