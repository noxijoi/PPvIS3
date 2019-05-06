package serverapp.managedb.delete;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import serverapp.managedb.ComboTypeAdapter;
import serverapp.managedb.Controller;
import serverapp.viewcomponents.FormManipulator;
import serverapp.viewcomponents.TableComponent;


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

        Button deleteButton = FormManipulator.createButton(dialog, "Delete");
        deleteButton.setEnabled(false);

        deletionParams = new Group(dialog, SWT.SHADOW_ETCHED_IN);
        deletionParams.setVisible(false);

        controller.askCreateStudentsTable(controller.getCurrentTableName()+"deleted");
        TableComponent resultTable = new TableComponent(dialog,
                new Controller(shell, controller.getCurrentTableName()+"deleted", controller.getContentController()));
        resultTable.setVisible(true);
        comboType.addSelectionListener(new ComboTypeAdapter(deletionParams, controller, deleteButton));
        deleteButton. addSelectionListener(new DelInfoAdapter(deletionParams, comboType, controller, resultTable));
        dialog.addListener(SWT.Close, new Listener() {
            @Override
            public void handleEvent(Event event) {
                controller.askDeleteTable(controller.getCurrentTableName()+"found");
            }
        });
        dialog.pack();
        dialog.open();
    }
}
