package com.monstertradingcardgame.server_core.http;

public class HttpRequest extends HttpMessage {

    private HttpMethod method;
    private String requestTarget;
    private String httpVersion;

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
    }
}