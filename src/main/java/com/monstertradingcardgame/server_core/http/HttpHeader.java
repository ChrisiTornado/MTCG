package com.monstertradingcardgame.server_core.http;

import java.util.HashMap;
import java.util.Map;

public class HttpHeader {
    public static final String CONTENT_LENGTH_HEADER = "Content-Length";
    private Map<String, String> headers = new HashMap<>();

    public void parseHeaders(String headerLine) {
        final String[] split = headerLine.split(":", 2);

        if (split.length >= 2) {
            headers.put(split[0].trim(), split[1].trim());
            System.out.println("test: " + headers.values());
        } else {
            // TODO: Implement
            System.err.println("Invalid header line: " + headerLine);
        }
    }

    public String getHeader(String headerName) {
        return headers.get(headerName);
    }

    public int getContentLength() {
        final String header = headers.get(CONTENT_LENGTH_HEADER);
        if (header == null) {
            return 0;
        }
        return Integer.parseInt(header);
    }
}
