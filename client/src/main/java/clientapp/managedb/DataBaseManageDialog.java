package clientapp.managedb;

import clientapp.Client;
import clientapp.managedb.addrecord.AddDialog;
import clientapp.managedb.delete.DeleteDialog;
import clientapp.managedb.search.SearchDialog;
import clientapp.view.FormManipulator;
import clientapp.view.TableComponent;
import clientapp.view.VerifyWordListener;
import clientapp.xml.save.DOMWriter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import java.util.Arrays;
import java.util.List;

public class DataBaseManageDialog {

    private Shell shell;
    private TableComponent tableComponent;
    private Combo tablesCombo ;
    private String currentTable;
    private Controller controller;

    public DataBaseManageDialog(Shell parent, Client client) {
        List<String> tablesNames = client.askAllTablesNames();
        tablesNames.forEach(tableName -> tablesCombo.add(tableName));
        if(!tablesNames.isEmpty()){
        currentTable = tablesNames.get(0);}

        this.controller = new Controller(client, parent, currentTable);
        shell = createShell(parent);
        shell.pack();
        shell.open();
    }

    private void init() {

    }

    private Shell createShell(Shell parent) {
        Shell shell = new Shell(parent );
        shell.setText("Manage data");
        shell.setRedraw(true);

        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 6;
        gridLayout.makeColumnsEqualWidth = false;

        shell.setLayout(gridLayout);

        GridData tableComponentGridData = new GridData();
        tableComponentGridData.horizontalSpan = 5;
        tableComponentGridData.verticalSpan = 10;
        tableComponentGridData.grabExcessHorizontalSpace = true;
        tableComponentGridData.grabExcessVerticalSpace = true;
        tableComponentGridData.horizontalAlignment = GridData.FILL;
        tableComponentGridData.verticalAlignment = GridData.FILL;
        tableComponent = new TableComponent(shell, controller);
        tableComponent.setLayoutData(tableComponentGridData);

        new Label(shell, SWT.NONE).setText("Select Table");
        tablesCombo = new Combo(shell, SWT.DROP_DOWN |SWT.READ_ONLY );

        tablesCombo.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                String value =
                    tablesCombo.getItem(tablesCombo.getSelectionIndex());
                tableComponent.setRecordsNum(controller.askChangeTable(value));

            }
        });

        Button deleteTableButton = FormManipulator.createButton(shell, "Delete Table");
        deleteTableButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                MessageBox confirm = new MessageBox(shell, SWT.ICON_QUESTION | SWT.OK |SWT.CANCEL);
                confirm.setText("delete Table");
                confirm.setMessage
                        ("Do you really want delete "+ tableComponent.getName() + " table???");
                int code = confirm.open();
                if(code == SWT.YES){
                    controller.askDeleteTable(currentTable);
                    updateCombo();
                }


            }
        });
        Button addTable = FormManipulator.createButton(shell,"Add Table");
        addTable.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                Shell askNameDialog =  new Shell(shell);
                RowLayout rowLayout = new RowLayout();
                askNameDialog.setLayout(rowLayout);
                askNameDialog.setText("New table");
                new Label(askNameDialog, SWT.NONE).setText("Enter Table name:");
                Text text = new Text(askNameDialog, SWT.SINGLE);
                text.addVerifyListener(new VerifyWordListener());
                Button addButton = FormManipulator.createButton(askNameDialog,"Add");
                addButton.addSelectionListener(new SelectionAdapter() {
                    @Override
                    public void widgetSelected(SelectionEvent selectionEvent) {
                        String tableName = text.getText();
                        if(Arrays.asList(controller.askAllTablesNames()).contains(tableName)){
                            MessageBox warning = new MessageBox(askNameDialog, SWT.ICON_WARNING);
                            warning.setMessage("Table with that name already exists!");
                            warning.open();
                        } else {
                            controller.askCreateStudentsTable(tableName);
                            updateCombo();
                        }
                    }
                });
                askNameDialog.pack();
                askNameDialog.open();
            }
        });

        new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);

        Button addRecordButton = FormManipulator.createButton(shell, " Add record");
        addRecordButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                new AddDialog(shell, controller);
            }
        });
        Button searchRecordsButton = FormManipulator.createButton(shell, "Search records");
        searchRecordsButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                new SearchDialog(shell, controller);
            }
        });
        Button deleteRecordsButton = FormManipulator.createButton(shell, "Delete records");
        deleteRecordsButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                new DeleteDialog(shell, controller);
            }
        });

        return shell;
    }

    private void updateCombo() {
        tablesCombo.removeAll();
        List<String> tablesNames = controller.askAllTablesNames();
        tablesNames.forEach(name ->tablesCombo.add(name) );
    }

    public void updateRecordsNum(int newRecNum) {
        tableComponent.setRecordsNum(newRecNum);
    }
}
