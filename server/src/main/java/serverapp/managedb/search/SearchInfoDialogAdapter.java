package serverapp.managedb.search;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolItem;
import serverapp.managedb.Controller;


public class SearchInfoDialogAdapter extends SelectionAdapter {
    private Controller controller;
    public SearchInfoDialogAdapter(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void widgetSelected(SelectionEvent e) {
        ToolItem source = (ToolItem) e.getSource();
        Shell shell = source.getParent().getShell();
        new SearchDialog(shell, controller);
    }
}
