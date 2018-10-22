/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WGU2.view;


import WGU2.model.AppointmentJoin;
import static WGU2.model.AppointmentJoin.AppointmentFilterList;
import static WGU2.model.AppointmentJoin.AppointmentFilterListTemp;
import WGU2.model.DbSync;
import static WGU2.model.AppointmentJoin.AppointmentList;
import WGU2.model.CustomerJoin;
import static WGU2.model.CustomerJoin.CustomerList;
import static WGU2.model.CustomerJoin.selectedCustomer;
import WGU2.model.UserTable;
import static WGU2.model.UserTable.UserList;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.util.StringConverter;


public class MainViewController implements Initializable {

    @FXML
    private TableColumn<AppointmentJoin, String> tableColumnAppDescription;
    @FXML
    private TableColumn<AppointmentJoin, String> tableColumnAppLocation;
    @FXML
    private TableColumn<AppointmentJoin, String> tableColumnAppCustomer;
    @FXML
    private TableColumn<AppointmentJoin, ZonedDateTime> tableColumnAppStart;
    @FXML
    private TableColumn<AppointmentJoin, ZonedDateTime> tableColumnAppEnd;
    @FXML
    private TableColumn<AppointmentJoin, String> tableColumnAppConsultant;
    @FXML
    private TableView<AppointmentJoin> tableViewCalendar;
    @FXML
    private TableColumn<UserTable, String> tableColumnUserUsername;
    @FXML
    private TableColumn<UserTable, String> tableColumnUserPassword;
    @FXML
    private TableView<UserTable> tableViewUsers;
    @FXML
    private TableColumn<CustomerJoin, Integer> tableColCustCustId;
    @FXML
    private TableColumn<CustomerJoin, String> tableColCustName;
    @FXML
    private TableColumn<CustomerJoin, Integer> tableColCustActive;
    @FXML
    private TableColumn<CustomerJoin, Integer> tableColCustAddressId;
    @FXML
    private TableColumn<CustomerJoin, String> tableColCustAddress;
    @FXML
    private TableColumn<CustomerJoin, String> tableColCustPostalCode;
    @FXML
    private TableColumn<CustomerJoin, String> tableColCustPhone;
    @FXML
    private TableColumn<CustomerJoin, Integer> tableColCustCityId;
    @FXML
    private TableColumn<CustomerJoin, String> tableColCustCity;
    @FXML
    private TableColumn<CustomerJoin, Integer> tableColCustCountryId;
    @FXML
    private TableColumn<CustomerJoin, String> tableColCustCountry;
    @FXML
    private TableView<CustomerJoin> tableViewCustomers;
    
    Connection conn;
    
    public static LocalDate startDate;
    public static LocalDate endDate;
    public static String weekMonthSelect;
    public static int selectOffset;
    @FXML
    private Label labelCustHeader;
    @FXML
    private Button buttonPrevious;
    @FXML
    private Button buttonNext;
    @FXML
    private ComboBox<UserTable> comboBoxUser;
        
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Appointment columns binding
        tableColumnAppDescription.setCellValueFactory(cellData -> cellData.getValue().DescriptionProperty());
        tableColumnAppLocation.setCellValueFactory(cellData -> cellData.getValue().LocationProperty());
        tableColumnAppCustomer.setCellValueFactory(cellData -> cellData.getValue().CustomerNameProperty());
        tableColumnAppStart.setCellValueFactory(cellData -> new SimpleObjectProperty (cellData.getValue().getStart().format(formatter)));
        tableColumnAppEnd.setCellValueFactory(cellData -> new SimpleObjectProperty (cellData.getValue().getEnd().format(formatter)));
        tableColumnAppConsultant.setCellValueFactory(cellData -> cellData.getValue().CreatedByProperty());
        
        //set AppointmentJoin TableView
        tableViewCalendar.setItems(AppointmentList);
        
