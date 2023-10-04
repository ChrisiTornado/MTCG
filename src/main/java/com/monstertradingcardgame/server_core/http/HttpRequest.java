package com.monstertradingcardgame.server_core.http;

import java.util.ArrayList;
import java.util.List;

public class HttpRequest {

    private HttpMethod method;
    private String requestTarget;
    private List<String> requestTargetParts;
    private String params;
    private HttpHeader httpHeader =  new HttpHeader();
    private String body;

    HttpRequest() {

    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getRequestTarget() {
        return requestTarget;
    }

    void setMethod(String methodType) throws HttpParsingException {
        for (HttpMethod method: HttpMethod.values()) {
            if (methodType.equals(method.name())) {
                this.method = method;
                return;
            }
        }
        throw new HttpParsingException(
                HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED
        );
    }

    public void setRequestTarget(String requestTarget) throws HttpParsingException {
        if (requestTarget == null || requestTarget.length() == 0) {
            throw new HttpParsingException(HttpStatusCode.SERVER_ERROR_500_SERVER_ERROR);
        }
        this.requestTarget = requestTarget;
        String[] parts = requestTarget.split("/");
        requestTargetParts = new ArrayList<>();
        for (String part : parts) {
            if (part != null && part.length() > 0)
                requestTargetParts.add(part);
        }
    }

    public HttpHeader getHeader() {
        return httpHeader;
    }

    public void setBody(String body) {
        this.body = body;
        System.out.println("Body:" + body);
    }

    public String getBody() {
        return body;
    }
}