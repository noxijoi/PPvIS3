package clientapp.view;



import clientapp.managedb.Controller;
import lib.entity.Student;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import java.util.ArrayList;
import java.util.List;


public class TableComponent {
    private String name;
    private Controller controller;
    private Table table;

    private Group group;

    private int currentPage = 0;
    private int recordsPerPage = 10;
    private int totalRecords = 0;

    private Label totalRecordsNum;
    private Label pageIndicatorLabel;

    private List<Student> page = new ArrayList<>();

    public TableComponent(Composite parent, Controller controller){
        this.controller = controller;
        group = new Group(parent, SWT.SHADOW_ETCHED_IN);
        group.setRedraw(true);
        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 5;
        gridLayout.makeColumnsEqualWidth = true;
        group.setLayout(gridLayout);
        table = FormManipulator.createTable(group,recordsPerPage);
        GridData tableGridData = new GridData();
        tableGridData.horizontalSpan = 5;
        tableGridData.grabExcessVerticalSpace = true;
        tableGridData.grabExcessHorizontalSpace = false;
        tableGridData.horizontalAlignment = GridData.FILL;
        tableGridData.verticalAlignment = GridData.FILL;
        tableGridData.heightHint = 300;
        table.setLayoutData(tableGridData);
        table.setRedraw(true);

        GridData buttonsGridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
        Button firstPage = FormManipulator.createButton(group,"<<");
        firstPage.setLayoutData(buttonsGridData);
        firstPage.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                toFirstPage();
            }
        });
        Button prevPage = FormManipulator.createButton(group, "<");
        prevPage.setLayoutData(buttonsGridData);
        prevPage.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                toPrevPage();
            }
        });
        pageIndicatorLabel = new Label(group, SWT.NONE);
        pageIndicatorLabel.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_CENTER));
        pageIndicatorLabel.setText("0 of 0");
        Button nextPage = FormManipulator.createButton(group,">");
        nextPage.setLayoutData(buttonsGridData);
        nextPage.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                toNextPage();
            }
        });
        Button lastPage = FormManipulator.createButton(group,">>");
        lastPage.setLayoutData(buttonsGridData);
        lastPage.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                toLastPage();
            }
        });

        totalRecordsNum = new Label(group, SWT.NONE);
        totalRecordsNum.setText("total number of records : 0");
        GridData totalLabelGridData = new GridData();
        totalLabelGridData.horizontalSpan = 5;
        totalLabelGridData.horizontalAlignment = GridData.FILL;
        totalRecordsNum.setLayoutData(totalLabelGridData);

        Label chooseNumOfLines = new Label(group, SWT.NONE);
        chooseNumOfLines.setText("Fields\n per page:");

        Combo linesChoose = new Combo(group, SWT.READ_ONLY);
        String[] items ={"10", "20", "30"};
        linesChoose.setText("10");
        linesChoose.setVisible(true);
        linesChoose.setItems(items);
        linesChoose.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                Combo combo = (Combo) e.getSource();
                int index = combo.getSelectionIndex();
                int data = Integer.parseInt(combo.getItem(index));
                resize(data);
            }
        });
        group.setVisible(true);
        group.pack();
    }

    public Group getGroup() {
        return group;
    }

    public void addStudent(Student student){
        page.add(student);
        if(table.getItemCount() >= recordsPerPage){
            currentPage++;
        }
        updatePageIndicatorLabel();
        setPage();
        updateTotalRecordsNumLabel();
    }
    public void addAllStudents(List<Student> studentList){
        page.addAll(studentList);
        update();
    }

    public void setPage() {
        table.removeAll();
        page = controller.askPage(currentPage);
        for (Student student : page) {
            TableItem item = new TableItem(table, SWT.NONE);
            item.setText(studentToStringArr(student));
            updateTotalRecordsNumLabel();
        }
    }

    private void updatePageIndicatorLabel() {
        pageIndicatorLabel.setText(currentPage + 1 + " of " + getNumOfPages());
    }

    private int getNumOfPages() {
        totalRecords = controller.askNumOfRecords();
        if(totalRecords % recordsPerPage == 0){
            return totalRecords / recordsPerPage;
        } else {
            return totalRecords / recordsPerPage + 1;
        }
    }

    private void toFirstPage(){
        currentPage = 0;
        update();
    }
    private void toLastPage() {
            currentPage = getNumOfPages() - 1;
            update();
    }
    private void toPrevPage(){
        if(currentPage > 0){
            currentPage--;
            update();
        }

    }
    private void toNextPage(){
        if(currentPage < getNumOfPages() - 1 ){
            currentPage++;
            update();
        }
    }

    public void clear(){
        table.removeAll();
        page.clear();
        totalRecords = 0;
        updatePageIndicatorLabel();
        updateTotalRecordsNumLabel();
    }

    private void updateTotalRecordsNumLabel() {
        totalRecordsNum.setText("total number of records : " + totalRecords);
    }
    private void resize(int numOfRecords){
        recordsPerPage = numOfRecords;
        controller.setCurrentPageSize(numOfRecords);
        update();
        group.setSize(group.computeSize(SWT.DEFAULT, SWT.DEFAULT));
        table.setSize(table.computeSize(SWT.DEFAULT, 300));
        Composite parent = group.getShell();
        parent.setSize(parent.computeSize(SWT.DEFAULT, SWT.DEFAULT));
    }

    private void updateCurrentPageNumber() {
        if(currentPage > getNumOfPages()){
            currentPage = getNumOfPages() - 1;
        }
    }
    private String[] studentToStringArr(Student student){
        return new String[]{student.getName().toString(),
                            student.getCourse().toString(),
                            student.getGroupNumber().toString(),
                            student.getTotalNumOfTask().toString(),
                            student.getNumOfDoneTasks().toString(),
                            student.getProgrammingLanguage()
        };
    }
    public void setLayoutData(GridData tableComponentGridData) {
        group.setLayoutData(tableComponentGridData);
    }

    public void setVisible(boolean b) {
        group.setVisible(b);
    }

    public int getCurrentPageN() {
        return currentPage;
    }

    public int getRecordsPerPage() {
        return  recordsPerPage;
    }

    public void setRecordsNum(int newRecNum) {
        updateTotalRecordsNumLabel(newRecNum);
    }

    private void updateTotalRecordsNumLabel(int newRecNum) {
        totalRecordsNum.setText("total number of records : " + newRecNum);
    }

    public String getName() {
        return name;

    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }

    public void update() {
        setPage();
        updateTotalRecordsNumLabel();
        updateCurrentPageNumber();
    }
}
