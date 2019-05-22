import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Group {

    private SimpleStringProperty groupName;
    private SimpleStringProperty groupSize;

    public Group(String groupName,String groupSize){

        this.groupName = new SimpleStringProperty(groupName);
        this.groupSize = new SimpleStringProperty(groupSize);
    }

    public void setGroupName(String groupName){
        this.groupName.set(groupName);
    }

    public String getGroupName(){
        return this.groupName.get();
    }

    public String getGroupSize(){
        return this.groupSize.get();
    }

    public void setGroupSize(String integer){
        this.groupSize.set(integer);
    }
}
