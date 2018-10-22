
package WGU2.model;

import java.time.LocalDate;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class UserTable {
    IntegerProperty userId;
    StringProperty userName;
    StringProperty password;
    IntegerProperty active;
    ObjectProperty<LocalDate> createDate;
    StringProperty createdBy;
    ObjectProperty<LocalDate> modifyDate;
    StringProperty modifyBy;
    
    public static String currentUser;
    public static UserTable selectedUser;
    
    public static ObservableList<UserTable> UserList = FXCollections.observableArrayList();
    
    public UserTable(int userId, String userName, String password, int active, LocalDate createDate, 
            String createdBy, LocalDate modifyDate, String modifyBy) {
        this.userId = new SimpleIntegerProperty(userId);
        this.userName = new SimpleStringProperty(userName);
        this.password = new SimpleStringProperty(password);
        this.active = new SimpleIntegerProperty(active);
        this.createDate = new SimpleObjectProperty(createDate);
        this.createdBy = new SimpleStringProperty(createdBy);
        this.modifyDate = new SimpleObjectProperty(modifyDate);
        this.modifyBy = new SimpleStringProperty(modifyBy);
    }
    
    public static void addNew(int userId, String userName, String password, int active, LocalDate createDate, 
            String createdBy, LocalDate modifyDate, String modifyBy){
        UserList.add(new UserTable(userId, userName, password, active, createDate, createdBy, modifyDate, modifyBy));
    }
    
    public String getUserName() {
        return userName.get();
    }
    
    public StringProperty userNameProperty(){
        return userName;
    }
    
    public StringProperty passwordProperty(){
        return password;
    }
    
}
