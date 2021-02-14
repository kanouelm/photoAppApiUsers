package de.kanoune.photoappapiusers.model.rest.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserRequest {

    @NotNull(message = "First name required")
    @Size(min = 2, max = 20, message = "First name must be between 2 and 20 characters long")
    private String firstName;

    @NotNull(message = "Last name required")
    @Size(min = 2, max = 20, message = "Last name must be between 2 and 20 characters long")
    private String lastName;

    @NotNull(message = "Password required")
    @Size(min=8, max=16, message = "Password must be between 2 and 16 characters long")
    private String password;

    @NotNull(message = "Email required")
    @Email
    private String email;
}
