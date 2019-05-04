package serverapp.managedb;


import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import serverapp.viewcomponents.FormManipulator;


import static serverapp.managedb.TypeOfSelection.getTypeByNumber;

public class ComboTypeAdapter extends SelectionAdapter {




    private Group paramsGroup;
    private Controller controller;
    private Button actionButton;

    public ComboTypeAdapter(Group paramsGroup, Controller controller, Button actionButton) {
        this.paramsGroup = paramsGroup;
        this.controller = controller;
        this.actionButton = actionButton;
    }

    @Override
    public void widgetSelected(SelectionEvent e) {

        Combo combo = (Combo)e.getSource();
        Composite parent = combo.getParent();
        int index = combo.getSelectionIndex();
        TypeOfSelection type = getTypeByNumber(index);
        RowLayout rowLayout = new RowLayout();
        paramsGroup.setLayout(rowLayout);
        switch (type){
            case FIO_OR_GROUP:
                FormManipulator.createFIOInput(paramsGroup);
                FormManipulator.createNumericInput(paramsGroup,"Group");
                break;
            case COURSE_OR_PL:
                FormManipulator.createNumericInput(paramsGroup, "Course");
                FormManipulator.createComboInputProgLang(paramsGroup, controller);
                break;
            case NUM_OF_TASKS:
                FormManipulator.createNumericInput(paramsGroup, "Number of tasks");
                FormManipulator.createNumericInput(paramsGroup,"Number of done tasks");
                break;
            case NUM_OF_UNDONE_TASKS:
                FormManipulator.createNumericInput(paramsGroup,"Number of undone tasks");
                break;
        }
        combo.setEnabled(false);
        paramsGroup.setVisible(true);
        actionButton.setEnabled(true);
        parent.pack();
    }
}
