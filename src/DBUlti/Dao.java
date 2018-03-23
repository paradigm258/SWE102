/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBUlti;

import java.sql.*;

/**
 *
 * @author Think
 */
public class Dao {

    public static void request(Model.Booking booking) throws Exception {
        Connection connection = DBConnect.getConnection();
        String sql = "insert into bookings values(?,?,?,?,?,?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, booking.getTrip_id());
        ps.setInt(2, booking.getBooker().getId());
        Date now = new Date(new java.util.Date().getTime());
        ps.setDate(3, now);
        ps.setDate(4, now);
        ps.setString(5, booking.getPickup());
        ps.setString(6, booking.getDropoff());
        ps.execute();
    }

    public static boolean changeUserData(Model.User user) throws Exception {
        java.sql.Connection connection = DBConnect.getConnection();
        String sql = "update users set name=?,email=?,phone=?,brand=?,plate=?,password=?"
                + " where id=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, user.getName());
        ps.setString(2, user.getEmail());
        ps.setString(3, user.getPhone());
        ps.setString(4, user.getBrand());
        ps.setString(5, user.getPlate());
        ps.setString(6, user.getPassword());
        ps.setInt(7, user.getId());
        ps.executeUpdate();
        return true;
    }

    public static Model.User getUserData(java.sql.ResultSet rs) throws SQLException {
        Model.User user = new Model.User();
        user.setId(rs.getInt(1));
        user.setName(rs.getString(2));
        user.setAvatar(rs.getString(3));
        user.setEmail(rs.getString(4));
        user.setPhone(rs.getString(5));
        user.setBrand(rs.getString("brand"));
        user.setPlate(rs.getString("plate"));
        user.setPassword(rs.getString("password"));
        return user;
    }

    public static boolean postToDataBase(Model.Trip post) throws Exception {
        String update = "insert into trips values(?,?,?,?,?,?,?,?,?,?,?)";
        java.sql.PreparedStatement ps = DBUlti.DBConnect.getConnection().prepareStatement(update);
        if (post.getDriverId() == null) {
            ps.setNull(1, java.sql.Types.INTEGER);
        } else {
            ps.setInt(1, post.getDriverId());
        }
        if (post.getPassangerId() == null) {
            ps.setNull(2, java.sql.Types.INTEGER);
        } else {
            ps.setInt(2, post.getPassangerId());
        }
        ps.setString(3, post.getType());
        ps.setString(4, post.getFrom());
        ps.setString(5, post.getTo());
        if (post.getTime() == null) {
            ps.setNull(6, java.sql.Types.DATE);
        } else {
            ps.setDate(6, new java.sql.Date(post.getTime().getTime()));
        }
        ps.setFloat(7, post.getPrice());
        ps.setString(8, post.getDescription());
        ps.setBoolean(9, post.getStatus());
        java.sql.Date now = new java.sql.Date((new java.util.Date()).getTime());
        ps.setDate(10, now);
        ps.setDate(11, now);
        ps.execute();
        return true;
    }

    public static java.util.List<Object[]> getUserPosts(Model.User user) {
        java.util.List<Object[]> rows = new java.util.ArrayList<>();
        try {
            String sql = "select * from trips where driver_id=? or passenger_id=?";
            java.sql.PreparedStatement ps = DBConnect.getConnection().prepareStatement(sql);
            ps.setInt(1, user.getId());
            ps.setInt(2, user.getId());
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Object[] singleRow = new Object[7];
                singleRow[0] = rs.getInt("id");
                singleRow[1] = rs.getString("from");
                singleRow[2] = rs.getString("to");
                singleRow[3] = rs.getString("type");
                singleRow[5] = rs.getDate("time");
                singleRow[6] = rs.getFloat("price");
                rows.add(singleRow);
            }
        } catch (Exception ex) {
            return rows;
        }
        return rows;
    }

    public static java.sql.ResultSet getBooking(int id) throws Exception {
        java.sql.Connection connection = DBUlti.DBConnect.getConnection();
        String sql = "select users.id,name,pickup,dropoff,phone from bookings \n"
                + "left join users on booker_id=id \n"
                + "left join trips on trip_id = trips.id\n"
                + "where trip_id=?";
        java.sql.PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        return ps.executeQuery();
    }

    public static boolean deletePost(int id) throws Exception {
        java.sql.Connection connection = DBConnect.getConnection();
        String sql = "delete from bookings where trip_id=?";
        java.sql.PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.execute();
        sql = "delete from trips where id=?";
        ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.execute();
        return true;
    }
}
