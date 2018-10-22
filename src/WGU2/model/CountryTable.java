
package WGU2.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class CountryTable{
    IntegerProperty countryId;
    StringProperty country;
    
    public static ObservableList<CountryTable> CountryTableList = FXCollections.observableArrayList();

    public CountryTable(int countryId, String country) {
        this.countryId = new SimpleIntegerProperty(countryId);
        this.country = new SimpleStringProperty(country);
        
        
    }
    
    public static void addNew(int countryId, String country) {
            CountryTableList.add(new CountryTable( countryId, country));
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
      
    
    public String getCountry() {
        return country.get();
    }

    public void setCountry(String country) {
        this.country.set(country);
    }
    
    public StringProperty countryProperty(){
        return country;
    }
    
}
