package clientapp.managedb;

import clientapp.Client;
import clientapp.managedb.addrecord.AddDialog;
import clientapp.managedb.delete.DeleteDialog;
import clientapp.managedb.search.SearchDialog;
import clientapp.view.FormManipulator;
import clientapp.view.TableComponent;
import clientapp.view.VerifyWordListener;
import lib.communication.cervercommands.Message;
import lib.entity.Student;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.*;

import java.util.List;

public class DataBaseManageDialog {

    private Shell shell;
    private TableComponent tableComponent;
    private Combo tablesCombo;
    private String currentTable;
    private Controller controller;

    public DataBaseManageDialog(Shell parent, Client client) {

        this.controller = new Controller(client, parent, null);
        shell = createShell(parent);
        shell.pack();
        shell.open();
    }

    private void init() {

    }

    private Shell createShell(Shell parent) {
        Shell shell = new Shell(parent);
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
        tablesCombo = new Combo(shell, SWT.DROP_DOWN | SWT.READ_ONLY);

        tablesCombo.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                updateCombo();
            }
        });
        tablesCombo.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                String value =
                        tablesCombo.getItem(tablesCombo.getSelectionIndex());
                Message msg = controller.askChangeTable(value);
                tableComponent.clear();
                tableComponent.setRecordsNum(msg.getRecordsNum());
                tableComponent.addAllStudents((List<Student>) msg.getResultList());

            }
        });

        Button deleteTableButton = FormManipulator.createButton(shell, "Delete Table");
        deleteTableButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                if (controller.getCurrentTableName() != null) {
                    MessageBox confirm = new MessageBox(shell, SWT.ICON_QUESTION | SWT.OK | SWT.CANCEL);
                    confirm.setText("delete Table");
                    confirm.setMessage
                            ("Do you really want delete " + tableComponent.getName() + " table???");
                    int code = confirm.open();
                    if (code == SWT.OK) {
                        controller.askDeleteTable(currentTable);
                    }
                } else {
                    MessageBox box = new MessageBox(shell);
                    box.setText("No selected table");
                    box.open();
                }


            }
        });
        Button addTable = FormManipulator.createButton(shell, "Add Table");
        addTable.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                Shell askNameDialog = new Shell(shell);
                RowLayout rowLayout = new RowLayout();
                askNameDialog.setLayout(rowLayout);
                askNameDialog.setText("New table");
                new Label(askNameDialog, SWT.NONE).setText("Enter Table name:");
                Text text = new Text(askNameDialog, SWT.SINGLE);
                text.addVerifyListener(new VerifyWordListener());
                Button addButton = FormManipulator.createButton(askNameDialog, "Add");
                addButton.addSelectionListener(new SelectionAdapter() {
                    @Override
                    public void widgetSelected(SelectionEvent selectionEvent) {
                        String tableName = text.getText();
                        if (controller.askAllTablesNames().contains(tableName)) {
                            MessageBox warning = new MessageBox(askNameDialog, SWT.ICON_WARNING);
                            warning.setMessage("Table with that name already exists!");
                            warning.open();
                        } else {
                            controller.askCreateStudentsTable(tableName);
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
                if (controller.getCurrentTableName() != null) {
                    new AddDialog(shell, controller);
                } else {
                    MessageBox box = new MessageBox(shell);
                    box.setText("No selected table");
                    box.open();
                }
            }
        });
        Button searchRecordsButton = FormManipulator.createButton(shell, "Search records");
        searchRecordsButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                if (controller.getCurrentTableName() != null) {
                    new SearchDialog(shell, controller);
                } else {
                    MessageBox box = new MessageBox(shell);
                    box.setText("No selected table");
                    box.open();
                }
            }
        });
        Button deleteRecordsButton = FormManipulator.createButton(shell, "Delete records");
        deleteRecordsButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                if (controller.getCurrentTableName() != null) {
                    new DeleteDialog(shell, controller);
                } else {
                    MessageBox box = new MessageBox(shell);
                    box.setText("No selected table");
                    box.open();
                }
            }
        });
        Button updateButton = FormManipulator.createButton(shell, "update table");
        updateButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                tableComponent.update();
            }
        });

        return shell;
    }

    private void updateCombo() {
        tablesCombo.removeAll();
        List<String> tablesNames = controller.askAllTablesNames();
        tablesNames.forEach(name -> tablesCombo.add(name));
    }

    public void updateRecordsNum(int newRecNum) {
        tableComponent.setRecordsNum(newRecNum);
    }
}
