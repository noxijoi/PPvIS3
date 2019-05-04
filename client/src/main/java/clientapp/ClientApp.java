package clientapp;


import clientapp.managedb.DataBaseManageDialog;
import clientapp.view.ServerDataWindow;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.*;

import java.io.IOException;
import java.net.Socket;

public class ClientApp {
    private static final Logger LOGGER =  LogManager.getLogger(ClientApp.class);
    private Client client;
    private Shell shell;
    private String ip;
    private int port;
    private DataBaseManageDialog dbManageDialog;
    private ServerDataWindow serverDataWindow;

    public ClientApp(){
        Display display = new Display();
        shell = new Shell(display);
        serverDataWindow = new ServerDataWindow(this);
        while(!shell.isDisposed()){
            if(!display.readAndDispatch())
                display.sleep();
        }

    }


    public String getIP() {
        return ip;
    }

    public void setIP(String ip){
        this.ip = ip;
    }

    public void init() {
        if(sheckIP()){
            dbManageDialog = new DataBaseManageDialog(shell, client);
            //serverDataWindow.close();
        }
    }

    private boolean sheckIP() {
        boolean hasConnection = false;
        try {
            Socket socket = new Socket(ip, port);
            client = new Client(socket, this);
            hasConnection = true;
            MessageBox msgBox = new MessageBox(shell,SWT.OK);
            msgBox.setMessage("Server connected");
            msgBox.open();
        } catch (IOException e) {
            MessageBox msgBox = new MessageBox(shell, SWT.OK);
            msgBox.setMessage("Server is not available");
            msgBox.open();
            LOGGER.info("No server connection " + e.getMessage());
        }
        return hasConnection;
    }

    public Shell getShell(){
        return shell;
    }
    public void setPort(int port) {
        this.port = port;
    }

    public void updateRecordsNum(int newRecNum) {
        dbManageDialog.updateRecordsNum(newRecNum);
    }
}
