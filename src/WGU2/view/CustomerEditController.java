/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WGU2.view;

import static WGU2.model.AddressTable.AddressTableList;
import WGU2.model.CityTable;
import static WGU2.model.CityTable.CityTableList;
import static WGU2.model.CityTable.CityTableListFiltered;
import WGU2.model.CountryTable;
import static WGU2.model.CountryTable.CountryTableList;
import WGU2.model.CustomerJoin;
import static WGU2.model.CustomerJoin.CustomerList;
import WGU2.model.DbSync;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author User
 */
public class CustomerEditController implements Initializable {

    @FXML
    private Label labelCustomerAddModify;
    @FXML
    private ComboBox<CityTable> comboBoxCustCity;
    @FXML
    private ComboBox<CountryTable> comboBoxCustCountry;
    @FXML
    private TextField textFieldCustAddress;
    @FXML
    private TextField textFieldCustName;
    @FXML
    private TextField textFieldCustPhone;
    @FXML
    private TextField textFieldCustPostalCode;
    @FXML
    private TextField textFieldCustAddress2;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comboBoxCustCity.setItems(null);
        comboBoxCustCountry.setItems(null);
        
        
        labelCustomerAddModify.setText(CustomerJoin.addModifySwitch); 
        if (CustomerJoin.selectedCustomer != null){
            initializeCountryComboBox();
            initializeCityComboBox();
            for (int i = 0; i < CountryTableList.size(); i++){
                if (CustomerJoin.selectedCustomer.getCountryId() == CountryTable.CountryTableList.get(i).getCountryId()){
                    comboBoxCustCountry.getSelectionModel().select(CountryTableList.get(i));
                }
            }
            
            for (int i = 0; i < CityTableList.size(); i++){
                if (CustomerJoin.selectedCustomer.getCityId() == CityTable.CityTableList.get(i).getCityId()){
                    comboBoxCustCity.getSelectionModel().select(CityTableList.get(i));
                }
            }
            
            textFieldCustAddress.setText(CustomerJoin.selectedCustomer.getAddress());
            textFieldCustAddress2.setText(CustomerJoin.selectedCustomer.getAddress2());
            textFieldCustName.setText(CustomerJoin.selectedCustomer.getName());
            textFieldCustPhone.setText(CustomerJoin.selectedCustomer.getPhone());
            textFieldCustPostalCode.setText(CustomerJoin.selectedCustomer.getPostalCode());
            
            
        }
        else{
            initializeCountryComboBox();
            initializeCityComboBox();
        }
            
    }    

    @FXML
    private void handlerSave(ActionEvent event) throws Exception{
        if(fieldsFilledTest()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fields Empty!");
            alert.setHeaderText("Please fill out all fields!");
            alert.setContentText("Please fill out all fields!");
            alert.showAndWait();
            
        }
        else{
            String tempCustName = textFieldCustName.getText();
            String tempCustAddress = textFieldCustAddress.getText();
            String tempCustAddress2 = textFieldCustAddress2.getText();
            String tempCustPhone = textFieldCustPhone.getText();
            String tempCustPostalCode = textFieldCustPostalCode.getText();
            int tempCityId = comboBoxCustCity.getValue().getCityId();
            
            if (CustomerJoin.selectedCustomer != null){
                int tempCustId = CustomerJoin.selectedCustomer.getCustomerId();
                int tempAddressId = CustomerJoin.selectedCustomer.getAddressId();
                DbSync.updateAddress(tempAddressId, tempCustAddress, tempCustAddress2, tempCityId, tempCustPostalCode, tempCustPhone);
                DbSync.updateCustomer(tempCustId, tempCustName, tempAddressId);
                
                //clear selected Customer
                CustomerJoin.selectedCustomer = null;
                 //refresh local
                DbSync.downloadAppointmentJoin();
                DbSync.downloadCustomerJoin();
                sceneChangeMain(event);
            }
            else{
                //update DB
                int tempAddressId = DbSync.newAddress(tempCityId, tempCustAddress, tempCustAddress2, tempCustPostalCode, tempCustPhone);
                DbSync.newCustomer(tempCustName, tempAddressId);
                
                //clear selected Customer
                CustomerJoin.selectedCustomer = null;
                //refresh local
                DbSync.downloadAppointmentJoin();
                DbSync.downloadCustomerJoin();
                sceneChangeMain(event);
                
            }
        }
    }
    
    
    private boolean fieldsFilledTest(){
        if(     (textFieldCustName.getText().isEmpty())||
                (textFieldCustAddress.getText().isEmpty())||
                (textFieldCustAddress2.getText().isEmpty())||
                (textFieldCustPhone.getText().isEmpty())||
                (textFieldCustPostalCode.getText().isEmpty())||
                (comboBoxCustCity.getValue()== null))
                
        {
            return true;
        }
        else{
            return false;
        }
    }
    
    
    
    @FXML
    private void handlerCancel(ActionEvent event) throws Exception {
        //clear selected Customer
        CustomerJoin.selectedCustomer = null;
        sceneChangeMain(event);
    }
    
    private void sceneChangeMain(ActionEvent event) throws Exception {
        Parent source = FXMLLoader.load(getClass().getResource("MainView.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(source);
        stage.setScene(scene);
        stage.show();
    }
      
    private void initializeCityComboBox(){
        comboBoxCustCity.setItems(null);
        DbSync.downloadCityTable();
        comboBoxCustCity.setItems(CityTableList);
        comboBoxCustCity.setConverter(new StringConverter<CityTable>() {
            @Override
            public String toString(CityTable object) {
            return object.cityProperty().get();
            }
            @Override
            public CityTable fromString(String string) {
            return null;
            }
            });
        
    }
    /*
    @FXML
    private void initializeCityComboBoxFiltered(){
        comboBoxCustCountry.setItems(null);
        DbSync.downloadCityTable();   
        CountryTable temp_CountrySelect = null;
        CityTableListFiltered.clear();
        temp_CountrySelect = comboBoxCustCountry.getValue();
           
        for (int i = 0; i < CityTableList.size(); i++){
                if (temp_CountrySelect.getCountryId() == CityTableList.get(i).getCountryId()){
                    int cityID = CityTableList.get(i).getCityId();
                    int countryID = CityTableList.get(i).getCountryId();
                    String city = CityTableList.get(i).getCity();
                    
                    CityTableListFiltered.add(new CityTable(cityID, countryID, city));
                    comboBoxCustCity.setItems(CityTableListFiltered); 
                    
                    comboBoxCustCity.setConverter(new StringConverter<CityTable>() {
                    @Override
                    public String toString(CityTable object) {
                    return object.cityProperty().get();
                    }
                    @Override
                    public CityTable fromString(String string) {
                    return null;
                    }
                });
                }
            }
        }
    */
            
    private void initializeCountryComboBox(){
            comboBoxCustCountry.setItems(null);
            DbSync.downloadCountryTable();
            comboBoxCustCountry.setItems(CountryTableList);
            comboBoxCustCountry.setConverter(new StringConverter<CountryTable>() {
            @Override
            public String toString(CountryTable object) {
            return object.countryProperty().get();
            }
            @Override
            public CountryTable fromString(String string) {
            return null;
            }
            });
    }

    @FXML
    private void handlerNewCity(ActionEvent event) {
        if (comboBoxCustCountry.getValue() != null){
            String city = "";
            int cityId = 0;
            int countryId = 0;
        
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Add New City");
            dialog.setTitle("Add New City");
            dialog.setContentText("Please enter the new value:");
            Optional<String> result = dialog.showAndWait();
                if (result.isPresent()) {
                    city = result.get();
                    countryId = comboBoxCustCountry.getValue().getCountryId();
                    cityId = DbSync.newCity(city, countryId);
                    comboBoxCustCity.setValue(new CityTable(cityId, countryId, city));
                    comboBoxCustCity.setConverter(new StringConverter<CityTable>() {
                        @Override
                        public String toString(CityTable object) {
                        return object.cityProperty().get();
                        }
                        @Override
                        public CityTable fromString(String string) {
                        return null;
                        }
                    });
                }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Select/Add Country first!");
                alert.setHeaderText("Select/Add Country first!");
                alert.setContentText("Select/Add Country first!");
                alert.showAndWait();
            }
        
    }
    
    @FXML
    private void handlerNewCountry(ActionEvent event) {
        String country = "";
        int countryId = 0;
        CountryTable newCountry = null;
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add New Country");
        dialog.setTitle("Add New Country");
        dialog.setContentText("Please enter the new value:");
        Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                country = result.get();
                countryId = DbSync.newCountry(country);
                comboBoxCustCountry.setValue(new CountryTable(countryId, country));
                comboBoxCustCountry.setConverter(new StringConverter<CountryTable>() {
                    @Override
                    public String toString(CountryTable object) {
                    return object.countryProperty().get();
                    }
                    @Override
                    public CountryTable fromString(String string) {
                    return null;
                    }
                });
            }
    
    }
    
    
}
