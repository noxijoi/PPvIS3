package serverapp;


import lib.communication.ContentController;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import serverapp.managedb.DataBaseManageDialog;
import serverapp.viewcomponents.FormManipulator;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ServerApp implements LoggingApp {
    private static final Logger LOGGER = LogManager.getLogger(ServerApp.class);

    private final int PORT = 49003;
    private Shell shell;
    private Text logArea;
    private Server mainServer;
    private ContentController contentController;


    public ServerApp() {
        Display display = new Display();
        contentController = new ContentController();
        shell = createShell(display);
        shell.pack();
        shell.open();
        shell.addListener(SWT.Close, new Listener() {
            @Override
            public void handleEvent(Event event) {
                mainServer.shutDown();
            }
        });
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch())
                display.sleep();
        }
    }

    private Shell createShell(Display display) {
        Shell shell = new Shell(display);
        shell.setText("Third lab");
        shell.setRedraw(true);

        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 2;
        gridLayout.makeColumnsEqualWidth = true;

        shell.setLayout(gridLayout);
        GridData buttonGridData = new GridData(GridData.HORIZONTAL_ALIGN_CENTER);


        Button runServerButton = FormManipulator.createButton(shell, "Run server");
        runServerButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                    if (mainServer == null) {
                        mainServer =new Server(contentController, ServerApp.this);
                        new Thread(mainServer).start();
                        LOGGER.info("run server");
                        log("Run server");
                    } else {
                        mainServer.setWorkingFlag(true);
                    }

            }
        });
        runServerButton.setLayoutData(buttonGridData);
        Button stopServerButton = FormManipulator.createButton(shell, "Stop Server");
        stopServerButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                mainServer.setWorkingFlag(false);
                LOGGER.info("server stopped");
                log("server stopped");
            }
        });
        stopServerButton.setLayoutData(buttonGridData);

        GridData textAreaGridData = new GridData();
        textAreaGridData.horizontalSpan = 2;
        textAreaGridData.widthHint = 400;
        textAreaGridData.heightHint = 500;
        textAreaGridData.grabExcessHorizontalSpace = true;
        textAreaGridData.grabExcessVerticalSpace = true;
        textAreaGridData.horizontalAlignment = GridData.FILL;
        textAreaGridData.verticalAlignment = GridData.FILL;
        logArea = new Text(shell, SWT.MULTI | SWT.READ_ONLY | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
        logArea.setVisible(true);
        logArea.setBackground(new Color(display, 255, 255, 255));
        logArea.setLayoutData(textAreaGridData);


        Button toDataManageButton = FormManipulator.createButton(shell, "Manage Database");
        toDataManageButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                new DataBaseManageDialog(shell, contentController);
            }
        });
        String serverIP;
        try {
            serverIP = InetAddress.getLocalHost().getHostAddress();
            LOGGER.info("Connected to host " + serverIP);
        } catch (UnknownHostException e) {
            serverIP = "Can't define server IP";
            LOGGER.error("Can't define server IP");
        }
        new Text(shell, SWT.READ_ONLY | SWT.SINGLE).setText("Server IP: " + serverIP);

        new Text(shell, SWT.READ_ONLY | SWT.SINGLE).setText("Port №" + PORT);

        return shell;
    }

    @Override
    public synchronized void log(String message) {
        Date date  = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
        logArea.append(dateFormat.format(date)+ " "+ message + "\n");
    }
}
