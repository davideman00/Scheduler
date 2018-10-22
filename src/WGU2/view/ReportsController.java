/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WGU2.view;

import WGU2.model.AppointmentJoin;
import static WGU2.model.AppointmentJoin.AppointmentFilterList;
import static WGU2.model.AppointmentJoin.AppointmentFilterListTemp;
import static WGU2.model.AppointmentJoin.AppointmentList;
import WGU2.model.UserTable;
import java.net.URL;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author User
 */
public class ReportsController implements Initializable {

    @FXML
    private Label labelReportTitle;
    @FXML
    private TableColumn<AppointmentJoin, ZonedDateTime> tableColAppStart;
    @FXML
    private TableColumn<AppointmentJoin, ZonedDateTime> tableColAppEnd;
    @FXML
    private TableColumn<AppointmentJoin, String> tableColAppConsultant;
    @FXML
    private TableColumn<AppointmentJoin, String> tableColAppDescription;
    @FXML
    private TableColumn<AppointmentJoin, String> tableColAppLocation;
    @FXML
    private TableView<AppointmentJoin> tableViewReports;
    
    public static String reportSwitch;
    @FXML
    private Label labelOne;
    @FXML
    private Label labelTwo;
    @FXML
    private Label labelThree;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Appointment columns binding
        tableColAppDescription.setCellValueFactory(cellData -> cellData.getValue().DescriptionProperty());
        tableColAppLocation.setCellValueFactory(cellData -> cellData.getValue().LocationProperty());
        
        tableColAppStart.setCellValueFactory(cellData -> new SimpleObjectProperty (cellData.getValue().getStart().format(formatter)));
        tableColAppEnd.setCellValueFactory(cellData -> new SimpleObjectProperty (cellData.getValue().getEnd().format(formatter)));
        tableColAppConsultant.setCellValueFactory(cellData -> cellData.getValue().CreatedByProperty());
        
        //set AppointmentJoin TableView
        tableViewReports.setItems(AppointmentFilterListTemp);
        
        if(AppointmentJoin.reportSelect.equals("type")){
            appTypeReport();    
            }
        if(AppointmentJoin.reportSelect.equals("location")){
            appLocationReport();    
            }
        if(AppointmentJoin.reportSelect.equals("consultant")){
            consultantReport();    
            }
        
        
      }    
    
    
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy hh:mm a");
    
    
    private void appTypeReport(){
        int presentationCount = 0;
        int trainingCount = 0;
        int proposalsCount = 0;
        for (int i = 0; i < AppointmentFilterList.size(); i++){
            if (AppointmentFilterList.get(i).getDescription().equals("Presentation")){
                presentationCount++;
            }
            if (AppointmentFilterList.get(i).getDescription().equals("Training")){
                trainingCount++;
            }
            if (AppointmentFilterList.get(i).getDescription().equals("Proposal")){
                proposalsCount++;
            }
                        
        }
        labelReportTitle.setText("Appointment Types Report");
        labelOne.setText("Presentations Total: " + presentationCount);  
        labelTwo.setText("Trainings Total: " + trainingCount);
        labelThree.setText("Proposals Total: " + proposalsCount);
        }
    
    public void appLocationReport(){
        int phoenixCount =0;
        int newYorkCount = 0;
        int londonCount = 0;
        
        for (int i = 0; i < AppointmentFilterList.size(); i++){
            if (AppointmentFilterList.get(i).getLocation().equals("Phoenix")){
                phoenixCount++;
            }
            if (AppointmentFilterList.get(i).getLocation().equals("New York")){
                newYorkCount++;
            }
            if (AppointmentFilterList.get(i).getLocation().equals("London")){
                londonCount++;
            }
            
        }
        labelReportTitle.setText("Appointment Locations Report");
        labelOne.setText("Phoenix Total: " + phoenixCount);
        labelTwo.setText("New York Total: " + newYorkCount);
        labelThree.setText("London Total: " + londonCount);
        }

    @FXML
    private void handlerOk(ActionEvent event) throws Exception {
        Parent source = FXMLLoader.load(getClass().getResource("MainView.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(source);
        stage.setScene(scene);
        stage.show();
    
    }
    
    private void consultantReport(){
        
        if(UserTable.selectedUser != null ){
        
                AppointmentFilterList.clear();
                for (int i = 0; i < AppointmentFilterListTemp.size(); i++){
                    if(AppointmentFilterListTemp.get(i).getCreatedBy().equals(UserTable.selectedUser.getUserName())){
                        AppointmentFilterList.add(AppointmentFilterListTemp.get(i));
                
                    }
                }
        
            tableViewReports.setItems(AppointmentFilterList);
            labelReportTitle.setText("Report for Consultant: " + UserTable.selectedUser.getUserName());
            labelOne.setVisible(false);
            labelTwo.setVisible(false);
            labelThree.setVisible(false);
            
        }
        
    }
    
}
