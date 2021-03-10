package de.kanoune.photoappapiusers.model.api;


import de.kanoune.photoappapiusers.model.rest.response.AlbumResponse;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserDTO implements Serializable {

    private String userId;
    private String firstName;
    private String lastName;
    private String password;
    private String encryptedPassword;
    private String email;
    private List<AlbumResponse> albumResponseList;

}
