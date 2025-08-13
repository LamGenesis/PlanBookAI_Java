package com.planbookai.authservice.dto;

import lombok.Data;
import java.util.Set;
import com.planbookai.authservice.entity.Role;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String email;
    private Set<Role> roles;
}
