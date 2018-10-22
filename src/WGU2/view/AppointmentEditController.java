/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WGU2.view;

import WGU2.model.AppointmentJoin;
import static WGU2.model.AppointmentJoin.AppointmentList;
import WGU2.model.CustomerJoin;
import static WGU2.model.CustomerJoin.CustomerList;
import WGU2.model.DbSync;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author User
 */
public class AppointmentEditController implements Initializable {

    @FXML
    private DatePicker DateField_StartDate;
    @FXML
    private DatePicker DateField_EndDate;
    @FXML
    private ComboBox<String> ComboBox_StartHour;
    @FXML
    private ComboBox<String> ComboBox_StartMinute;
    @FXML
    private ComboBox<String> ComboBox_EndHour;
    @FXML
    private ComboBox<String> ComboBox_EndMinute;
    @FXML
    private ComboBox<CustomerJoin> ComboBox_Customer;
    @FXML
    private ComboBox<String> ComboBox_Description;
    @FXML
    private ComboBox<String> ComboBox_Location;
    @FXML
    private ComboBox<String> ComboBox_StartAMPM;
    @FXML
    private ComboBox<String> ComboBox_EndAMPM;
    @FXML
    private Label Label_addModifyAppointment;
    @FXML
    private TextField TextField_Title;
    @FXML
    private TextField TextField_Contact;
    @FXML
    private TextField TextField_Url;
       

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeComboBox();
        Label_addModifyAppointment.setText(AppointmentJoin.addModifySwitch);
        
