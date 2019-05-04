package serverapp;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import serverapp.communication.cervercommands.Message;
import serverapp.communication.clientcommands.Command;
import serverapp.managedb.ContentController;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientSocketProcess implements Runnable {
    private final static Logger LOGGER = LogManager.getLogger(ClientSocketProcess.class);

    private Socket socket;
    private ServerLogger serverLogger;
    private ContentController controller;

    public ClientSocketProcess(Socket socket, ServerLogger serverLogger) {
        this.socket = socket;
        this.controller = controller;
        this.serverLogger = serverLogger;
        run();
    }

    @Override
    public void run() {
        try {
                final ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                final ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            while (socket.isConnected()) {
                Command command = (Command) inputStream.readObject();
                serverLogger.log(command.getDescription());
                Message message = command.execute(controller);
                outputStream.writeObject(message);
            }
        } catch (IOException e) {
            LOGGER.error("connection error in clientSocket process because " + e.getMessage());
            serverLogger.log("connection error in clientSocket process because " + e.getMessage());
        } catch (ClassNotFoundException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public void close() {
        try {
            socket.close();
            serverLogger.log("socket close" +socket);
        } catch (IOException e) {
            LOGGER.error("Can't close socket " + e.getMessage());
        }
    }
}
