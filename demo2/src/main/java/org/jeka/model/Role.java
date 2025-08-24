package org.jeka.model;

import java.util.Arrays;

public enum Role {

    USER("user"),
    ASSISTANT("assistant"),
    SYSTEM("system");

    private final String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public static Role fromString(String roleName) {
        if (roleName == null || roleName.trim().isEmpty()) {
            throw new IllegalArgumentException("Role name cannot be null or empty");
        }

        return Arrays.stream(Role.values())
                .filter(role -> role.role.equalsIgnoreCase(roleName.trim()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown role: '" + roleName + "'. Valid roles are: " +
                                Arrays.toString(Role.values())
                ));
    }

    @Override
    public String toString() {
        return role;
    }
}