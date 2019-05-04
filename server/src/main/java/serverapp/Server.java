package serverapp;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class Server extends Thread {
    private static final Logger LOGGER = LogManager.getLogger(Server.class);
    private ServerLogger serverLogger;

    private static ServerSocket serverSocket;
    private List<ClientSocketProcess> allProcess;
    private boolean workingFlag  = false;
    private final String serverProp = "server";

    public Server() {
        allProcess = new ArrayList<>();
    }

    @Override
    public void run() {
        try {
            Properties properties = new Properties();
            setWorkingFlag(true);

            File propFile = new File("D:\\javalessons\\laba3v2\\server\\src\\main\\resources\\server.properties");
            try {
                properties.load(new FileReader(propFile));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Integer port = Integer.parseInt(properties.getProperty("server.port"));
            serverSocket = new ServerSocket(port);
            LOGGER.info("Server started");
            Thread socketListenerThread = new Thread(new SocketListener(this));
            serverLogger.log("Server initialised");
            socketListenerThread.start();
            while (true) {
                if (workingFlag) {
                    TimeUnit.MILLISECONDS.sleep(20);
                } else {
                    TimeUnit.SECONDS.sleep(1);
                }
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            serverLogger.log("Server can't connect");
        } catch (InterruptedException e) {
            LOGGER.error("Server can't sleep" + e.getMessage());
        }
        /*} finally {
           shutDown();
           LOGGER.info("Server shut Down");
        }*/
    }

    private void shutDown() {
        try {
            allProcess.forEach(x -> x.close());
            serverSocket.close();
            serverLogger.log("Server shut down");
        } catch (IOException e) {
            LOGGER.error("Cant shut down server "+ e.getMessage());
        }
    }

    public void setWorkingFlag(boolean b) {
        workingFlag = b;
        if (b){
            LOGGER.info("Server resume work");
            serverLogger.log("Server resume work");
        } else{
            LOGGER.info("Server stop work");
            serverLogger.log("Server stop work");
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
        allProcess.add(new ClientSocketProcess(client, serverLogger));
        LOGGER.info("Connected new client " + client.getInetAddress());
        serverLogger.log("Connected new client " + client.getInetAddress());

    }

    public void setServerLogger(ServerLogger serverLogger) {
        this.serverLogger = serverLogger;
    }
}
