package serverapp.managedb;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.*;
import serverapp.managedb.addrecord.AddDialog;
import serverapp.managedb.delete.DeleteDialog;
import serverapp.managedb.search.SearchDialog;
import serverapp.viewcomponents.FormManipulator;
import serverapp.viewcomponents.TableComponent;
import serverapp.viewcomponents.VerifyWordListener;
import serverapp.xml.open.OpenAdapter;
import serverapp.xml.save.SaveAdapter;

import java.util.List;

public class DataBaseManageDialog {

    private Shell shell;
    private TableComponent tableComponent;
    private Combo tablesCombo ;
    private Controller controller;

    public DataBaseManageDialog(Shell parent, ContentController controller) {
        this.controller = new Controller(parent, null, controller);
        shell = createShell(parent);
        shell.pack();
        shell.open();
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
                controller.askChangeTable(value);
                tableComponent.setPage();

            }
        });

        Button deleteTableButton = FormManipulator.createButton(shell, "Delete Table");
        deleteTableButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                if(controller.getCurrentTableName()!= null) {
                    MessageBox confirm = new MessageBox(shell, SWT.ICON_QUESTION | SWT.OK |SWT.CANCEL);
                    confirm.setText("delete Table");
                    confirm.setMessage
                            ("Do you really want delete "+ controller.getCurrentTableName() + " table???");
                    int code = confirm.open();
                    if(code == SWT.OK){
                        controller.askDeleteTable(controller.getCurrentTableName());
                        updateCombo();
                    }
                }else {
                    MessageBox box = new MessageBox(shell);
                    box.setText("No selected table");
                    box.open();
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
                        if(controller.getContentController().getAllTablesNames().contains(tableName)){
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
                if(controller.getCurrentTableName()!= null) {
                    new AddDialog(shell, controller);
                }else {
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
                if(controller.getCurrentTableName()!= null) {
                    new SearchDialog(shell, controller);
                }else {
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
                if(controller.getCurrentTableName()!= null) {
                    new DeleteDialog(shell, controller);
                }else {
                    MessageBox box = new MessageBox(shell);
                    box.setText("No selected table");
                    box.open();
                }
            }
        });
        Button openXMLButton = FormManipulator.createButton(shell, "Open from XML");
        openXMLButton.addSelectionListener(new OpenAdapter(controller.getContentController()));

        Button saveXMLButton = FormManipulator.createButton(shell, "Sve to XML");
        saveXMLButton.addSelectionListener(new SaveAdapter(controller.getCurrentStudArray()));
        return shell;


    }

    private void updateCombo() {
        tablesCombo.removeAll();
        List<String> tablesNames = controller.askAllTablesNames();
        if (!tablesNames.isEmpty()) {
            tablesNames.forEach(tableName -> tablesCombo.add(tableName));
        }
    }

}
