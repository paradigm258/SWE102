/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Think
 */
public class Booking {
    int trip_id;
    User booker;
    String pickup;
    String dropoff;

    public Booking(int trip_id, User booker, String pickup, String dropoff) {
        this.trip_id = trip_id;
        this.booker = booker;
        this.pickup = pickup;
        this.dropoff = dropoff;
    }

    

    

    public int getTrip_id() {
        return trip_id;
    }

    public void setTrip_id(int trip_id) {
        this.trip_id = trip_id;
    }

    public String getPickup() {
        return pickup;
    }

    public void setPickup(String pickup) {
        this.pickup = pickup;
    }

    public String getDropoff() {
        return dropoff;
    }

    public void setDropoff(String dropoff) {
        this.dropoff = dropoff;
    }

    public User getBooker() {
        return booker;
    }

    public void setBooker(User booker) {
        this.booker = booker;
    }

    
    
}
