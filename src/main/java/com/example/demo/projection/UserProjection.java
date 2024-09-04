package com.example.demo.projection;

import com.example.demo.enums.Role;

public interface UserProjection {
    long getId();

    String getFirstName();

    String getMiddleName();

    String getLastName();

    String getEmail();

    Role getRole();

    String getPhone();

    boolean isActive();

    int getBorrowedBookCount();
}
