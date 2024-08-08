package com.example.demo.projection;

public interface UserProjection {
    long getId();

    String getFirstName();

    String getMiddleName();

    String getLastName();

    String getEmail();

    String getPhone();

    boolean isActive();
}
