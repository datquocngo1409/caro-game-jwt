package com.example.carogamejwt.service;

import com.example.carogamejwt.model.Room;

import java.util.List;

public interface RoomService {
    List<Room> findAll();

    Room findById(Long id);

    void createRoom(Room room);

    void updateRoom(Room room);

    void deleteRoom(Long id);
}
