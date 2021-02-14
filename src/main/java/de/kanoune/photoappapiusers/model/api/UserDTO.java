package de.kanoune.photoappapiusers.model.api;


import lombok.Data;

import java.io.Serializable;

@Data
public class UserDTO implements Serializable {

    private String userId;
    private String firstName;
    private String lastName;
    private String encryptedPassword;
    private String email;

}
