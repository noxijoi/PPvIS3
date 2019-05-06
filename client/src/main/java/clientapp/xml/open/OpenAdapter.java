package clientapp.xml.open;


import lib.communication.ContentController;
import lib.entity.StudArray;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class OpenAdapter extends SelectionAdapter {
    private ContentController controller;
    public OpenAdapter(ContentController controller) {
        this.controller = controller;
    }

    @Override
    public void widgetSelected(SelectionEvent e) {
        try {
            String[] names = {"XML-файлы (*.xml)"};
            String[] extensions = {"*.xml"};
            FileDialog fileDialog =new FileDialog(new Shell());
            fileDialog.setText("Open file");
            fileDialog.setFilterNames(names);
            fileDialog.setFilterExtensions(extensions);
            String selected = fileDialog.open();
            if(selected != null){
                ReadParser readParser = new ReadParser();
                StudArray studArray = readParser.parse(selected);
                controller.addStudArray(studArray);
            }
        } catch (ParserConfigurationException | SAXException | IOException e1) {
            e1.printStackTrace();
        }
    }
}
