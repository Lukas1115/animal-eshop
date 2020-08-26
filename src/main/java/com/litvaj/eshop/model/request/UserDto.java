package com.litvaj.eshop.model.request;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

    @NotNull
    private String name;

    @NotNull
    private String password;

    private String matchingPassword;

    @NotNull
    private String email;
}
