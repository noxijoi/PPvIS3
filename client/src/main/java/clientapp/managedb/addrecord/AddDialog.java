package clientapp.managedb.addrecord;

import clientapp.Client;
import clientapp.entity.Name;
import clientapp.entity.Student;
import clientapp.managedb.Controller;
import clientapp.view.FormManipulator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class AddDialog {

    private List<Text> inputFields = new ArrayList<>();


    public AddDialog(Shell parent, Controller controller){
        Shell dialog = new Shell(parent);
        RowLayout rowLayout = new RowLayout(SWT.VERTICAL);
        dialog.setText("Add student");
        dialog.setLayout(rowLayout);

        Group fioGroup = FormManipulator.createFIOInput(dialog);
        Collections.addAll(inputFields, FormManipulator.textfieldsFromInputGroup(fioGroup));


        Group courseGroup = FormManipulator.createNumericInput(dialog, "Course");
        inputFields.add(FormManipulator.textfieldsFromInputGroup(courseGroup)[0]);

        Group groupGroup = FormManipulator.createNumericInput(dialog, "Group");
        inputFields.add(FormManipulator.textfieldsFromInputGroup(groupGroup)[0]);

        Group numberOfTaskGroup = FormManipulator.createNumericInput(dialog, "Number of tasks");
        inputFields.add(FormManipulator.textfieldsFromInputGroup(numberOfTaskGroup)[0]);

        Group numberOfDoneTaskGroup = FormManipulator.createNumericInput(dialog, "Done tasks");
        inputFields.add(FormManipulator.textfieldsFromInputGroup(numberOfDoneTaskGroup)[0]);

        Group programmingLanguageGroup = FormManipulator.createInput(dialog, "Programming language");
        inputFields.add(FormManipulator.textfieldsFromInputGroup(programmingLanguageGroup)[0]);

        Button submit = FormManipulator.createButton(dialog, "Add");
        submit.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                controller.askAddStudent(getStudent());
            }
        });

        dialog.pack();
        dialog.open();
    }


    public Student getStudent() {
        long filledfields = inputFields.stream()
                .filter(x -> !x.getText().isEmpty())
                .count();
        if(filledfields == inputFields.size()) {
            Iterator<Text> fieldsIterator = inputFields.iterator();
            String[] fioStrings = new String[3];
            for (int i = 0; i < 3; i++) {
                fioStrings[i] = fieldsIterator.next().getText();
            }
            Name name = new Name(fioStrings);
            int course = Integer.parseInt(fieldsIterator.next().getText());
            int group = Integer.parseInt(fieldsIterator.next().getText());
            int numOfTasks = Integer.parseInt(fieldsIterator.next().getText());
            int numOfDoneTasks = Integer.parseInt(fieldsIterator.next().getText());
            String progLang = fieldsIterator.next().getText();

            return new Student(name, course, group, numOfTasks, numOfDoneTasks, progLang);
        } else {
            return null;
        }
    }
}
