package com.monstertradingcardgame.message_server.API.Trading;

import com.monstertradingcardgame.message_server.API.IRouteParser;

import java.util.Map;

public class TradingDealParser implements IRouteParser {
    @Override
    public boolean IsMatch(String resourcePath, String routePattern) {
        return false;
    }

    @Override
    public Map<String, String> ParseParameters(String resourcePath, String routePattern) {
        return null;
    }
}
