package clientapp.xml.save;


import lib.entity.StudArray;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

public class SaveAdapter extends SelectionAdapter {
    private StudArray studArray;
    public SaveAdapter(StudArray content) {
        this.studArray = content;
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
            domWriter.writeToFile(fn, studArray);
        }
    }
}
