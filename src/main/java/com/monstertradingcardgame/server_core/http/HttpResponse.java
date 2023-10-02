package com.monstertradingcardgame.server_core.http;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class HttpResponse {
    private HttpStatusCode statusCode;
    private String contentType;
    private String content;

    public HttpResponse(HttpStatusCode statusCode, String contentType, String content) {
        this.statusCode = statusCode;
        this.contentType = contentType;
        this.content = content;
    }

    public String getStatusCode() {

        String localDatetime = DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now(ZoneId.of("UTC")));
        return "HTTP/1.1 " + this.statusCode.STATUS_CODE + " " + this.statusCode.MESSAGE + "\r\n" +
                "Cache-Control: max-age=0\r\n" +
                "Connection: close\r\n" +
                "Date: " + localDatetime + "\r\n" +
                "Expires: " + localDatetime + "\r\n" +
                "Content-Type: " + this.contentType + "\r\n" +
                "Content-Length: " + this.content.length() + "\r\n" +
                "\r\n" +
                this.content;
    }
}
