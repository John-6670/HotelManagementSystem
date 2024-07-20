package models.interfaces;

import models.room.Room;

import java.util.List;

public interface RoomManage {
    void updateRoom(Room room);
    List<Room> searchRoom();
}
