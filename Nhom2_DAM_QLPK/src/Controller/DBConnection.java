
package Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnection {
    private static final String url="jdbc:sqlserver://localhost:1433;databaseName=Nhom2_DuAnMau;"+ "encrypt=true;trustServerCertificate=true;";
    private static final String username="nhom3";
    private static final String password="1";
    
    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(url,username,password);
    }
    public static void main(String[] args) throws SQLException {
        Connection con=getConnection();
        if(con!=null){
            System.out.println("Data Connect Successful");
        }else{
            System.out.println("Data connect fail");
        }
    }
}