        //User columns binding
        tableColumnUserUsername.setCellValueFactory(cellData -> cellData.getValue().userNameProperty());
        tableColumnUserPassword.setCellValueFactory(cellData -> cellData.getValue().passwordProperty());
        
        //set UserTable TableView
        tableViewUsers.setItems(UserList);
        
        //Customer columns binding
        tableColCustCustId.setCellValueFactory(cellData -> cellData.getValue().customerIdProperty().asObject());
        tableColCustName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        tableColCustActive.setCellValueFactory(cellData -> cellData.getValue().activeProperty().asObject());
        tableColCustAddressId.setCellValueFactory(cellData -> cellData.getValue().addressIdProperty().asObject());
        tableColCustAddress.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
        tableColCustPostalCode.setCellValueFactory(cellData -> cellData.getValue().postalCodeProperty());
        tableColCustPhone.setCellValueFactory(cellData -> cellData.getValue().phoneProperty());
        tableColCustCityId.setCellValueFactory(cellData -> cellData.getValue().cityIdProperty().asObject());
        tableColCustCity.setCellValueFactory(cellData -> cellData.getValue().cityProperty());
        tableColCustCountryId.setCellValueFactory(cellData -> cellData.getValue().countryIdProperty().asObject());
        tableColCustCountry.setCellValueFactory(cellData -> cellData.getValue().countryProperty());
        
        //set CustomerJoin TableView
        tableViewCustomers.setItems(CustomerList);
        
        //clear switches
        CustomerJoin.addModifySwitch = null;
        AppointmentJoin.addModifySwitch = null;
        weekMonthSelect = "";
        
        //hide buttons
        buttonPrevious.setVisible(false);
        buttonNext.setVisible(false);
        
        //set Labels
        labelCustHeader.setText("All Appointments");
        
        //initialize combobox
        initializeUserComboBox();
        
        selectOffset = 0;
        
