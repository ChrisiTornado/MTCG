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

    public String toString() {
        StringBuilder headerString = new StringBuilder();
        System.out.println("Header:");
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            headerString.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        return headerString.toString();
    }
}
