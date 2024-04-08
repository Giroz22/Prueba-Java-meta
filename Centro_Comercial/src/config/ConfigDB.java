package config;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConfigDB {
    public static Connection objConnection = null;

    public static boolean openConnection(){
        boolean isOpen = false;
        try{
            String url = "jdbc:mysql://localhost:3306/db_centro_comercial";
            String user = "root";
            String password = "";

            objConnection = (Connection) DriverManager.getConnection(url,user,password);
            isOpen  = true;
            System.out.println("Successful connection");
        }catch (SQLException e){
            System.err.println("Connection error: " + e.getMessage());
        }
        return isOpen;
    }

    public static boolean closeConnection(){
        boolean isClosed =  false;

        try{
            if(objConnection != null) objConnection.close();
            isClosed = true;
        }catch (SQLException e){
            System.err.println("Error close connection");
        }

        return isClosed;
    }
}
