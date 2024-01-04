package com.monstertradingcardgame.message_server.API;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.monstertradingcardgame.message_server.API.Card.FormatParser;
import com.monstertradingcardgame.message_server.API.Trading.TradingDealParser;
import com.monstertradingcardgame.message_server.API.User.*;
import com.monstertradingcardgame.message_server.BLL.user.IUserManager;
import com.monstertradingcardgame.message_server.Models.User.Credentials;
import com.monstertradingcardgame.message_server.Models.User.User;
import com.monstertradingcardgame.message_server.Models.User.UserData;
import com.monstertradingcardgame.server_core.http.HttpRequest;
import com.monstertradingcardgame.server_core.httpserver.util.Json;

import java.security.Identity;

public class Router {
    private final IUserManager _userManager;
    // private final IdentityProvider _identityProvider;
    private final IRouteParser _routeParserUsername = new UsernameRouteParser();
    private final IRouteParser _routeParserTradingdealId = new TradingDealParser();
    private final IRouteParser _routeParserFormat = new FormatParser();
    private final IdentityProvider _identityProvider;

    public Router(IUserManager userManager) {
        _userManager = userManager;
        _identityProvider = new IdentityProvider(userManager);
    }

    public IRouteCommand Resolve(HttpRequest request) {
        IRouteCommand command = null;
        JsonNode jsonNode;
        try {
            jsonNode = Json.parse(request.getBody());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        switch (request.getMethod()) {
            case GET -> {
                switch (request.getRequestTarget()) {
                    case "/users" -> {
                        if (isMatchUsername(request.getRequestTarget())) {
                            User identity = _identityProvider.getIdentityForRequest(request);
                            command = new GetCommand(_userManager, parseUsername(request.getRequestTarget()), identity);
                        }
                    }
                    case "/cards" -> {
                        System.out.println("cards");
                    }
                    case "/deck" -> {
                        if (isMatchFormat(request.getRequestTarget())) {

                        }
                    }
                    case "/stats" -> {
                        System.out.println("stats");
                    }
                    case "/score" -> {
                        System.out.println("score");
                    }
                    case "/tradings" -> {
                        System.out.println("gettradings");
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
                        System.out.println("addpackages");
                    }
                    case "/transactions/packages" -> {
                        System.out.println("transactions");
                    }
                    case "/battles" -> {
                        System.out.println("battles");
                    }
                    case "/tradings" -> {
                        if (isMatchTradingdealId(request.getRequestTarget())) {
                            System.out.println("tradings");
                        }
                    }
                }
            }
            case PUT -> {
                switch (request.getRequestTarget()) {
                    case "/users" -> {
                        if (isMatchUsername(request.getRequestTarget())) {
                            User identity = _identityProvider.getIdentityForRequest(request);
                            try {
                                UserData userData = Json.fromJson(jsonNode, UserData.class);
                                command = new UpdateCommand(_userManager, parseUsername(request.getRequestTarget()), identity, userData);
                            } catch (JsonProcessingException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                    case "/deck" -> {

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
        };
        return command;
    }

    public boolean isMatchUsername(String path) {
        return _routeParserUsername.IsMatch(path, "/users/{username}");
    }

    public boolean isMatchTradingdealId(String path) {
        return _routeParserTradingdealId.IsMatch(path, "/tradings/{tradingdealid}");
    }

    public boolean isMatchFormat(String path) {
        return _routeParserFormat.IsMatch(path, "/deck");
    }

    public String parseUsername(String path) {
//        _routeParserUsername.parseParameters(path, "/users/{username}");
        return "";
    }
}
