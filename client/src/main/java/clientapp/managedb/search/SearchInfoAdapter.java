package clientapp.managedb.search;

import clientapp.managedb.Controller;
import clientapp.managedb.TypeOfSelection;
import clientapp.entity.Name;
import clientapp.entity.Student;
import clientapp.view.TableComponent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import java.util.List;


public class SearchInfoAdapter extends SelectionAdapter {
    private Group paramsGroup;
    private Combo combo;
    private Controller controller;
    private TableComponent resultTable;

    public SearchInfoAdapter(Group paramsGroup, Combo type, Controller controller, TableComponent resultTable) {
        this.paramsGroup = paramsGroup;
        this.combo = type;
        this.controller = controller;
        this.resultTable = resultTable;
    }

    @Override
    public void widgetSelected(SelectionEvent e) {
        Group group1;
        Control[] children1;
        Group group2;
        Control[] children2 = null;

        group1 = (Group) paramsGroup.getChildren()[0];
        children1 = group1.getChildren();

        if(paramsGroup.getChildren().length > 1) {
            group2 = (Group) paramsGroup.getChildren()[1];
            children2 = group2.getChildren();
        }
        Object firstParam = "";
        Object secondParam = "";

        TypeOfSelection type = TypeOfSelection.getTypeByNumber(combo.getSelectionIndex());
        switch (type){
            case FIO_OR_GROUP:
                Text fName = (Text) children1[1];
                Text lName = (Text) children1[3];
                Text pName = (Text) children1[5];
                firstParam = new Name(fName.getText(), lName.getText(), pName.getText());

                Text groupText = (Text) children2[1];
                if(!groupText.getText().isEmpty()) {
                    secondParam = Integer.parseInt(groupText.getText());
                }
                break;
            case COURSE_OR_PL:
                Text courseText = (Text) children1[1];
                if(!courseText.getText().isEmpty()) {
                    firstParam = Integer.parseInt(courseText.getText());
                }
                Combo progLangCombo = (Combo) children2[1];
                secondParam  = progLangCombo.getText();
                break;
            case NUM_OF_TASKS:
                Text numTaskText = (Text) children1[1];
                if(!numTaskText.getText().isEmpty()) {
                    firstParam = Integer.parseInt(numTaskText.getText());
                }
                Text numDoneTaskText = (Text) children2[1];
                if(!numDoneTaskText.getText().isEmpty()) {
                    secondParam = Integer.parseInt(numDoneTaskText.getText());
                }
                break;
            case NUM_OF_UNDONE_TASKS:
                Text numUndoneTaskText = (Text) children1[1];
                if(!numUndoneTaskText.getText().isEmpty()) {
                    firstParam = Integer.parseInt(numUndoneTaskText.getText());
                }
                break;
        }
        List<Student> foundStudents = controller.askFindStudentByParam(firstParam, secondParam, type);
        Composite parent = paramsGroup.getParent();
        resultTable.clear();
        if(foundStudents.isEmpty()){
            new Label(parent, SWT.NONE).setText("No such students found");
        } else {
            Group tableGroup = new Group(parent, SWT.SHADOW_ETCHED_IN);
            tableGroup.setLayout(new GridLayout());
            GridData tableComponentGridData = new GridData();
            tableComponentGridData.grabExcessHorizontalSpace = true;
            tableComponentGridData.grabExcessVerticalSpace = true;
            tableComponentGridData.horizontalAlignment = GridData.FILL;
            tableComponentGridData.verticalAlignment = GridData.FILL;

            resultTable.addAllStudents(foundStudents);
            resultTable.setLayoutData(tableComponentGridData);
            resultTable.setVisible(true);
        }
    }
}
