package serverapp.managedb.search;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import serverapp.managedb.ComboTypeAdapter;
import serverapp.managedb.Controller;
import serverapp.managedb.TypeOfSelection;
import serverapp.viewcomponents.FormManipulator;
import serverapp.viewcomponents.TableComponent;


public class SearchDialog {
    private Group searchParams;
    private TypeOfSelection type;
    public SearchDialog(Shell parent, Controller controller) {
        Shell dialog = new Shell(parent);
        dialog.setText("Search");
        dialog.setModified(true);
        GridLayout gridLayout = new GridLayout();
        dialog.setLayout(gridLayout);
        Label chooseLbl = new Label(dialog, SWT.NONE);
        chooseLbl.setText("Choose type of search");
        Combo typeOfSearch = new Combo(dialog, SWT.READ_ONLY);
        String[] types = {"by NAME or GROUP", "by COURSE or PROGRAMMING LANGUAGE",
                "by NUMofTASK or NUMofDONE TASKS","by NUM of UNDONE TASKS"};
        typeOfSearch.setItems(types);
        Button searchButton = FormManipulator.createButton(dialog, "Search");
        searchButton.setEnabled(false);
        searchParams = new Group(dialog, SWT.SHADOW_ETCHED_IN);
        searchParams.setVisible(false);
        TableComponent resultTable;
        resultTable = new TableComponent(dialog,
                new Controller(dialog, controller.getCurrentTableName()+"found", controller.getContentController()));
        resultTable.setVisible(false);
        typeOfSearch.addSelectionListener(new ComboTypeAdapter(searchParams, controller, searchButton));
        searchButton.addSelectionListener(new SearchInfoAdapter(searchParams, typeOfSearch, controller, resultTable));
        dialog.addListener(SWT.CLOSE, new Listener() {
            @Override
            public void handleEvent(Event event) {
                controller.askDeleteTable(controller.getCurrentTableName()+"found");
            }
        });
        dialog.pack();
        dialog.open();

    }
}
