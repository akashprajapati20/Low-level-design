package com.roombooking.model;

import java.util.Objects;

/** Immutable employee entity. */
public final class Employee {

    private final String id;
    private final String name;
    private final String email;

    public Employee(String id, String name, String email) {
        this.id = Objects.requireNonNull(id, "id");
        this.name = name;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "Employee{" + id + ", " + name + "}";
    }
}
