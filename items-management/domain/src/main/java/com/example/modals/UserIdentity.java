package com.example.modals;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserIdentity {
    private String fullName;
    private String username;
    private String email;
    private List<String> userRoles;


    public static UserIdentity of(String fullName, String username, String email, List<String> userRoles) {
        return new UserIdentity(fullName, username, email, userRoles);
    }

}
