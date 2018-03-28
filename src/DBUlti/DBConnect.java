/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBUlti;

import java.sql.Connection;
import java.sql.DriverManager;


/**
 *
 * @author Think
 */
public class DBConnect {

    public DBConnect() {
    }

    public static  Connection getConnection() throws Exception{
        String url = "jdbc:sqlserver://"+serverName+":"+portNumber+";databaseName="+dbName;
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        return DriverManager.getConnection(url,userID,password);
    }
    private static final String serverName = "192.168.137.1";
    private static final String portNumber = "1433";
    private static final String dbName = "SWE102";
    private static final String userID = "sa";
    private static final String password = "sa";
}
