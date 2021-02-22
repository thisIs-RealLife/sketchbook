package ru.oleg.sketchbook.model;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Role {
    USER(Arrays.asList(Permission.USER_READ)),
    ADMIN(Arrays.asList(Permission.USER_READ, Permission.USER_WRITE));

    private final List<Permission> permissionList;

    Role(List<Permission> permissionList) {
        this.permissionList = permissionList;
    }

    public List<Permission> getPermissionList() {
        return permissionList;
    }

    public List<SimpleGrantedAuthority> getAuthorities(){
        return permissionList.stream().map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
    }
}
