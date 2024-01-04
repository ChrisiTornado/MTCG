package com.monstertradingcardgame.server_core.httpserver.core;

import com.monstertradingcardgame.message_server.API.IRouteCommand;
import com.monstertradingcardgame.message_server.API.Router;
import com.monstertradingcardgame.message_server.BLL.user.UserManager;
import com.monstertradingcardgame.message_server.DAL.DatabaseUserDao;
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

    private DatabaseUserDao userDao = new DatabaseUserDao();
    private UserManager userManager = new UserManager(userDao);

    private Router router = new Router(userManager);

    public HttpConnectionWorkerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        System.out.println("Test 2 + " + Thread.currentThread());
        InputStream inputStream = null;
        OutputStream outputStream = null;
        HttpRequest request;

        try {
            inputStream = socket.getInputStream();
            request = httpParser.parseHttpRequest(inputStream);
            IRouteCommand command = router.Resolve(request);

            HttpResponse response;

            if (command != null)
                response = command.Execute();
            else
            {
                response = new HttpResponse(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
            }

            System.out.println(response.buildResponse());

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
