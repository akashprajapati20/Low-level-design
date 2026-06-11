package com.roombooking.repository.impl;

import com.roombooking.model.Room;
import com.roombooking.repository.RoomRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryRoomRepository implements RoomRepository {

    private final Map<String, Room> store = new ConcurrentHashMap<>();

    public InMemoryRoomRepository(Collection<Room> rooms) {
        for (Room room : rooms) {
            store.put(room.getId(), room);
        }
    }

    @Override
    public Optional<Room> findById(String roomId) {
        return Optional.ofNullable(store.get(roomId));
    }

    @Override
    public List<Room> findAll() {
        return new ArrayList<>(store.values());
    }
}
