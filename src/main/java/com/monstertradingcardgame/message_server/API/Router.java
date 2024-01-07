package com.monstertradingcardgame.message_server.API;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.monstertradingcardgame.message_server.API.Card.ConfigureDeckCommand;
import com.monstertradingcardgame.message_server.API.Card.FormatParser;
import com.monstertradingcardgame.message_server.API.Card.GetCardsCommand;
import com.monstertradingcardgame.message_server.API.Card.GetDeckCommand;
import com.monstertradingcardgame.message_server.API.Package.BuyPackageCommand;
import com.monstertradingcardgame.message_server.API.Package.NewPackageCommand;
import com.monstertradingcardgame.message_server.API.Trading.TradingDealParser;
import com.monstertradingcardgame.message_server.API.User.*;
import com.monstertradingcardgame.message_server.API.game.BattleCommand;
import com.monstertradingcardgame.message_server.API.game.GetScoreboardCommand;
import com.monstertradingcardgame.message_server.API.game.GetStatsCommand;
import com.monstertradingcardgame.message_server.BLL.Package.IPackageManager;
import com.monstertradingcardgame.message_server.BLL.cards.ICardsManager;
import com.monstertradingcardgame.message_server.BLL.game.IGameManager;
import com.monstertradingcardgame.message_server.BLL.user.IUserManager;
import com.monstertradingcardgame.message_server.Models.Card.Card;
import com.monstertradingcardgame.message_server.Models.User.Credentials;
import com.monstertradingcardgame.message_server.Models.User.User;
import com.monstertradingcardgame.message_server.Models.User.UserData;
import com.monstertradingcardgame.server_core.http.HttpRequest;
import com.monstertradingcardgame.server_core.httpserver.util.Json;

import java.util.Map;
import java.util.UUID;

public class Router {
    private final IUserManager _userManager;
    private final ICardsManager _cardsManager;
    private final IPackageManager _packageManager;
    private final IGameManager _gameManager;
    // private final IdentityProvider _identityProvider;
    private final IRouteParser _routeParserUsername = new UsernameRouteParser();
    private final IRouteParser _routeParserTradingdealId = new TradingDealParser();
    private final IRouteParser _routeParserFormat = new FormatParser();
    private final IdentityProvider _identityProvider;

    public Router(IUserManager userManager, ICardsManager cardsManager, IPackageManager packageManager, IGameManager gameManager) {
        _userManager = userManager;
        _cardsManager = cardsManager;
        _packageManager = packageManager;
        _gameManager = gameManager;
        _identityProvider = new IdentityProvider(userManager);
    }

    public IRouteCommand Resolve(HttpRequest request) {
        IRouteCommand command = null;
        JsonNode jsonNode = null;
        User identity = _identityProvider.getIdentityForRequest(request);
        try {
            if (request.getBody() != null)
                jsonNode = Json.parse(request.getBody());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        switch (request.getMethod()) {
            case GET -> {
                switch (request.getRequestTarget()) {
                    case "/cards" -> command = new GetCardsCommand(_cardsManager, identity);
                    case "/stats" -> command = new GetStatsCommand(identity, _gameManager);
                    case "/scoreboard" -> command = new GetScoreboardCommand(identity, _gameManager);
                    case "/tradings" -> {
                        System.out.println("gettradings");
                    }
                    default -> {
                        String r;
                        if (isMatchFormat(request.getRequestTarget())) {
                            command = new GetDeckCommand(identity, _cardsManager, parseFormat(request.getRequestTarget()));
                        } else if (isMatchUsername(request.getRequestTarget()))
                            command = new GetUserCommand(_userManager, parseUsername(request.getRequestTarget()), identity);
                    }
                }
            }
            case POST -> {
                switch(request.getRequestTarget()) {
                    case "/users" -> {
                        try {
                            Credentials credentials = Json.fromJson(jsonNode, Credentials.class);
                            command = new RegisterCommand(_userManager, credentials);
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                    }
                    case "/sessions" -> {
                        try {
                            Credentials credentials = Json.fromJson(jsonNode, Credentials.class);
                            command = new LoginCommand(_userManager, credentials);
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    case "/packages" -> {
                        try {
                            Card[] cards = Json.fromJson(jsonNode, Card[].class);
                            command = new NewPackageCommand(identity, _packageManager, cards);
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    case "/transactions/packages" -> command = new BuyPackageCommand(_packageManager, identity);
                    case "/battles" -> command = new BattleCommand(identity, _gameManager);
                    case "/tradings" -> {
                        if (isMatchTradingdealId(request.getRequestTarget())) {
                            System.out.println("tradings");
                        }
                    }
                }
            }
            case PUT -> {
                switch (request.getRequestTarget()) {
                    case "/deck" -> {
                        try {
                            UUID[] cardIds = Json.fromJson(jsonNode, UUID[].class);
                            command = new ConfigureDeckCommand(_cardsManager, cardIds ,identity);
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    default -> {
                        if (isMatchUsername(request.getRequestTarget())) {
                            try {
                                UserData userData = Json.fromJson(jsonNode, UserData.class);
                                command = new UpdateUserCommand(_userManager, parseUsername(request.getRequestTarget()), identity, userData);
                            } catch (JsonProcessingException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }
            }
            case DELETE -> {
                switch (request.getRequestTarget()) {
                    case "/tradings" -> {
                        if (isMatchTradingdealId(request.getRequestTarget())) {
                            System.out.println("tradings");
                        }
                    }
                }
            }
        }
        return command;
    }

    public boolean isMatchUsername(String path) {
        return _routeParserUsername.isMatch(path, "/users/{username}");
    }

    public boolean isMatchTradingdealId(String path) {
        return _routeParserTradingdealId.isMatch(path, "/tradings/{tradingdealid}");
    }

    public boolean isMatchFormat(String path) {
        return _routeParserFormat.isMatch(path, "/deck");
    }

    public String parseUsername(String path) {
        Map<String, String> parameters = _routeParserUsername.parseParameters(path, "/users/{username}");
        return parameters.get("username");
    }

    public String parseFormat(String path) {
        Map<String, String> dict = _routeParserFormat.parseParameters(path, "/deck");
        if (!dict.containsKey("format")) {
            dict.put("format", "json");
        }
        return dict.get("format");
    }
}
