/*
 * Handle authentication
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBUlti;

import Model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Think
 */
public class Authentication {

    Connection connection;

    public Authentication() throws Exception {
        this.connection = DBConnect.getConnection();
    }
    /**
     * 
     * @param email
     * @param password
     * @return boolean true if password and username is found in database
     * @throws SQLException 
     */
    public java.sql.ResultSet Login(String email,String password) throws SQLException
    {
        PreparedStatement ps = connection.prepareStatement("select * from users where email = ? and password = ?");
        ps.setString(1, email);
        ps.setString(2, password);
        ResultSet rs =  ps.executeQuery();
        return rs;
    }
    /**
     * 
     * @param user
     * @return true if
     * @throws SQLException 
     */
    public boolean Register(User user) throws SQLException{
        PreparedStatement ps = connection.prepareStatement("select * from users where email=?");
        ps.setString(1, user.getEmail());
        if(ps.executeQuery().next())return false;
        ps = connection.prepareStatement("insert into users values(?,?,?,?,?,?,?,?,?)"); 
        ps.setString(1, user.getName());
        ps.setString(2,user.getAvatar());
        ps.setString(3, user.getEmail());
        ps.setString(4, user.getPhone());
        ps.setString(5, user.getBrand());
        ps.setString(6, user.getPlate());
        ps.setString(7, user.getPassword());
        java.sql.Date create = new java.sql.Date(user.getCreate_at().getTime());
        ps.setDate(8, create);
        ps.setDate(9, create);
        ps.executeUpdate();
        return true;
    }
}
