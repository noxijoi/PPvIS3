package serverapp;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import serverapp.communication.cervercommands.Message;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class SocketListener implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger(SocketListener.class);
    private Server server;

    public SocketListener(Server server) {
        this.server = server;
    }

    @Override
    public void run() {
        ServerSocket socket = server.getServerSocket();
        Socket client = null;
        while (true) {
            try {
                client = socket.accept();
                if (server.getWorkingFlag()) {
                    server.addClient(client);
                } else {
                    ObjectOutputStream outputStream = new ObjectOutputStream(client.getOutputStream());
                    Message serverUnreachableMessage = new Message();
                    serverUnreachableMessage.setExecutionResult(false);
                    outputStream.writeObject(serverUnreachableMessage);
                }
            } catch (IOException e) {
                LOGGER.warn("Can't receive client " + e.getMessage());
            }
        }

    }
}
