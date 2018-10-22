
package WGU2.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class AddressTable {
    IntegerProperty addressId;
    IntegerProperty cityId;
    StringProperty address;
    StringProperty address2;
    StringProperty postalCode;
    StringProperty phone;
    
    public static ObservableList<AddressTable> AddressTableList = FXCollections.observableArrayList();

    public AddressTable(int addressId, int cityId, String address, String address2, String postalCode, String phone) {
        this.addressId = new SimpleIntegerProperty(addressId);
        this.cityId = new SimpleIntegerProperty(cityId);
        this.address = new SimpleStringProperty(address);
        this.address2 = new SimpleStringProperty(address2);
        this.postalCode = new SimpleStringProperty(postalCode);
        this.phone = new SimpleStringProperty(phone);
        
    }
    
    public static void addNew(int addressId, int cityId, String address, String address2, String postalCode, String phone) {
            
            AddressTableList.add(new AddressTable(addressId, cityId, address, address2, postalCode, phone));
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

    public void setAddress2(String address) {
        this.address2.set(address);
    }
    
    public StringProperty address2Property(){
        return address2;
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
