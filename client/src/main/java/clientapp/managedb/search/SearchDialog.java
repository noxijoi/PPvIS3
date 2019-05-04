package clientapp.managedb.search;

import clientapp.Client;
import clientapp.managedb.ComboTypeAdapter;
import clientapp.managedb.Controller;
import clientapp.managedb.TypeOfSelection;
import clientapp.view.FormManipulator;
import clientapp.view.TableComponent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;



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
                new Controller(controller.getClient(), dialog, controller.getCurrentTableName()+"found"));
        resultTable.setVisible(false);
        typeOfSearch.addSelectionListener(new ComboTypeAdapter(searchParams, controller, searchButton));
        searchButton.addSelectionListener(new SearchInfoAdapter(searchParams, typeOfSearch, controller, resultTable));
        dialog.pack();
        dialog.open();
    }
}
