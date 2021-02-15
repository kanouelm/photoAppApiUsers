package de.kanoune.photoappapiusers.services;

import de.kanoune.photoappapiusers.model.api.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public interface UserService extends UserDetailsService {

    UserDTO createUser(UserDTO userDetails);
    UserDTO getUserDetailsByEmail(String email);
}
