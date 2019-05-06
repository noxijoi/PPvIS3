package serverapp;

import lib.communication.ContentController;
import lib.communication.cervercommands.Message;
import lib.communication.clientcommands.Command;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Display;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientSocketProcess implements Runnable {
    private final static Logger LOGGER = LogManager.getLogger(ClientSocketProcess.class);

    private Socket socket;
    private LoggingApp app;
    private ContentController controller;

    public ClientSocketProcess(Socket socket, LoggingApp app, ContentController controller) {
        this.socket = socket;
        this.controller = controller;
        this.app = app;
    }

    @Override
    public void run() {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            while (socket.isConnected()) {
                Command command = (Command) inputStream.readObject();
                sendLog(command.getDescription());
                Message message = command.execute(controller);
                outputStream.writeObject(message);
            }
        } catch (IOException e) {
            LOGGER.error("connection error in clientSocket process because " + e.getMessage());
            sendLog("connection error in clientSocket process because " + e.getMessage());
        } catch (ClassNotFoundException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public void close() {
        try {
            socket.close();
            sendLog("socket close" + socket);
        } catch (IOException e) {
            LOGGER.error("Can't close socket " + e.getMessage());
        }
    }
    public void sendLog(String s){
        new Thread(new Runnable() {
            public void run() {
                Display.getDefault().asyncExec(new Runnable() {
                    public void run() {
                        app.log(s);
                    }
                });
            }
        }).start();
    }
}
