package com.monstertradingcardgame.message_server.API.User;

import com.monstertradingcardgame.message_server.API.IRouteParser;

import java.util.Map;

public class UsernameRouteParser implements IRouteParser {

    @Override
    public boolean IsMatch(String resourcePath, String routePattern) {
        return false;
    }

    @Override
    public Map<String, String> parseParameters(String resourcePath, String routePattern) {
        return null;
    }
}
