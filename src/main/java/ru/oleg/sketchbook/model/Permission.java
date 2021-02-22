package ru.oleg.sketchbook.model;

public enum Permission {
    USER_READ("user:read"),
    USER_WRITE("user:write");

    private String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission(){
        return permission;
    }
}
