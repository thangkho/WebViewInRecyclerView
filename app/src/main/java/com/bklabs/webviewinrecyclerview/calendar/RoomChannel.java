package com.bklabs.webviewinrecyclerview.calendar;

import java.io.Serializable;
import java.util.Date;

public class RoomChannel implements Serializable {
    int roomEmpty;
    int roomSell;
    Date date;

    public int getRoomEmpty() {
        return roomEmpty;
    }

    public void setRoomEmpty(int roomEmpty) {
        this.roomEmpty = roomEmpty;
    }

    public int getRoomSell() {
        return roomSell;
    }

    public void setRoomSell(int roomSell) {
        this.roomSell = roomSell;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
