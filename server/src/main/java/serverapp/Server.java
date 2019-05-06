package serverapp;

import lib.communication.ContentController;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Display;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Server implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger(Server.class);

    private static ServerSocket serverSocket;
    private List<ClientSocketProcess> allProcess;
    private boolean workingFlag  = false;
    private boolean run = true;
    private ContentController contentController;
    private SocketListener socketListener;
    private LoggingApp app;
    private final int port = 49003;

    public Server(ContentController controller, LoggingApp app) {
        this.app = app;
        this.contentController = controller;
        allProcess = new ArrayList<>();
        try {
            serverSocket = new ServerSocket(port);
            LOGGER.info("server created");
        } catch (IOException e) {
            LOGGER.fatal("cant create server");
            throw new RuntimeException("Cant create server "+ e.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            setWorkingFlag(true);

            LOGGER.info("Server started");
            socketListener =new SocketListener(this);
            Thread socketListenerThread = new Thread(socketListener);
            sendLog("Server initialised");
            socketListenerThread.start();
            while (run) {
                if (workingFlag) {
                    TimeUnit.MILLISECONDS.sleep(20);
                } else {
                    TimeUnit.SECONDS.sleep(1);
                }
            }
        } catch (InterruptedException e) {
            LOGGER.error("Server can't sleep" + e.getMessage());
        } finally {
           shutDown();
           LOGGER.info("Server shut Down");
        }
    }

    void shutDown() {
        try {
            run = false;
            allProcess.forEach(ClientSocketProcess::close);
            socketListener.setRun(false);
            serverSocket.close();
            sendLog("Server shut down");

        } catch (IOException e) {
            LOGGER.error("Cant shut down server "+ e.getMessage());
        }
    }

    public void setWorkingFlag(boolean b) {
        workingFlag = b;
        if (b){
            LOGGER.info("Server resume work");
            sendLog("Server resume work");
        } else{
            LOGGER.info("Server stop work");
            sendLog("Server stop work");
            //TODO как то нужно всем потокам не давать работать
        }
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public boolean getWorkingFlag() {
        return workingFlag;
    }

    public void addClient(Socket client) {
        ClientSocketProcess clientSocketProcess = new ClientSocketProcess(client, app, contentController);
        new Thread(clientSocketProcess).start();
        allProcess.add(clientSocketProcess);
        LOGGER.info("Connected new client " + client.getInetAddress());
        sendLog("Connected new client " + client.getInetAddress());
    }

    public void sendLog(String s){
        new Thread(() -> Display.getDefault().asyncExec(() -> app.log(s))).start();
    }
}
