package com.monstertradingcardgame.server_core.httpserver.core;

import com.monstertradingcardgame.server_core.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class HttpConnectionWorkerThread extends Thread {
    private final static Logger LOGGER = LoggerFactory.getLogger(HttpConnectionWorkerThread.class);
    private Socket socket;
    private HttpParser httpParser = new HttpParser();

    public HttpConnectionWorkerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        HttpRequest request = null;

        try {
            inputStream = socket.getInputStream();
            request = httpParser.parseHttpRequest(inputStream);


            HttpResponse response = new HttpResponse(HttpStatusCode.SUCCESS_200_OK, request.getBody());


            byte[] responseBytes = response.buildResponse().getBytes();

            outputStream = socket.getOutputStream();
            outputStream.write(responseBytes);
            outputStream.flush();
            LOGGER.info(" * Connection Processing Finished.");
        } catch (IOException | HttpParsingException e) {
            LOGGER.error("Problem with communication", e);
        } finally {
            if (inputStream!= null) {
                try {
                    inputStream.close();
                } catch (IOException e) {}
            }
            if (outputStream!=null) {
                try {
                    outputStream.close();
                } catch (IOException e) {}
            }
            if (socket!= null) {
                try {
                    socket.close();
                } catch (IOException e) {}
            }
        }
    }
}
