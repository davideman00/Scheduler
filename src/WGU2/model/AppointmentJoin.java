
package WGU2.model;

import java.time.ZonedDateTime;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class AppointmentJoin {

    IntegerProperty appointmentId;
    IntegerProperty customerId;
    StringProperty customerName;
    IntegerProperty userId;
    StringProperty title;
    StringProperty description;
    StringProperty location;
    StringProperty contact;
    StringProperty url;
    ObjectProperty<ZonedDateTime> start;
    ObjectProperty<ZonedDateTime> end;
    StringProperty createdBy;
    
    public static String addModifySwitch;
    public static String reportSelect;
    
    public static AppointmentJoin selectedAppointment;
    
    public static ObservableList<AppointmentJoin> AppointmentList = FXCollections.observableArrayList();
    public static ObservableList<AppointmentJoin> AppointmentFilterList = FXCollections.observableArrayList();
    public static ObservableList<AppointmentJoin> AppointmentFilterListTemp = FXCollections.observableArrayList();
    
    public AppointmentJoin(int appointmentId, int customerId, String customerName, int userId, String title, String description, String location, String contact, 
            String url, ZonedDateTime start, ZonedDateTime end, String createdBy) {
        this.appointmentId = new SimpleIntegerProperty(appointmentId);
        this.customerId = new SimpleIntegerProperty(customerId);
        this.customerName = new SimpleStringProperty(customerName);
        this.userId = new SimpleIntegerProperty(userId);
        this.title =new SimpleStringProperty(title);
        this.description = new SimpleStringProperty(description);
        this.location = new SimpleStringProperty(location);
        this.contact = new SimpleStringProperty(contact);
        this.url = new SimpleStringProperty(url);
        this.start = new SimpleObjectProperty<>(start);
        this.end = new SimpleObjectProperty<>(end);
        this.createdBy = new SimpleStringProperty(createdBy);
    }
    
    public static void addNew(int appointmentId, int customerId, String customerName, int userId, String title, String description, String location, String contact, 
            String url, ZonedDateTime start, ZonedDateTime end, String createdBy) {
            
            AppointmentList.add(new AppointmentJoin( appointmentId, customerId, customerName, userId, title, description, location, contact, 
            url, start, end, createdBy));
    }
    
    
    public int getAppointmentId() {
        return appointmentId.get();
    }

    public void setAppointmentId(int x) {
        this.appointmentId.set(x);
    }
    
    public IntegerProperty AppointmentIdProperty() {
        return appointmentId;
    }
    
    public int getCustomerId() {
        return customerId.get();
    }

    public void setCustomerId(int x) {
        this.customerId.set(x);
    }
    
    public IntegerProperty CustomerIdProperty() {
        return customerId;
    }
    
    public String getCustomerName() {
        return customerName.get();
    }

    public void setCustomerName(String x) {
        this.customerName.set(x);
    }
    
    public StringProperty CustomerNameProperty(){
        return customerName;
    }
        
    public int getUserId() {
        return userId.get();
    }

    public void setUserId(int x) {
        this.userId.set(x);
    }
    
    public IntegerProperty UserIdProperty(){
        return userId;    
    }
    
    public String getTitle() {
        return title.get();
    }

    public void setTitle(String x) {
        this.title.set(x);
    }
    
    public StringProperty TitleProperty(){
        return title;
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String x) {
        this.description.set(x);
    }
    
    public StringProperty DescriptionProperty(){
        return description;
    }
    
    public String getLocation() {
        return location.get();
    }

    public void setLocation(String x) {
        this.location.set(x);
    }
    
    public StringProperty LocationProperty(){
        return location;
    }
    
    public String getContact() {
        return contact.get();
    }

    public void setContact(String x) {
        this.contact.set(x);
    }
    
    public StringProperty ContactProperty(){
        return contact;
    }
        
    public String getUrl() {
        return url.get();
    }

    public void setUrl(String x) {
        this.url.set(x);
    }
    
    public StringProperty UrlProperty(){
        return url;
    }
    
    public ZonedDateTime getStart() {
        return start.get();
    }

    public void setStart(ZonedDateTime x) {
        this.start.set(x);
    }
    
    public ObjectProperty StartProperty(){
        return start;
    }

    
    public ZonedDateTime getEnd() {
        return end.get();
    }

    public void setEnd(ZonedDateTime x) {
        this.end.set(x);
    }
    
    public ObjectProperty EndProperty(){
        return end;
    }
    
    public String getCreatedBy() {
        return createdBy.get();
    }

    public void setCreatedBy(String x) {
        this.createdBy.set(x);
    }
    
    public StringProperty CreatedByProperty(){
        return createdBy;
    }
    
    
}
