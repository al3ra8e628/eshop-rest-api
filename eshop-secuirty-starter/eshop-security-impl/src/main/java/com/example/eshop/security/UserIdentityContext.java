package com.example.eshop.security;


import com.example.eshop.models.UserIdentity;

public class UserIdentityContext {
    private static final ThreadLocal<UserIdentity> userIdentityContext = new ThreadLocal<>();

    public static void setCurrentIdentity(UserIdentity userIdentity) {
        userIdentityContext.set(userIdentity);
    }

    public static UserIdentity getCurrentIdentity() {
        return userIdentityContext.get();
    }

}
