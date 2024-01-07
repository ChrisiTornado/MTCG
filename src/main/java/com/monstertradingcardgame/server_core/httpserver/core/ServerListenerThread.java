package com.monstertradingcardgame.server_core.httpserver.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerListenerThread extends Thread {

    private final static Logger LOGGER = LoggerFactory.getLogger(ServerListenerThread.class);

    private int port;
    private String webroot;
    private ServerSocket serverSocket;
    private boolean isServerSocketRunning;


    public ServerListenerThread(int port, String webroot) throws IOException {
        this.port = port;
        this.webroot = webroot;
        this.serverSocket = new ServerSocket(this.port);
        isServerSocketRunning = serverSocket.isBound() && !serverSocket.isClosed();
    }

    @Override
    public void run() {
        try {
            while (isServerSocketRunning) {
                Socket socket = serverSocket.accept();
                HttpConnectionWorkerThread workerThread = new HttpConnectionWorkerThread(socket);
                workerThread.start();
            }
        } catch (IOException e) {
            LOGGER.error("Problem with setting socket", e);
        } finally {
            try {
                isServerSocketRunning = false;
                if (serverSocket != null && !serverSocket.isClosed()) {
                    serverSocket.close();
                }
            } catch (IOException e) {
                LOGGER.error("Error while closing the server socket", e);
            }
        }
    }
}