package serverapp.viewcomponents;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.*;
import serverapp.managedb.Controller;

import java.util.List;

public class FormManipulator {
    public static Group createFIOInput(Composite parent){
        Group group = new Group(parent, SWT.SHADOW_ETCHED_IN);
        GridLayout gridLayout = new GridLayout(2, false);
        group.setLayout(gridLayout);
        Label fName = new Label(group, SWT.NONE);
        fName.setText("First Name: ");
        Text fNameText = new Text(group, SWT.BORDER);
        GridData textGridData = new GridData();
        textGridData.widthHint = 200;
        fNameText.addVerifyListener(new VerifyWordListener());
        fNameText.setLayoutData(textGridData);


        Label lName = new Label(group, SWT.NONE);
        lName.setText("Last Name: ");
        Text lNameText = new Text(group, SWT.BORDER);
        lNameText.setLayoutData(textGridData);
        lNameText.addVerifyListener(new VerifyWordListener());

        Label pName = new Label(group, SWT.NONE);
        pName.setText("Patronymic: ");
        Text pNameText = new Text(group, SWT.BORDER);
        pNameText.setLayoutData(textGridData);
        pNameText.addVerifyListener(new VerifyWordListener());
        return group;
    }
    public static Group createComboInputProgLang(Composite parent, Controller controller){
        Group group = new Group(parent, SWT.SHADOW_ETCHED_IN);
        RowLayout rowLayout = new RowLayout();
        group.setLayout(rowLayout);
        new Label(group, SWT.NONE).setText("programing language: " );
        Combo combo = new Combo(group, SWT.DROP_DOWN);
        List<String> progLangs = controller.askAllProgramLang();
        for (String progLang : progLangs) {
            combo.add(progLang);
        }
        return group;
    }
    public static Group createNumericInput(Composite parent, String type){
        Group group = createInput(parent, type);
        Text text = textfieldsFromInputGroup(group)[0];
        text.addVerifyListener(new VerifyNumberListener());
        return group;
    }
    public static Group createWordInput(Composite parent, String type) {
        Group group = createInput(parent, type);
        Text text = textfieldsFromInputGroup(group)[0];
        text.addVerifyListener(new VerifyWordListener());
        return group;
    }

    public static Group createInput(Composite parent, String type) {
        Group group = new Group(parent, SWT.SHADOW_ETCHED_IN);
        group.setLayout(new RowLayout());
        Label label = new Label(group, SWT.NONE);
        label.setText(type + ": ");
        new Text(group, SWT.BORDER);
        return group;
    }
    public static Button createButton(Composite parent, String text){
        Button button = new Button(parent, SWT.PUSH);
        button.setText(text);
        return button;
    }

    public static Table createTable(Composite parent, int rows){
        Color GRAY = new Color(parent.getDisplay(), 189, 189, 189);
        Table table = new Table(parent, SWT.MULTI| SWT.BORDER| SWT.FULL_SELECTION);
        String[] titles = {"ФИО", "Курс", "Группа", "Кол-во работ",
                "Готовых работ", "Язык программирования"};
        table.setHeaderVisible(true);
        //table.setHeaderBackground(GRAY);
        table.setLinesVisible(true);
        for (String title : titles) {
            TableColumn column = new TableColumn(table, SWT.NULL);
            column.setText(title);
        }
        table.getColumn(0).setWidth(400);
        table.getColumn(1).setWidth(80);
        table.getColumn(2).setWidth(80);
        table.getColumn(3).setWidth(120);
        table.getColumn(4).setWidth(120);
        table.getColumn(5).setWidth(200);
        TableItem tableItem = new TableItem(table, SWT.NONE);
        int tableItemSize = tableItem.getBounds().height;
        table.remove(0);
        table.setSize(table.computeSize(0,0).y, tableItemSize * (rows + 1));
        return table;
    }

    public static Text[] textfieldsFromInputGroup(Group group){
        Control[] children = group.getChildren();
        Text[] texts = new Text[children.length / 2];
        for (int i = 0; i < children.length / 2 ; i++) {
            texts[i] = (Text)children[i * 2 + 1];
        }
        return texts;
    }

}

