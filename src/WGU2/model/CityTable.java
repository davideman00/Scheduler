
package WGU2.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class CityTable {
    
    IntegerProperty cityId;
    IntegerProperty countryId;
    StringProperty city;
    
    
    public static ObservableList<CityTable> CityTableList = FXCollections.observableArrayList();
    public static ObservableList<CityTable> CityTableListFiltered = FXCollections.observableArrayList();

    public CityTable(int cityId, int countryId, String city) {
        this.cityId = new SimpleIntegerProperty(cityId);
        this.countryId = new SimpleIntegerProperty(countryId);
        this.city = new SimpleStringProperty(city);
        
    }
    
    public static void addNew(int cityId, int countryId, String city) {
            
            CityTableList.add(new CityTable( cityId, countryId, city));
    }
    
    public static void addNewFiltered(int cityId, int countryId, String city) {
            
            CityTableListFiltered.add(new CityTable( cityId, countryId, city));
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
    
    public String getCity() {
        return city.get();
    }

    public void setCity(String city) {
        this.city.set(city);
    }
    
    public StringProperty cityProperty(){
        return city;
    }
    
    
            
}
