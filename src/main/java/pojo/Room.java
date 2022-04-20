package pojo;

import java.io.Serializable;

public class Room implements Serializable {

    private Integer roomId;

    private boolean isOrder;

    private String checkInDate;

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public boolean getIsOrder() {
        return  isOrder;
    }

    public void setIsOrder(boolean isOrder) {
        this.isOrder = isOrder;
    }

    public String getCheckInDateDate() {
        return checkInDate;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    @Override
    public String toString () {
        return  "roomId: " + roomId + " isOrder" + isOrder + " checkInDate" + checkInDate;
    }
}
