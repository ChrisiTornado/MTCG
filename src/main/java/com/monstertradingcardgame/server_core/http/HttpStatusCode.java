package com.monstertradingcardgame.server_core.http;

public enum HttpStatusCode {

    //    CLIENT ERRORS
    CLIENT_ERROR_400_BAD_REQUEST(400, "Bad Request"),
    CLIENT_ERROR_401_METHOD_NOT_ALLOWED(400, "Method not Allowed"),
    CLIENT_ERROR_414_URI_TOO_LONG(414, "URI Too Long"),
    //    SERVER ERRORS

    SERVER_ERROR_500_SERVER_ERROR(500, "Internal Server Error"),
    SERVER_ERROR_501_NOT_IMPLEMENTED(501, "Not Implemented"),
    SERVER_ERROR_505_HTTP_VERSION_NOT_SUPPORTED(505,"HTTP Version not Supported");

    public final int STATUS_CODE;
    public final String MESSAGE;

    HttpStatusCode(int STATUS_CODE, String MESSAGE) {
        this.STATUS_CODE = STATUS_CODE;
        this.MESSAGE = MESSAGE;
    }
}
