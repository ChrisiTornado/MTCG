package com.monstertradingcardgame.server_core.http;

public enum HttpMethod {
    HEAD, GET, POST, PUT, DELETE;

    public static final int MAX_LENGTH;

    static {
        int tempMaxLength = -1;
        for (HttpMethod method : values()) {
            if (method.name().length() > tempMaxLength) {
                tempMaxLength = method.name().length();
            }
        }
        MAX_LENGTH = tempMaxLength;
    }
}
