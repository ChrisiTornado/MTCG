package com.monstertradingcardgame.message_server.API;

import java.util.Map;

public interface IRouteParser {
    boolean IsMatch(String resourcePath, String routePattern);
    Map<String, String> ParseParameters(String resourcePath, String routePattern);
}