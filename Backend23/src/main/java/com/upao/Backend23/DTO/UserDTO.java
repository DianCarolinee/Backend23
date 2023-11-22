package com.upao.Backend23.DTO;

import com.upao.Backend23.models.User;
import lombok.Getter;

@Getter
public class UserDTO {

    private final String firstName;
    private final String lastName;
    private final String email;

    public UserDTO(User user) {
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
    }


}
