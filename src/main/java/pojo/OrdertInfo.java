package pojo;


import java.io.Serializable;
import java.util.Date;

public class OrdertInfo implements Serializable {


    private String guestName;

    private Room room;

    private Date orderDate;

    public OrdertInfo(String guestName) {
        this.guestName = guestName;
    }

    public OrdertInfo(String guestName, Date orderDate,Room room) {
        this.guestName = guestName;
        this.orderDate = orderDate;
        this.room = room;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    @Override
    public String toString () {
        return  "guestName: " + guestName + " orderDate: " + orderDate + " roomInfo" + room  ;
    }
}
