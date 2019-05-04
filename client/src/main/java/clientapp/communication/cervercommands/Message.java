package clientapp.communication.cervercommands;

import java.io.Serializable;
import java.util.List;

public class Message implements Serializable {
    private List<? extends Object> resultList;
    int recordsNum;
    private boolean executionResult = true;

    public Message(){

    }

    public Message(List<? extends Object> resultList, boolean executionResult) {
        this.resultList = resultList;
        this.executionResult = executionResult;
    }

    public void setRecordsNum(int recordsNum){
        this.recordsNum = recordsNum;
    }
    public int getRecordsNum(){
        return recordsNum;
    }

    public List<? extends Object> getResultList() {
        return resultList;
    }

    public void setResultList(List<? extends Object> resultList) {
        this.resultList = resultList;
    }

    public void setExecutionResult(boolean done){
        this.executionResult = done;
    }

    public boolean getExecutionResult() {
        return executionResult;
    }
}
