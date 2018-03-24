/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBUlti;

import Model.Booking;
import Model.Trip;
import Model.User;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Think
 */
public class Dao {

    public static void accept(int id) throws Exception {
        Connection connection = DBConnect.getConnection();
        String sql = "select * from bookings where id =? ";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        Model.Booking book = new Booking();
        if (rs.next()) {
            book.setBooker(getUserData(rs.getInt("booker_id")));
            book.setTrip_id(rs.getInt("trip_id"));
        } else {
            throw new Exception();
        }
        sql = "delete from bookings where trip_id=?";
        ps = connection.prepareStatement(sql);
        ps.setInt(1, book.getTrip_id());
        ps.execute();
        String update;
        Model.Trip trip = getTrip(book.getTrip_id());
        if (trip.getDriverId() == null) {
            update = "driver_id";
        } else {
            update = "passenger_id";
        }
        sql = "update trips set " + update + "=?,status=1 where id=?";
        ps = connection.prepareStatement(sql);
        ps.setInt(1, book.getBooker().getId());
        ps.setInt(2, book.getTrip_id());
        ps.execute();
        sql = "insert into notifications values(?,?)";
        System.out.println(sql);
        ps = connection.prepareStatement(sql);
        ps.setInt(1, book.getBooker().getId());
        ps.setString(2, trip.getType()+" from "+trip.getFrom()+" to "+trip.getTo()+"\nAccepted\nDescription:"+trip.getDescription());
        ps.execute();
    }

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

    public static Model.User getUserData(int id) throws Exception {
        java.sql.Connection connnection = DBConnect.getConnection();
        String sql = "select * from users where id=?";
        PreparedStatement ps = connnection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
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
        } else {
            return null;
        }
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

    public static User Login(String email, String password) throws Exception {
        Connection connection = DBConnect.getConnection();;
        PreparedStatement ps = connection.prepareStatement("select * from users where email = ? and password = ?");
        ps.setString(1, email);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return getUserData(rs.getInt("id"));
        } else {
            return null;
        }
    }

    public static boolean Register(User user) throws SQLException, Exception {
        Connection connection = DBConnect.getConnection();
        PreparedStatement ps = connection.prepareStatement("select * from users where email=?");
        ps.setString(1, user.getEmail());
        if (ps.executeQuery().next()) {
            return false;
        }
        ps = connection.prepareStatement("insert into users values(?,?,?,?,?,?,?,?,?)");
        ps.setString(1, user.getName());
        ps.setString(2, user.getAvatar());
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

    public static Model.Trip getTrip(int id) throws Exception {
        String update = "select * from trips where id=?";
        java.sql.PreparedStatement ps = DBUlti.DBConnect.getConnection().prepareStatement(update);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            Model.Trip trip = new Trip();
            trip.setTripId(rs.getInt("id"));
            trip.setDriverId(rs.getInt("driver_id"));
            trip.setPassangerId(rs.getInt("passenger_id"));
            trip.setFrom(rs.getString("from"));
            trip.setTo(rs.getString("to"));
            return trip;
        }
        return null;
    }

    public static java.util.List<Object[]> getUserPosts(Model.User user) {
        java.util.List<Object[]> rows = new java.util.ArrayList<>();
        try {
            String sql = "select * from trips where (driver_id=? or passenger_id=?) and status=0";
            Connection connection = DBConnect.getConnection();
            java.sql.PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, user.getId());
            ps.setInt(2, user.getId());

            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Object[] singleRow = new Object[7];
                singleRow[0] = rs.getInt("id");
                singleRow[1] = rs.getString("from");
                singleRow[2] = rs.getString("to");
                sql = "select count(booker_id) as total\n"
                        + "from bookings \n"
                        + "where trip_id=?\n"
                        + "group by bookings.booker_id";
                ps = connection.prepareStatement(sql);
                ps.setInt(1, rs.getInt("id"));
                ResultSet rs2 = ps.executeQuery();
                singleRow[4] = (rs2.next()?rs2.getInt(1):0);
                singleRow[3] = rs.getString("type");
                singleRow[5] = rs.getDate("time");
                singleRow[6] = rs.getFloat("price");
                rows.add(singleRow);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return rows;
        }
        return rows;
    }

    public static java.sql.ResultSet getBooking(int id) throws Exception {
        java.sql.Connection connection = DBUlti.DBConnect.getConnection();
        String sql = "select bookings.id,name,pickup,dropoff,phone from bookings \n"
                + "left join users on booker_id=users.id \n"
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

    public static List<Object[]> getUserHistory(int id) throws Exception {
        List<Object[]> list = new ArrayList<>();
        String sql = "select * from trips where passenger_id=? or driver_id=? and status=1";
        Connection connection = DBConnect.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.setInt(2, id);
        ResultSet rs = ps.executeQuery();
        System.out.println(id);
        while(rs.next()){
            System.out.println("i");
            Object[] trip = new Object[6];
            trip[0]=rs.getInt(1);
            int customer = rs.getInt(2)==id?rs.getInt(3):rs.getInt(2);
            trip[1]=getUserData(customer).getName();
            trip[2]=rs.getString(4);
            trip[3]=rs.getString(5);
            trip[4]=rs.getString(6);
            trip[5]=new SimpleDateFormat("dd-MM-yyyy").format(new java.util.Date(rs.getDate(7).getTime()));
            list.add(trip);
        }
        return list;
    }
    
    public static List<String> getNotifications(int id) throws Exception{
        List<String> list = new ArrayList<>();
        String sql = "select * from notifications where user_id=?";
        Connection connection = DBConnect.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            list.add(rs.getString(2));
        }
        return list;
    }
    public static void deleteNotification(int id) throws Exception{
        String sql = "delete from notifications where user_id=?";
        Connection connection = DBConnect.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.execute();
    }
}

