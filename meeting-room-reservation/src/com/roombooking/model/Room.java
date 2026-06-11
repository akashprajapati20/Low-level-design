package com.roombooking.model;

import java.util.Objects;

/** Immutable meeting-room entity. */
public final class Room {

    private final String id;
    private final String name;
    private final int capacity;
    private final String location;

    public Room(String id, String name, int capacity, String location) {
        this.id = Objects.requireNonNull(id, "id");
        this.name = name;
        this.capacity = capacity;
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return "Room{" + id + ", " + name + ", cap=" + capacity + "}";
    }
}
