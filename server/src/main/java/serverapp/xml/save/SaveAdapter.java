package serverapp.xml.save;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import serverapp.managedb.Controller;


public class SaveAdapter extends SelectionAdapter {
    private Controller controller;
    public SaveAdapter(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void widgetSelected(SelectionEvent e) {
        FileDialog fileDialog = new FileDialog(new Shell(), SWT.SAVE);
        fileDialog.setText("Select file to save");

        fileDialog.setFilterNames(new String[]{"XML files"});
        fileDialog.setFilterExtensions(new String[]{"*.xml"});

        String fn = fileDialog.open();
        if (fn != null) {
            DOMWriter domWriter = new DOMWriter();
            domWriter.writeToFile(fn, controller.getCurrentStudArray());
        }
    }
}
