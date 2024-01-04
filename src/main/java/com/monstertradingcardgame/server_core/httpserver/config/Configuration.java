package com.monstertradingcardgame.server_core.httpserver.config;

public class Configuration {
    private int port;
    private String webroot;

    private String url;
    private String db_user;
    private String db_password;

    public String getUrl() {
        return url;
    }

    public String getDb_user() {
        return db_user;
    }

    public String getDb_password() {
        return db_password;
    }

    public int getPort() {
        return port;
    }

    public String getWebroot() {
        return webroot;
    }
}
