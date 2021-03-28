package de.kanoune.photoappapiusers.model.rest.response;

import lombok.Data;

import java.util.List;

@Data
public class UserResponse {

    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private List<AlbumResponse> albumResponseList;
}
