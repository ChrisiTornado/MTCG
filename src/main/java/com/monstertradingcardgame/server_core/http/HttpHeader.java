package com.monstertradingcardgame.server_core.http;

import java.util.HashMap;
import java.util.Map;

public class HttpHeader {
    private Map<String, String> headers = new HashMap<>();

    public void parseHeaders(String headerLine) {
        final String[] split = headerLine.split(":", 2);
        headers.put(split[0].trim(), split[1].trim());
    }

    public String getHeader(String headerName) {
        return headers.get(headerName);
    }
}
