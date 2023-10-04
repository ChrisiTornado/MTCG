package com.monstertradingcardgame.server_core.http;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class HttpResponse {
    private HttpStatusCode statusCode;
    private String contentType;
    private String content;

    final String CRLF = "\r\n";
    private static final String CONTENT_TYPE = "application/json";

    public HttpResponse(HttpStatusCode statusCode, String content) {
        this.statusCode = statusCode;
        this.contentType = CONTENT_TYPE;
        this.content = content;
    }

    public String buildResponse() {
        String localDatetime = DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now(ZoneId.of("UTC")));
        return "HTTP/1.1 " + this.statusCode.STATUS_CODE + " " + this.statusCode.MESSAGE + "\r\n" +
                "Cache-Control: max-age=0" + CRLF +
                "Connection: close" + CRLF +
                "Date: " + localDatetime + CRLF +
                "Expires: " + localDatetime + CRLF +
                "Content-Type: " + this.contentType + CRLF +
    //            "Content-Length: " + this.content.length() + CRLF +
                CRLF +
                this.content;
    }
}