        if (AppointmentJoin.selectedAppointment != null){
            TextField_Title.setText(AppointmentJoin.selectedAppointment.getTitle());
            TextField_Contact.setText(AppointmentJoin.selectedAppointment.getContact());
            TextField_Url.setText(AppointmentJoin.selectedAppointment.getUrl());
            ComboBox_Description.setValue(AppointmentJoin.selectedAppointment.getDescription());
            ComboBox_Location.setValue(AppointmentJoin.selectedAppointment.getLocation());
            DateField_StartDate.setValue(AppointmentJoin.selectedAppointment.getStart().toLocalDate());
            String appStartHr = AppointmentJoin.selectedAppointment.getStart().toLocalTime().format(hrFormatter);
            String appStartMin = AppointmentJoin.selectedAppointment.getStart().toLocalTime().format(minFormatter);
            String appStartAmPm = AppointmentJoin.selectedAppointment.getStart().toLocalTime().format(amPMFormatter);
            
            ComboBox_StartHour.setValue(appStartHr);
            ComboBox_StartAMPM.setValue(appStartAmPm);
            ComboBox_StartMinute.setValue(appStartMin);
                  
            String appEndHr = AppointmentJoin.selectedAppointment.getEnd().toLocalTime().format(hrFormatter);
            String appEndMin = AppointmentJoin.selectedAppointment.getEnd().toLocalTime().format(minFormatter);
            String appEndAmPm = AppointmentJoin.selectedAppointment.getEnd().toLocalTime().format(amPMFormatter);
            DateField_EndDate.setValue(AppointmentJoin.selectedAppointment.getEnd().toLocalDate());
            ComboBox_EndHour.setValue(appEndHr);
            ComboBox_EndMinute.setValue(appEndMin);
            ComboBox_EndAMPM.setValue(appEndAmPm);
            
            for (int i = 0; i < CustomerList.size(); i++){
                int currentAppCustomerId = AppointmentJoin.selectedAppointment.getCustomerId();
                int matchCustomerId = CustomerJoin.CustomerList.get(i).getCustomerId();
                
                if (currentAppCustomerId == matchCustomerId ){
                    ComboBox_Customer.setValue(CustomerList.get(i));
                }
            }
        }
        else{
            ComboBox_StartHour.setValue("12");
            ComboBox_StartMinute.setValue("00");
            ComboBox_EndHour.setValue("12");
            ComboBox_EndMinute.setValue("00");
        }
        
    }    

    private final DateTimeFormatter hrFormatter = DateTimeFormatter.ofPattern("hh");
    private final DateTimeFormatter minFormatter = DateTimeFormatter.ofPattern("mm");
    private final DateTimeFormatter amPMFormatter = DateTimeFormatter.ofPattern("a");
    
    @FXML
    private void handlerSave(ActionEvent event) throws Exception{
        
        if(fieldsFilledTest()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Please Fill out all fields!");
            alert.setHeaderText("All fields must be filled out!");
            alert.setContentText("All fields must be filled out!!");
            alert.showAndWait();
                
        }
        else{
            String startAmPm = "";
            String endAmPm = "";
            String tempTitle = "";
            String tempContact = "";
            String tempUrl = "";
            String tempDescription = "";
            String tempLocation = "";
            int tempCustomerId = 0;
            LocalDate tempStartDate = null;
            String tempStartHr = "";
            String tempStartMin = "";
            DateTimeFormatter hrMinFormatter = null;
            LocalTime tempStartTime = null;
            LocalDate tempEndDate = null;
            String tempEndHr = "";
            String tempEndMin = "";
            LocalTime tempEndTime = null;
            ZoneId zid = null;
            LocalDateTime ldtStart = null;
            ZonedDateTime zdtStart = null;
            ZonedDateTime utcStart = null;
            LocalDateTime dbStart = null;
            Timestamp dbTSStart = null;
            String str_dbTSStart = null;
            LocalDateTime ldtEnd = null;
            ZonedDateTime zdtEnd = null;
            ZonedDateTime utcEnd = null;
            LocalDateTime dbEnd = null;
            Timestamp dbTSEnd = null;
            String str_dbTSEnd = "";
            
            try{
            startAmPm = ComboBox_StartAMPM.getValue(); 
            endAmPm = ComboBox_EndAMPM.getValue();
            tempTitle = TextField_Title.getText();
            tempContact = TextField_Contact.getText();
            tempUrl = TextField_Url.getText();
            tempDescription = ComboBox_Description.getValue();
            tempLocation = ComboBox_Location.getValue();
            tempCustomerId = ComboBox_Customer.getValue().getCustomerId();
            tempStartDate = DateField_StartDate.getValue();
            tempStartHr = ComboBox_StartHour.getValue();
            tempStartMin = ComboBox_StartMinute.getValue();
            hrMinFormatter = DateTimeFormatter.ofPattern("hh mm a");
            tempStartTime = LocalTime.parse(tempStartHr + " " + tempStartMin + " " + startAmPm, hrMinFormatter); 
            tempEndDate = DateField_EndDate.getValue();
            tempEndHr = ComboBox_EndHour.getValue();
            tempEndMin = ComboBox_EndMinute.getValue();
            tempEndTime = LocalTime.parse(tempEndHr + " " + tempEndMin + " " + endAmPm, hrMinFormatter); 
            zid = ZoneId.systemDefault();
            
            //convert to LDT/ZDT, then UTC version of ZDT, then timestamp/String
            ldtStart = LocalDateTime.of(tempStartDate, tempStartTime);
            zdtStart = ldtStart.atZone(zid);
            utcStart = zdtStart.withZoneSameInstant(ZoneId.of("UTC"));
            dbStart = utcStart.toLocalDateTime();
            dbTSStart = Timestamp.valueOf(dbStart);
            str_dbTSStart = dbTSStart.toString();
        
            //convert to LDT/ZDT, then UTC version of ZDT, then timestamp/String
            ldtEnd = LocalDateTime.of(tempEndDate, tempEndTime);
            zdtEnd = ldtEnd.atZone(zid);
            utcEnd = zdtEnd.withZoneSameInstant(ZoneId.of("UTC"));
            dbEnd = utcEnd.toLocalDateTime();
            dbTSEnd = Timestamp.valueOf(dbEnd);
            str_dbTSEnd = dbTSEnd.toString();
            }
            catch(Exception e){
                e.printStackTrace();
            }
            
            if(AppointmentJoin.selectedAppointment != null){
                AppointmentList.remove(AppointmentJoin.selectedAppointment);
            }
            
            //check for overlapping appointments 
            for (int i = 0; i < AppointmentList.size(); i++){
                LocalDateTime matchStart = AppointmentList.get(i).getStart().toLocalDateTime();
                LocalDateTime matchEnd = AppointmentList.get(i).getEnd().toLocalDateTime();
                if(ldtStart.isEqual(matchStart) || ldtStart.isAfter(matchStart) && ldtStart.isBefore(matchEnd) || ldtEnd.isEqual(matchEnd) || ldtEnd.isBefore(matchEnd) && ldtEnd.isAfter(matchStart)){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Appointment Overlap!");
                    alert.setHeaderText("Appointment Overlap!");
                    alert.setContentText("Overlap with Appointment from " + matchStart.format(formatter) + " and " + matchEnd.format(formatter));
                    alert.showAndWait();
                    return;
                }
            }
                //check for business hours   
                if (busHrsTest(ldtStart, ldtEnd)){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Outside of Business Hours!");
                    alert.setHeaderText("Outside of Business Hours!");
                    alert.setContentText("Business hours are between 9AM and 5PM");
                    alert.showAndWait();
                    return;
            }
            
            //Check if end appointment is before start
            
            if(ldtEnd.isBefore(ldtStart) || ldtEnd.isEqual(ldtStart)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Appointment End must be after Appointment Start");
                alert.setHeaderText("Appointment End must be after Appointment Start");
                alert.setContentText("Please correct appointment End time.");
                alert.showAndWait();
                return;
            }
              
            if (AppointmentJoin.addModifySwitch.equals("Modify Appointment")){
                int tempAppID = AppointmentJoin.selectedAppointment.getAppointmentId();
                DbSync.updateAppointment(tempCustomerId, tempTitle, tempDescription, tempLocation, tempContact, tempUrl, str_dbTSStart, str_dbTSEnd, tempAppID);
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Record updated Successfully!");
                alert.setHeaderText("Record updated Successfully!");
                alert.setContentText("Record updated Successfully!");
                alert.showAndWait();
                //refresh DB
                DbSync.downloadAppointmentJoin();
                DbSync.downloadCustomerJoin();
                //scene change
                Parent source = FXMLLoader.load(getClass().getResource("MainView.fxml"));
                Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                Scene scene = new Scene(source);
                stage.setScene(scene);
                stage.show();
            }
            else{
            
                //newAppointment(int customerId, String title, String description, String location, String contact, String url, String start, String end)
                DbSync.newAppointment(tempCustomerId, tempTitle, tempDescription, tempLocation, tempContact, tempUrl, str_dbTSStart, str_dbTSEnd);
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("New Appointment Added!");
                alert.setHeaderText("New Appointment Added!");
                alert.setContentText("New Appointment Added!");
                alert.showAndWait();
                //refresh DB
                DbSync.downloadAppointmentJoin();
                DbSync.downloadCustomerJoin();
                //scene change
                Parent source = FXMLLoader.load(getClass().getResource("MainView.fxml"));
                Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                Scene scene = new Scene(source);
                stage.setScene(scene);
                stage.show();
            }
        }
       
      
    }
    
    private boolean fieldsFilledTest(){
        if(     (TextField_Title.getText().isEmpty())||
                (TextField_Contact.getText().isEmpty())||
                (TextField_Url.getText().isEmpty())||
                (ComboBox_Description.getValue().isEmpty())||
                (ComboBox_Location.getValue().isEmpty())||
                (ComboBox_Customer.getValue().getCustomerId() == 0)||
                (DateField_StartDate.getValue() == null)||
                (ComboBox_StartHour.getValue().isEmpty()) ||
                (ComboBox_StartMinute.getValue().isEmpty()) || 
                (DateField_EndDate.getValue() == null)|| 
                (ComboBox_EndHour.getValue().isEmpty()) || 
                (ComboBox_EndMinute.getValue().isEmpty()))
        {
            return true;
        }
        else{
            return false;
        }
    }
    
    private boolean busHrsTest(LocalDateTime ldtStart, LocalDateTime ldtEnd){
        LocalDateTime busHrsStart = LocalDateTime.of((ldtStart.toLocalDate()), LocalTime.of(9, 0));
        LocalDateTime dayStart = LocalDateTime.of((ldtStart.toLocalDate()), LocalTime.of(0, 0));
        LocalDateTime dayEnd = LocalDateTime.of((ldtStart.toLocalDate()), LocalTime.of(23, 59));
        LocalDateTime busHrsEnd = LocalDateTime.of((ldtStart.toLocalDate()), LocalTime.of(17, 0));
        if(
            (ldtStart.isEqual(busHrsEnd)) ||  
            (ldtStart.isAfter(dayStart)) && (ldtStart.isBefore(busHrsStart)) || 
            (ldtStart.isAfter(busHrsEnd)) && (ldtStart.isBefore(dayEnd)) || 
            (ldtEnd.isEqual(busHrsStart)) || 
            (ldtEnd.isAfter(dayStart)) && (ldtEnd.isBefore(busHrsStart)) || 
            (ldtEnd.isAfter(busHrsEnd)) && ldtEnd.isBefore(dayEnd) )
        {
            return true;
        }
        else{
            return false;
        }
    }
    
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy hh:mm a");

    @FXML
    private void handlerCancel(ActionEvent event) throws Exception {
        //scene change
        Parent source = FXMLLoader.load(getClass().getResource("MainView.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(source);
        stage.setScene(scene);
        stage.show();
    }
    
    private void initializeComboBox() {
        
        ObservableList<String> hours = FXCollections.observableArrayList("01","02","03","04","05","06","07","08","09","10","11","12");
        ObservableList<String> minutes = FXCollections.observableArrayList("00", "15", "30", "45");
        ObservableList<String> appTypes = FXCollections.observableArrayList("Presentation", "Training", "Proposal");
        ObservableList<String> locations = FXCollections.observableArrayList("Phoenix", "New York", "London");
        ObservableList<String> amPM = FXCollections.observableArrayList("AM", "PM");
        
        ComboBox_StartHour.setItems(hours);
        ComboBox_EndHour.setItems(hours);
        ComboBox_StartMinute.setItems(minutes);
        ComboBox_EndMinute.setItems(minutes);
        ComboBox_Description.setItems(appTypes);
        ComboBox_Location.setItems(locations);
        ComboBox_StartAMPM.setItems(amPM);
        ComboBox_EndAMPM.setItems(amPM);
        
        ComboBox_Customer.setItems(CustomerList);
        
        ComboBox_Customer.setConverter(new StringConverter<CustomerJoin>() {

            @Override
            public String toString(CustomerJoin object) {
            return object.nameProperty().get();
            }

            @Override
            public CustomerJoin fromString(String string) {
            return null;
            }
                                   
        });
        
        
    }

    @FXML
    private void Handler_ModifyCustomer(ActionEvent event) throws Exception{
        CustomerJoin.selectedCustomer = ComboBox_Customer.getValue();
        CustomerJoin.addModifySwitch = "Modify Customer";
        
        //switch scene
        Parent source = FXMLLoader.load(getClass().getResource("CustomerEdit.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(source);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void Handler_NewCustomer(ActionEvent event) throws Exception{
        CustomerJoin.addModifySwitch = "Add Customer";
        
        //switch scene
        Parent source = FXMLLoader.load(getClass().getResource("CustomerEdit.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(source);
        stage.setScene(scene);
        stage.show();
    }
    
}
