
package WGU2.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class CustomerJoin {
    IntegerProperty customerId;
    IntegerProperty appointmentId;
    IntegerProperty addressId;
    IntegerProperty cityId;
    IntegerProperty countryId;
    IntegerProperty active;
    StringProperty name;
    StringProperty address;
    StringProperty address2;
    StringProperty city;
    StringProperty country;
    StringProperty postalCode;
    StringProperty phone;
    
    public static CustomerJoin selectedCustomer;
    public static String addModifySwitch;
    
    
    public static ObservableList<CustomerJoin> CustomerList = FXCollections.observableArrayList();

    public CustomerJoin(int customerId, int appointmentId, int addressId, int cityId, int countryId, int active, String name, String address, String address2, 
            String city, String country, String postalCode, String phone) {
        this.customerId =new SimpleIntegerProperty(customerId);
        this.appointmentId = new SimpleIntegerProperty(appointmentId);
        this.addressId = new SimpleIntegerProperty(addressId);
        this.cityId = new SimpleIntegerProperty(cityId);
        this.countryId = new SimpleIntegerProperty(countryId);
        this.active = new SimpleIntegerProperty(active);
        this.name = new SimpleStringProperty(name);
        this.address = new SimpleStringProperty(address);
        this.address2 = new SimpleStringProperty(address2);
        this.city = new SimpleStringProperty(city);
        this.country = new SimpleStringProperty(country);
        this.postalCode = new SimpleStringProperty(postalCode);
        this.phone = new SimpleStringProperty(phone);
        
    }
    
    public static void addNew(int customerId, int appointmentId, int addressId, int cityId, int countryId, int active, String name, String address, String address2, String city, String country, 
            String postalCode, String phone) {
            CustomerList.add(new CustomerJoin( customerId, appointmentId, addressId, cityId, countryId, active, name, address, address2, city, country, postalCode, phone));
    }
    
    public CustomerJoin(int customerId, int active, String name) {
        this.customerId =new SimpleIntegerProperty(customerId);
        this.active = new SimpleIntegerProperty(active);
        this.name = new SimpleStringProperty(name);
        
    }
    
    public static void addNewCustomerName(int customerId, int active, String name) {
            CustomerList.add(new CustomerJoin(customerId, active, name));
    }
    
    public int getCustomerId() {
        return customerId.get();
    }

    public void setCustomerId(int customerId) {
        this.customerId.set(customerId);
    }
    
    public IntegerProperty customerIdProperty(){
        return customerId;
    }
        
    public int getAppointmentId() {
        return appointmentId.get();
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId.set(appointmentId);
    }
    
    public IntegerProperty appointmentIdProperty(){
        return appointmentId;
    }
    
    public int getAddressId() {
        return addressId.get();
    }

    public void setAddressId(int addressId) {
        this.addressId.set(addressId);
    }
    
    public IntegerProperty addressIdProperty(){
        return addressId;
    }
    
    public int getCityId() {
        return cityId.get();
    }

    public void setCityId(int cityId) {
        this.cityId.set(cityId);
    }
    
    public IntegerProperty cityIdProperty(){
        return cityId;
    }
    
    public int getCountryId() {
        return countryId.get();
    }

    public void setCountryId(int countryId) {
        this.countryId.set(countryId);
    }
    
    public IntegerProperty countryIdProperty(){
        return countryId;
    }
        
    public int getActive() {
        return active.get();
    }

    public void setActive(int active) {
        this.active.set(active);
    }
    
    public IntegerProperty activeProperty(){
        return active;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }
    
    public StringProperty nameProperty(){
        return name;
    }
    
    public String getAddress() {
        return address.get();
    }

    public void setAddress(String address) {
        this.address.set(address);
    }
    
    public StringProperty addressProperty(){
        return address;
    }
    
    public String getAddress2() {
        return address2.get();
    }

    public void setAddress2(String address2) {
        this.address2.set(address2);
    }
    
    public StringProperty address2Property(){
        return address2;
    }
    
    public String getCity() {
        return city.get();
    }

    public void setCity(String city) {
        this.city.set(city);
    }
    
    public StringProperty cityProperty(){
        return city;
    }
    
    public String getCountry() {
        return country.get();
    }

    public void setCountry(String country) {
        this.country.set(country);
    }
    
    public StringProperty countryProperty(){
        return country;
    }
    
    public String getPostalCode() {
        return postalCode.get();
    }

    public void setPostalCode(String postalCode) {
        this.postalCode.set(postalCode);
    }
    
    public StringProperty postalCodeProperty(){
        return postalCode;
    }
    
        public String getPhone() {
        return phone.get();
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }
    
    public StringProperty phoneProperty(){
        return phone;
    }
            
}