        //copy to filter lists
        AppointmentFilterList.addAll(AppointmentList);
        AppointmentFilterListTemp.addAll(AppointmentList);
    }    
    
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy hh:mm a");
    
    private void standardAlert(String message){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(message);
        alert.setHeaderText(message);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private void sceneChange(ActionEvent event, String sceneFile) throws Exception{
        Parent source = FXMLLoader.load(getClass().getResource(sceneFile));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(source);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handlerModifyAppointment(ActionEvent event) throws Exception {
        AppointmentJoin.addModifySwitch = "Modify Appointment";
        AppointmentJoin.selectedAppointment = tableViewCalendar.getSelectionModel().getSelectedItem();
        if(AppointmentJoin.selectedAppointment == null){
            standardAlert("Must select an Appointment to edit");
            }
        else{
        sceneChange(event, "AppointmentEdit.fxml");
        }
    }

    @FXML
    private void handlerNewAppointment(ActionEvent event) throws Exception {
        AppointmentJoin.addModifySwitch = "New Appointment";
        AppointmentJoin.selectedAppointment = null;
        sceneChange(event, "AppointmentEdit.fxml");
    }
    
    @FXML
    private void handlerAddCustomer(ActionEvent event) throws Exception {
        CustomerJoin.addModifySwitch = "Add Customer";
        sceneChange(event, "CustomerEdit.fxml");
    }

    @FXML
    private void handlerModifyCustomer(ActionEvent event) throws Exception {
        CustomerJoin.selectedCustomer = tableViewCustomers.getSelectionModel().getSelectedItem();
        CustomerJoin.addModifySwitch = "Modify Customer";
        if (CustomerJoin.selectedCustomer == null){
            standardAlert("Must select a Customer to change");
        }
        else{
            sceneChange(event, "CustomerEdit.fxml");
        }
    }
    
    
    @FXML
    private void handlerDeleteAppointment(ActionEvent event) {
        AppointmentJoin.selectedAppointment = null;
        AppointmentJoin.selectedAppointment = tableViewCalendar.getSelectionModel().getSelectedItem();
        if(AppointmentJoin.selectedAppointment != null){
 
            //Confirm Delete
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Delete...");
            alert.setHeaderText("Deleting...");
            alert.setContentText("Are you sure you wanna Delete this Appointment?");
            alert.showAndWait()
                    .filter(response -> response == ButtonType.OK)
                    .ifPresent(response -> DbSync.setAppointmentInactive(AppointmentJoin.selectedAppointment.getAppointmentId()));
                    //Lambda expression to more efficiently capture user feedback
            DbSync.downloadAppointmentJoin();
            tableViewCalendar.setItems(AppointmentList);
        } else {
             // Nothing selected.
            standardAlert("Must select an Appointment!");
            
        }
    }

    @FXML
    private void handlerSetInactive(ActionEvent event) {
        selectedCustomer = null;
        selectedCustomer = tableViewCustomers.getSelectionModel().getSelectedItem();
        if(selectedCustomer != null){
            //Confirm Delete
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Delete...");
            alert.setHeaderText("Deleting...");
            alert.setContentText("Are you sure you want to make this customer inactive?");
            alert.showAndWait()
                    .filter(response -> response == ButtonType.OK)
                    .ifPresent(response -> DbSync.setCustomerInactive(selectedCustomer.getCustomerId()));
            //Lambda expression to more efficiently capture user feedback
            DbSync.downloadCustomerJoin();
            tableViewCustomers.setItems(CustomerList);
        } else {
            // Nothing selected.
            standardAlert("Must select a Customer!");
            
        }
    }
    
    
    
    @FXML
    private void handlerMonth(ActionEvent event) {
        selectOffset = 0;
        monthFilter(selectOffset);
        weekMonthSelect = "Month";
        comboBoxUser.getSelectionModel().clearSelection();
    }

    @FXML
    private void handlerWeek(ActionEvent event) {
        selectOffset = 0;
        weekFilter(selectOffset);
        weekMonthSelect = "Week";
        comboBoxUser.getSelectionModel().clearSelection();
    }

    @FXML
    private void handlerAll(ActionEvent event) {
        AppointmentFilterList.clear();
        AppointmentFilterListTemp.clear();
        
        //copy to filter lists
        AppointmentFilterList.addAll(AppointmentList);
        AppointmentFilterListTemp.addAll(AppointmentList);
        
        tableViewCalendar.setItems(AppointmentFilterList);
        
        labelCustHeader.setText("All Appointments");
        
        buttonPrevious.setVisible(false);
        buttonNext.setVisible(false);
        weekMonthSelect = "";
        comboBoxUser.getSelectionModel().clearSelection();
    }
    
    private void monthFilter(int offset){
        AppointmentFilterList.clear();
        AppointmentFilterListTemp.clear();
        buttonPrevious.setVisible(true);
        buttonNext.setVisible(true);
        
        int selectedMonth = 0;
        int selectedYear = 0;
        selectedMonth = LocalDate.now().getMonthValue() + offset;
        selectedYear = LocalDate.now().getYear();
        
        int appMonth = 0;
        int appYear = 0;
         for (int i = 0; i < AppointmentList.size(); i++){
            appMonth = AppointmentJoin.AppointmentList.get(i).getStart().toLocalDate().getMonthValue();
            appYear = AppointmentJoin.AppointmentList.get(i).getEnd().toLocalDate().getYear();
                if (selectedMonth == appMonth && selectedYear == appYear){
                    AppointmentFilterListTemp.add(AppointmentJoin.AppointmentList.get(i));
                    }
        }
        //copy over to main filter, keep temp filter array for sub filter by consultant
        for (int i = 0; i < AppointmentFilterListTemp.size(); i++){
            AppointmentFilterList.add(AppointmentFilterListTemp.get(i));
        }
         
        tableViewCalendar.setItems(AppointmentFilterList);
        labelCustHeader.setText("Month of " + Month.of(selectedMonth).name());
        
    }
    
    private void weekFilter(int offset){
        AppointmentFilterList.clear();
        AppointmentFilterListTemp.clear();
        buttonPrevious.setVisible(true);
        buttonNext.setVisible(true);
        
        LocalDate filterWeekStart = null;
        LocalDate filterWeekEnd = null;
        int weekOffset = 0;
        LocalDate appStart = null;
        LocalDate appEnd = null;
        weekOffset = (LocalDate.now().getDayOfWeek().getValue()) - offset;
        filterWeekStart = LocalDate.now().minusDays(weekOffset);
        filterWeekEnd = filterWeekStart.plusDays(6);
        
        for (int i = 0; i < AppointmentList.size(); i++){
        appStart = AppointmentList.get(i).getStart().toLocalDate();
        appEnd = AppointmentList.get(i).getEnd().toLocalDate();
            if (appStart.isEqual(filterWeekStart)|| appStart.isAfter(filterWeekStart)){
                if (appEnd.isEqual(filterWeekEnd)||appEnd.isBefore(filterWeekEnd)){
                    AppointmentFilterListTemp.add(AppointmentList.get(i));
                    
                }
            }
        }
        //copy over to main filter, keep temp filter array for sub filter by consultant
        AppointmentFilterList.addAll(AppointmentFilterListTemp);
        
        tableViewCalendar.setItems(AppointmentFilterList);
        labelCustHeader.setText("Week of " + filterWeekStart + " to " + filterWeekEnd) ;
        
    }

    @FXML
    private void handlerPrevious(ActionEvent event) {
        if(weekMonthSelect.equals("Month")){
            selectOffset = selectOffset - 1;
            monthFilter(selectOffset);
        }
        else{
            selectOffset = selectOffset - 7;
            weekFilter(selectOffset);
        }
        
    }

    @FXML
    private void handlerNext(ActionEvent event) {
        if(weekMonthSelect.equals("Month")){
            selectOffset = selectOffset + 1;
            monthFilter(selectOffset);
        }
        else{
            selectOffset = selectOffset + 7;
            weekFilter(selectOffset);
        }
    }
        
      
    private void initializeUserComboBox(){
    comboBoxUser.setItems(UserList);
        
        comboBoxUser.setConverter(new StringConverter<UserTable>() {

            @Override
            public String toString(UserTable object) {
            return object.userNameProperty().get();
            }

            @Override
            public UserTable fromString(String string) {
            return null;
            }
                                   
        });    
        
    }

    @FXML
    private void handlerOpenUserLog(ActionEvent event) {
        try {
            Desktop desktop = null;
            if (Desktop.isDesktopSupported()) {
                desktop = Desktop.getDesktop();
                desktop.open(new File("userLog.txt"));
                }
            } 
        catch (IOException ioe) {
                ioe.printStackTrace();
            }
    }

    @FXML
    private void handlerAppTypes(ActionEvent event) throws Exception {
        AppointmentJoin.reportSelect = "type";
        sceneChange(event, "Reports.fxml");
        
    }
    
    @FXML
    private void handlerAppLocation(ActionEvent event) throws Exception {
        AppointmentJoin.reportSelect = "location";
        sceneChange(event, "Reports.fxml");
        
    }

    @FXML
    private void handlerConsultantReport(ActionEvent event) throws Exception {
        if(comboBoxUser.getValue() == null){
           Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Must select a Consultant!");
            alert.setHeaderText("Must select a Consultant!");
            alert.setContentText("Please select a consultant from the drop down!");
            alert.showAndWait(); 
        }
        else{
            AppointmentJoin.reportSelect = "consultant";
            UserTable.selectedUser = comboBoxUser.getValue();
            sceneChange(event, "Reports.fxml");
        }
    }

    
    
}
