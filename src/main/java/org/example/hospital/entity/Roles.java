package org.example.hospital.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Roles implements GrantedAuthority {
    admin,
    doctor,
    patient,
    nurse;

    @Override
    public String getAuthority() {
        return name();
    }
}
