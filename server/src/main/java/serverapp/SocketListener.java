package serverapp;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class SocketListener implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger(SocketListener.class);
    private Server server;
    private boolean run = true;

    public SocketListener(Server server) {
        this.server = server;
    }

    @Override
    public void run() {
        ServerSocket socket = server.getServerSocket();
        Socket client = null;
        while (run) {
            try {
                client = socket.accept();
                if (server.getWorkingFlag()) {
                    server.addClient(client);
                }
            } catch (IOException e) {
                LOGGER.warn("Can't receive client " + e.getMessage());
            }
        }

    }

    public boolean isRun() {
        return run;
    }

    public void setRun(boolean run) {
        this.run = run;
    }
}
