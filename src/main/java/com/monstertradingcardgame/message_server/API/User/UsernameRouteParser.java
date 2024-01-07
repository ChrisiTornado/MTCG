package com.monstertradingcardgame.message_server.API.User;

import com.monstertradingcardgame.message_server.API.IRouteParser;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UsernameRouteParser implements IRouteParser {

    @Override
    public boolean isMatch(String resourcePath, String routePattern) {
        String pattern = "^" + routePattern.replace("{username}", ".*").replace("/", "\\/") + "(\\?.*)?$";
        return Pattern.matches(pattern, resourcePath);
    }

    @Override
    public Map<String, String> parseParameters(String resourcePath, String routePattern) {
        // Query-Parameter verarbeiten
        Map<String, String> parameters = parseQueryParameters(resourcePath);

        // ID-Parameter (Benutzername) extrahieren
        String username = parseUsernameParameter(resourcePath, routePattern);
        if (username != null) {
            parameters.put("username", username);
        }

        return parameters;
    }

    private String parseUsernameParameter(String resourcePath, String routePattern) {
        String pattern = "^" + routePattern.replace("{username}", "(?<username>[^\\?\\/]*)").replace("/", "\\/") + "$";
        Matcher matcher = Pattern.compile(pattern).matcher(resourcePath);
        if (matcher.find()) {
            return matcher.group("username");
        }
        return null;
    }

    private Map<String, String> parseQueryParameters(String route) {
        Map<String, String> parameters = new HashMap<>();

        String[] splitRoute = route.split("\\?", -1);
        if (splitRoute.length > 1) {
            String query = splitRoute[1];
            String[] keyValues = query.split("&");

            for (String keyValue : keyValues) {
                String[] keyValueSplit = keyValue.split("=");
                if (keyValueSplit.length == 2) {
                    parameters.put(keyValueSplit[0], keyValueSplit[1]);
                }
            }
        }

        return parameters;
    }
}
