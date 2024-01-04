package com.monstertradingcardgame.message_server.API.Card;

import com.monstertradingcardgame.message_server.API.IRouteParser;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormatParser implements IRouteParser {
    @Override
    public boolean IsMatch(String resourcePath, String routePattern) {
        String pattern = "^" + routePattern.replace("{format}", ".*").replace("/", "\\/") + "(\\?.*)?$";
        Pattern regexPattern = Pattern.compile(pattern);
        Matcher matcher = regexPattern.matcher(resourcePath);
        return matcher.matches();
    }

    @Override
    public Map<String, String> parseParameters(String resourcePath, String routePattern) {
        // query parameters
        Map<String, String> parameters = parseQueryParameters(resourcePath);

        // id parameter
        String format = parseFormatParameter(resourcePath, routePattern);
        if (format != null) {
            parameters.put("format", format);
        }

        return parameters;
    }

    public static String parseFormatParameter(String resourcePath, String routePattern) {
        String pattern = "^" + routePattern.replace("{format}", "(?<format>[^\\?\\/]*)").replace("/", "\\/") + "$";
        Pattern regexPattern = Pattern.compile(pattern);
        Matcher matcher = regexPattern.matcher(resourcePath);

        if (matcher.find()) {
            return matcher.group("format");
        } else {
            return null;
        }
    }

    public static Map<String, String> parseQueryParameters(String route) {
        Map<String, String> parameters = new HashMap<>();

        String[] splitRoute = route.split("\\?", 2);
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
