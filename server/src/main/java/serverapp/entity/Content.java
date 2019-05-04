package serverapp.entity;

import java.util.ArrayList;
import java.util.List;

public class Content {
    List<StudArray> studArrayList;

    public Content(){
        studArrayList = new ArrayList<>();
        studArrayList.add(new StudArray("New Array"));
    }

    public StudArray getStudArrayByName(String tableName) {
        StudArray result = null;
        for (StudArray studArray : studArrayList) {
            if(studArray.getName().equals(tableName)){
                result = studArray;
            }
        }
        return result;
    }

    public List<StudArray> getStudArrays() {
        return studArrayList;
    }

    public void remove(StudArray studArray) {
        studArrayList.remove(studArray);
    }

    public boolean createNewArr(String tableName) {
        if(getStudArrayByName(tableName)==null){
            StudArray studArray = new StudArray(tableName);
            studArrayList.add(studArray);
            return true;
        }else {
            return false;
        }
    }

    public void addStudArray(StudArray studArray) {
        studArrayList.add(studArray);
    }
}
