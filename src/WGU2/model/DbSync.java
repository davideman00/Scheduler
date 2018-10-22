/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WGU2.model;

import static WGU2.model.AddressTable.AddressTableList;
import static WGU2.model.AppointmentJoin.AppointmentList;
import static WGU2.model.CityTable.CityTableList;
import static WGU2.model.CountryTable.CountryTableList;
import static WGU2.model.CustomerJoin.CustomerList;
import static WGU2.model.UserTable.UserList;
import static java.lang.Integer.parseInt;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author UserTable
 */
public class DbSync {
        //Server name:  52.206.157.109 
        //Database name:  U04n6A
        //Username:  U04n6A
        //Password:  53688291722
        public final static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        public final static String DB_URL = "jdbc:mysql://52.206.157.109/U04n6A";

        //  Database credentials
        public final static String DBUSER = "U04n6A";
        public final static String DBPASS = "53688291722";
    
    public static void newAppointment(int customerId, String title, String description, String location, String contact, String url, String start, String end){
        
        Connection conn;
          try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            conn = DriverManager.getConnection(DB_URL, DBUSER, DBPASS);

            PreparedStatement ps = null;

        
            try {

                String sql = "INSERT INTO appointment (customerId, title, description, location, contact, url, start, end, createDate, createdBy, LastUpdate, LastUpdateBy) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                ps = conn.prepareStatement(sql);
                ps.setInt(1, customerId);
                ps.setString(2, title);
                ps.setString(3, description);
                ps.setString(4, location);
                ps.setString(5, contact);
                ps.setString(6, url);
                ps.setString(7, start);
                ps.setString(8, end);
                //ps.setString(9, LocalDateTime.now().toString());
                ps.setTimestamp(9, java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));
                ps.setString(10, UserTable.currentUser);
                //ps.setString(11, LocalDateTime.now().toString());
                ps.setTimestamp(11, java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));
                ps.setString(12, UserTable.currentUser);
                int res = ps.executeUpdate();
                if (res == 1) {
                    System.out.println("Created New Appointment!");
                } else {
                    System.out.println("Nogo!");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

           
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    
    public static void newCustomer(String customerName, int addressId){
        
        Connection conn;
          try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            conn = DriverManager.getConnection(DB_URL, DBUSER, DBPASS);

            PreparedStatement ps = null;

        
            try {

                String sql = "INSERT INTO customer (customerName, addressId, active, createDate, createdBy, LastUpdate, LastUpdateBy) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?)";

                ps = conn.prepareStatement(sql);
                ps.setString(1, customerName);
                ps.setInt(2, addressId);
                ps.setInt(3, 1);
                //ps.setString(4, LocalDateTime.now().toString());
                ps.setTimestamp(4, java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));
                ps.setString(5, UserTable.currentUser);
                //ps.setString(6, LocalDateTime.now().toString());
                ps.setTimestamp(6, java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));
                ps.setString(7, UserTable.currentUser);
                int res = ps.executeUpdate();
                if (res == 1) {
                    System.out.println("Created New Customer!");
                } else {
                    System.out.println("Nogo!");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

           
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public static int newAddress(int cityId, String address, String address2, String postalCode, String phone){
        int addressId = 0;
        Connection conn;
          try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            conn = DriverManager.getConnection(DB_URL, DBUSER, DBPASS);

            PreparedStatement ps = null;
            try {

                String sql = "INSERT INTO address (address, address2, cityId, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdateBy) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                       
                ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                
                ps.setString(1, address);
                ps.setString(2, address2);
                ps.setInt(3, cityId);
                ps.setString(4, postalCode);
                ps.setString(5, phone);
                ps.setTimestamp(6, java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));
                ps.setString(7, UserTable.currentUser);
                ps.setTimestamp(8, java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));
                ps.setString(9, UserTable.currentUser);
                
                
                int res = ps.executeUpdate();
                if (res == 1) {
                    ResultSet generatedKeys = ps.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        addressId = generatedKeys.getInt(1);
                        System.out.println("Created New Address!");
                        return addressId;
                    }
                } else {
                    System.out.println("Nogo!");
                    return addressId;
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
          return addressId;
    }

    public static int newCity(String city, int countryId){
        int cityId = 0;
        Connection conn;
          try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            conn = DriverManager.getConnection(DB_URL, DBUSER, DBPASS);

            PreparedStatement ps = null;

        
            try {

                String sql = "INSERT INTO city (city, countryId, createDate, createdBy, lastUpdate, lastUpdateBy) "
                        + "VALUES (?, ?, ?, ?, ?, ?)";

                ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, city);
                ps.setInt(2, countryId);
                //ps.setString(3, LocalDateTime.now().toString());
                ps.setTimestamp(3, java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));
                ps.setString(4, UserTable.currentUser);
                //ps.setString(5, LocalDateTime.now().toString());
                ps.setTimestamp(5, java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));
                ps.setString(6, UserTable.currentUser);
                int res = ps.executeUpdate();
                if (res == 1) {
                    ResultSet generatedKeys = ps.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        cityId = generatedKeys.getInt(1);
                        System.out.println("Created New City");
                        return cityId;
                    }
                } else {
                    System.out.println("Nogo!");
                    return cityId;
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

           
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
          return cityId;
    }
    
    public static int newCountry(String country){
        int countryId = 0;
        Connection conn;
          try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            conn = DriverManager.getConnection(DB_URL, DBUSER, DBPASS);

            PreparedStatement ps = null;

        
            try {

                String sql = "INSERT INTO country (country, createDate, createdBy, lastUpdate, lastUpdateBy) "
                        + "VALUES (?, ?, ?, ?, ?)";

                ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, country);
                ps.setTimestamp(2, java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));
                ps.setString(3, UserTable.currentUser);
                ps.setTimestamp(4, java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));
                ps.setString(5, UserTable.currentUser);
                int res = ps.executeUpdate();
                if (res == 1) {
                    ResultSet generatedKeys = ps.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        countryId = generatedKeys.getInt(1);
                        System.out.println("Created New Country");
                        return countryId;
                    }
                } else {
                    System.out.println("Nogo!");
                    return countryId;
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

           
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
          return countryId;
    }
    
    
    public static void updateAppointment(int customerId, String title, String description, String location, 
            String contact, String url, String start, String end, int appId ){
        
        Connection conn;
  
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            conn = DriverManager.getConnection(DB_URL, DBUSER, DBPASS);

            PreparedStatement ps = null;

            String sql = "UPDATE appointment SET customerId = ?, title = ?, description = ?, location = ?, contact = ?, url = ?, start = ?, end = ?, lastUpdate = ?, lastUpdateBy = ? WHERE appointmentId = ?";

        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, customerId);
            ps.setString(2, title);
            ps.setString(3, description);
            ps.setString(4, location);
            ps.setString(5, contact);
            ps.setString(6, url);
            ps.setString(7, start);
            ps.setString(8, end);
            //ps.setString(9, LocalDateTime.now().toString());
            ps.setTimestamp(9, java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));
            ps.setString(10, UserTable.currentUser);
            ps.setInt(11, appId);
            ps.execute();
            System.out.println("Appointment APPID#" + appId + " updated");
        } catch (SQLException ex) {
            Logger.getLogger(AppointmentJoin.class.getName()).log(Level.SEVERE, null, ex);
        }
           
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public static void updateAddress(int addressId, String address, String address2, int cityId, 
            String postalCode, String phone){
        
        Connection conn;
  
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            conn = DriverManager.getConnection(DB_URL, DBUSER, DBPASS);

            PreparedStatement ps = null;

            String sql = "UPDATE address SET address = ?, address2 = ?, cityId = ?, postalCode = ?, phone = ?, lastUpdate = ?, lastUpdateBy = ? WHERE addressId = ?";

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, address);
            ps.setString(2, address2);
            ps.setInt(3, cityId);
            ps.setString(4, postalCode);
            ps.setString(5, phone);
            //ps.setString(6, LocalDateTime.now().toString());
            ps.setTimestamp(6, java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));
            ps.setString(7, UserTable.currentUser);
            ps.setInt(8, addressId);
            ps.execute();
            System.out.println("Address #" + addressId + " updated");
        } catch (SQLException ex) {
            Logger.getLogger(AppointmentJoin.class.getName()).log(Level.SEVERE, null, ex);
        }
           
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public static void updateCustomer(int customerId, String customerName, int addressId){
        
        Connection conn;
  
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            conn = DriverManager.getConnection(DB_URL, DBUSER, DBPASS);

            PreparedStatement ps = null;

            String sql = "UPDATE customer SET customerName = ?, addressId = ?, lastUpdate = ?, lastUpdateBy = ? WHERE customerId = ?";

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, customerName);
            ps.setInt(2, addressId);
            //ps.setString(3, LocalDateTime.now().toString());
            ps.setTimestamp(3, java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));
            ps.setString(4, UserTable.currentUser);
            ps.setInt(5, customerId);
            ps.execute();
            System.out.println("Customer #" + customerId + " updated");
        } catch (SQLException ex) {
            Logger.getLogger(AppointmentJoin.class.getName()).log(Level.SEVERE, null, ex);
        }
           
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public static void setCustomerInactive(int customerId){
        
        Connection conn;
  
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            conn = DriverManager.getConnection(DB_URL, DBUSER, DBPASS);

            PreparedStatement ps = null;

            String sql = "UPDATE customer SET active = ?, lastUpdate = ?, lastUpdateBy = ? WHERE customerId = ?";

        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, 0);
            //ps.setString(2, LocalDateTime.now().toString());
            ps.setTimestamp(2, java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));
            ps.setString(3, UserTable.currentUser);
            ps.setInt(4, customerId);
            ps.execute();
            System.out.println("Customer #" + customerId + " set inactive");
        } catch (SQLException ex) {
            Logger.getLogger(AppointmentJoin.class.getName()).log(Level.SEVERE, null, ex);
        }
           
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public static void setAppointmentInactive(int appointmentId){
        
        Connection conn;
  
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            conn = DriverManager.getConnection(DB_URL, DBUSER, DBPASS);

            PreparedStatement ps = null;

            String sql = "UPDATE appointment SET title = ?, lastUpdate = ?, lastUpdateBy = ? WHERE appointmentId = ?";

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, "Inactive");
            //ps.setString(2, LocalDateTime.now().toString());
            ps.setTimestamp(2, java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));
            ps.setString(3, UserTable.currentUser);
            ps.setInt(4, appointmentId);
            ps.execute();
            System.out.println("Customer #" + appointmentId + " set inactive");
        } catch (SQLException ex) {
            Logger.getLogger(AppointmentJoin.class.getName()).log(Level.SEVERE, null, ex);
        }
           
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    
    
    public static void downloadCustomerJoin() {
        Connection conn;
        
        boolean res = false;
        ResultSet rs = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Download Customer Join...");
            conn = DriverManager.getConnection(DB_URL, DBUSER, DBPASS);

            Statement stmt;

            try {
                CustomerList.clear();
                stmt = conn.createStatement();
                rs = stmt.executeQuery(
                        "select customer.customerId, customer.customerName, customer.active, address.addressId, address.address,\n" +
                        "address.address2, address.postalCode, address.phone, city.cityId, city.city, country.countryId, country.country \n" +
                        "from customer\n" +
                        "	join address\n" +
                        "		on address.addressId = customer.addressId\n" +
                        "	join city\n" +
                        "		on city.cityId = address.cityId\n" +
                        "	join country	\n" +
                        "		on country.countryId = city.countryId");

                while (rs.next()) {
                    int customerId = parseInt(rs.getString(1));
                    String name = rs.getString(2);
                    int active = parseInt(rs.getString(3));
                    int addressId = parseInt(rs.getString(4));
                    String address = rs.getString(5);
                    String address2 = rs.getString(6);
                    String postalCode = rs.getString(7);
                    String phone = rs.getString(8);
                    int cityId = parseInt(rs.getString(9));
                    String city = rs.getString(10);
                    int countryId = parseInt(rs.getString(11));
                    String country = rs.getString(12);
                    //customerId, appointmentId, addressId, cityId, countryId, active, name, address, city, country, postalCode, phone           
                    if (active == 1){
                    CustomerJoin.addNew(customerId, 1, addressId, cityId, countryId, active, name, address, address2, city, country, postalCode, phone);
                    }    
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
        
    }
    public static void downloadAppointmentJoin() {
        Connection conn;
        
        boolean res = false;
        ResultSet rs = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Download Appointment Join...");
            conn = DriverManager.getConnection(DB_URL, DBUSER, DBPASS);

            Statement stmt;

            try {
                AppointmentList.clear();
                stmt = conn.createStatement();
                rs = stmt.executeQuery("select appointment.*, customer.customerName\n" +
                                       "from appointment\n" +
                                        "join customer \n" +
                                        "on customer.customerId = appointment.customerId");

                while (rs.next()) {
                    int appointmentId = parseInt(rs.getString(1));
                    int customerId = parseInt(rs.getString(2));
                    String title = rs.getString(3);
                    String description = rs.getString(4);
                    String location = rs.getString(5);
                    String contact = rs.getString(6);
                    String url = rs.getString(7);
                    String createdBy = rs.getString(11);
                    
                    
                    //Parse "start" date/time
                    
                    String str_start = rs.getString(8).substring(0, 16);
                    String str_end = rs.getString(9).substring(0, 16);
                    
                    
                    //convert start timestamp from String to ZonedDateTime, convert from UTC to local
                    DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm");
                    ZoneId utcZid = ZoneId.of("UTC");
                    ZoneId localZid = ZoneId.systemDefault();
                    
                    LocalDateTime ldtStart = LocalDateTime.parse(str_start, df);
                    ZonedDateTime utcZDTStart = ldtStart.atZone(utcZid);
                    ZonedDateTime localZDTStart = utcZDTStart.withZoneSameInstant(localZid);
                                        
                    //convert end timestamp from String to ZonedDateTime, convert from UTC to local
                    LocalDateTime ldtEnd = LocalDateTime.parse(str_end, df);
                    
                    ZonedDateTime utcZDTEnd = ldtEnd.atZone(utcZid);
                    ZonedDateTime localZDTEnd = utcZDTEnd.withZoneSameInstant(localZid);
                                      
                    String customerName = rs.getString(14);
                    if (!title.equals("Inactive")){                    
                    
                    AppointmentJoin.addNew(appointmentId, customerId, customerName, 2, title, description, location, contact, url, localZDTStart, localZDTEnd, createdBy);
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
        
    }
    
    public static void downloadAddressTable() {
        Connection conn;
        
        boolean res = false;
        ResultSet rs = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Download Address Table...");
            conn = DriverManager.getConnection(DB_URL, DBUSER, DBPASS);

            Statement stmt;

            try {
                AddressTableList.clear();
                stmt = conn.createStatement();
                rs = stmt.executeQuery( "select * from address");

                while (rs.next()) {
                    int addressId = parseInt(rs.getString(1));
                    String address = rs.getString(2);
                    String address2 = rs.getString(3);
                    int cityId = parseInt(rs.getString(4));
                    String postalCode = rs.getString(5);
                    String phone = rs.getString(6);
                    
                    //int addressId, int cityId, String address, String address2, String postalCode, String phone
                    AddressTable.addNew(addressId, cityId, address, address2, postalCode, phone);
                    
                    
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
        
    }
    
    public static void deleteAppointment(int appId ){
        
        Connection conn;
  
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            conn = DriverManager.getConnection(DB_URL, DBUSER, DBPASS);

            PreparedStatement ps = null;

            String sql = "DELETE from appointment WHERE appointmentId = ?";

        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, appId);
            
            ps.execute();
            System.out.println("Appointment APPID#" + appId + " deleted");
        } catch (SQLException ex) {
            Logger.getLogger(AppointmentJoin.class.getName()).log(Level.SEVERE, null, ex);
        }
           
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public static void downloadUserTable() {
        Connection conn;
        boolean res = false;
        ResultSet rs = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Download User Table...");
            conn = DriverManager.getConnection(DB_URL, DBUSER, DBPASS);

            Statement stmt;

            try {
                UserList.clear();
                stmt = conn.createStatement();
                rs = stmt.executeQuery("SELECT * FROM user");

                while (rs.next()) {
                    int userId = parseInt(rs.getString(1));
                    String userName = rs.getString(2);
                    String password = rs.getString(3);
                    int active = parseInt(rs.getString(4));
                    String createBy = rs.getString(5);
                    
                    //Parse "create" date
                    String str_create = rs.getString(6);
                    int create_year = Integer.parseInt(str_create.substring(0, 4));
                    int create_month = Integer.parseInt(str_create.substring(5, 7));
                    int create_day = Integer.parseInt(str_create.substring(8, 10));
                    LocalDate createDate = LocalDate.of(create_year, create_month, create_day);
                    
                    
                    //Parse "modify" date
                    String str_modify = rs.getString(7);
                    int modify_year = Integer.parseInt(str_modify.substring(0, 4));
                    int modify_month = Integer.parseInt(str_modify.substring(5, 7));
                    int modify_day = Integer.parseInt(str_modify.substring(8, 10));
                    LocalDate modifyDate = LocalDate.of(modify_year, modify_month, modify_day);
                    String modifyBy = rs.getString(8);
                    //(int userId, String userName, String password, int active, LocalDate createDate, 
                    //String createdBy, LocalDate modifyDate, String modifyBy)
                    UserTable.addNew(userId, userName, password, active, createDate, createBy, modifyDate, modifyBy);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
        
    }
    
    public static void downloadCityTable() {
        Connection conn;
        boolean res = false;
        ResultSet rs = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Download City Table...");
            conn = DriverManager.getConnection(DB_URL, DBUSER, DBPASS);

            Statement stmt;

            try {
                CityTableList.clear();
                stmt = conn.createStatement();
                rs = stmt.executeQuery("SELECT * FROM city");

                while (rs.next()) {
                    int cityId = parseInt(rs.getString(1));
                    String city = rs.getString(2);
                    int countryId = parseInt(rs.getString(3));
                    
                    CityTable.addNew(cityId, countryId, city);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
        
    }
    
    public static void downloadCountryTable() {
        Connection conn;
        ResultSet rs = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Download Country Table...");
            conn = DriverManager.getConnection(DB_URL, DBUSER, DBPASS);

            Statement stmt;

            try {
                CountryTableList.clear();
                stmt = conn.createStatement();
                rs = stmt.executeQuery("SELECT * FROM country");

                while (rs.next()) {
                    int countryId = parseInt(rs.getString(1));
                    String country = rs.getString(2);
                    CountryTable.addNew(countryId, country);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
        
    }
    public static boolean login(String username, String password) {
        Connection conn;
        boolean login = false;
        ResultSet rs = null;

        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, DBUSER, DBPASS);

            PreparedStatement ps = null;
            
        
            try {

                String sql = "SELECT * FROM user WHERE username = ? and password = ?";

                ps = conn.prepareStatement(sql);
                ps.setString(1, username);
                ps.setString(2, password);
                
                rs = ps.executeQuery();
                
                while (rs.next()) {
                    login = true;
                    System.out.println("Login Success!");
                }
                
                
                
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

           
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return login;
    }
    
}
