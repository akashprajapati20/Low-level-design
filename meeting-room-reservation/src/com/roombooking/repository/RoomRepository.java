package com.roombooking.repository;

import com.roombooking.model.Room;

import java.util.List;
import java.util.Optional;

public interface RoomRepository {

    Optional<Room> findById(String roomId);

    List<Room> findAll();
}
