package com.monstertradingcardgame.message_server.API.Card;

import com.monstertradingcardgame.message_server.API.IRouteParser;

import java.util.Map;

public class FormatParser implements IRouteParser {
    @Override
    public boolean IsMatch(String resourcePath, String routePattern) {
        return false;
    }

    @Override
    public Map<String, String> ParseParameters(String resourcePath, String routePattern) {
        return null;
    }
}