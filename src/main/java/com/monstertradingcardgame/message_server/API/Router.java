package com.monstertradingcardgame.message_server.API;

import com.monstertradingcardgame.message_server.API.Card.FormatParser;
import com.monstertradingcardgame.message_server.API.Trading.TradingDealParser;
import com.monstertradingcardgame.message_server.API.User.UsernameRouteParser;

public class Router {
    private final IRouteParser _routeParserUsername = new UsernameRouteParser();
    private final IRouteParser _routeParserTradingdealId = new TradingDealParser();
    private final IRouteParser _routeParserFormat = new FormatParser();

    public void Resolve() {

    }
}
