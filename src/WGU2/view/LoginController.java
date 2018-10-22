/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WGU2.view;


import static WGU2.model.AppointmentJoin.AppointmentList;
import WGU2.model.DbSync;
import WGU2.model.UserTable;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author UserTable
 */
public class LoginController implements Initializable {

    @FXML
    private PasswordField passwordFieldPassword;
    @FXML
    private TextField textFieldUsername;
    @FXML
    private Label labelUsername;
    @FXML
    private Label labelPassword;
    @FXML
    private Label labelTitle;
    
    Connection conn;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //change Default Locale
        //Locale.setDefault(new Locale("nl", "NL"));
        
        //import language files
        rb = ResourceBundle.getBundle("language_files/login");
        
        //Set Language labels
        labelUsername.setText(rb.getString("username"));
        labelPassword.setText(rb.getString("password"));
        labelTitle.setText(rb.getString("title"));
        
        DbSync.downloadUserTable();
        }    
    
    //method for getting language strings
    public String getLangString (String x){
        ResourceBundle rb = ResourceBundle.getBundle("language_files/login");
        return rb.getString(x);
    }
    
    
    
    
    
    @FXML
    private void handlerLogin(ActionEvent event) throws Exception {
        String tempUsername = textFieldUsername.getText();
        String tempPassword = passwordFieldPassword.getText();
        
        //check for empty/null fields
        if (!tempUsername.isEmpty() && !tempPassword.isEmpty()){
            //Check username/password match
                if (DbSync.login(tempUsername, tempPassword)){
                    UserTable.currentUser = tempUsername;
                    userLog(tempUsername);
                    DbSync.downloadAppointmentJoin();
                    DbSync.downloadCustomerJoin();
                    
                    //Check for apps within 15 mins
                                for (int k = 0; k < AppointmentList.size(); k++) {
                                LocalDateTime nowPlus = LocalDateTime.now().plusMinutes(15);
                                LocalDateTime now = LocalDateTime.now();
                                    if (AppointmentList.get(k).getStart().toLocalDateTime().isEqual(now)||AppointmentList.get(k).getStart().toLocalDateTime().isAfter(now)){
                                        if (AppointmentList.get(k).getStart().toLocalDateTime().isEqual(nowPlus)||AppointmentList.get(k).getStart().toLocalDateTime().isBefore(nowPlus)){
                                        String cusName = AppointmentList.get(k).getCustomerName();
                                        LocalTime appStart = AppointmentList.get(k).getStart().toLocalTime();
                                        String appLocation = AppointmentList.get(k).getLocation();
                                        Alert alert = new Alert(Alert.AlertType.ERROR);
                                        alert.setTitle("Appointment coming up!");
                                        alert.setHeaderText("Appointment within the next 15 Minutes");
                                        alert.setContentText("You have an appointment today at " + appStart + " with " + cusName + " at " + appLocation);
                                        alert.showAndWait();
                                        }
                                    }
                                }
                                //scene change
                                Parent source = FXMLLoader.load(getClass().getResource("MainView.fxml"));
                                Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                                Scene scene = new Scene(source);
                                stage.setScene(scene);
                                stage.show();
                }
                else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle(getLangString("usernamePwIncorrect"));
                        alert.setHeaderText(getLangString("usernamePwIncorrect"));
                        alert.setContentText(getLangString("usernamePwIncorrect"));
                        alert.showAndWait();
                    }
                    
                }
            
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(getLangString("fieldEmpty"));
                alert.setHeaderText(getLangString("fieldEmpty"));
                alert.setContentText(getLangString("fieldEmpty"));
                alert.showAndWait();
            }
            
    }
    
    private void userLog(String username){
        try(FileWriter fw = new FileWriter("userlog.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            out.println("user: " + username+ " TimeStamp: " + LocalDateTime.now());
        
        } 
        catch (IOException e) {
            e.printStackTrace() ;
        }
    }
}
   
   
