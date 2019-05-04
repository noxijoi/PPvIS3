package clientapp.managedb.delete;

import clientapp.Client;
import clientapp.managedb.ComboTypeAdapter;
import clientapp.managedb.Controller;
import clientapp.view.TableComponent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;


public class DeleteDialog {
    private Group deletionParams;
    public DeleteDialog(Shell shell, Controller controller) {
        Shell dialog = new Shell(shell);
        dialog.setText("Delete");
        dialog.setModified(true);
        GridLayout gridLayout = new GridLayout();
        dialog.setLayout(gridLayout);
        Label chooseLbl = new Label(dialog, SWT.NONE);
        chooseLbl.setText("Choose type of deletion");
        Combo comboType = new Combo(dialog, SWT.READ_ONLY);
        String[] types = {"by NAME or GROUP", "by COURSE or PROGRAMMING LANGUAGE",
        "by NUMofTASK or NUMofDONE TASKS","by NUM of UNDONE TASKS"};
        comboType.setItems(types);

        Button deleteButton = new Button(dialog, SWT.PUSH);
        deleteButton.setEnabled(false);

        deletionParams = new Group(dialog, SWT.SHADOW_ETCHED_IN);
        deletionParams.setVisible(false);
        TableComponent resultTable = new TableComponent(dialog, new Controller(controller.getClient(), shell, controller.getCurrentTableName()+"deleted"));
        comboType.addSelectionListener(new ComboTypeAdapter(deletionParams, controller, deleteButton));
        deleteButton. addSelectionListener(new DelInfoAdapter(deletionParams, comboType, controller, resultTable));
        dialog.pack();
        dialog.open();
    }
}
