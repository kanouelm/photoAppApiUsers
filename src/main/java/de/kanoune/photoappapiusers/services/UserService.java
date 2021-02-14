package de.kanoune.photoappapiusers.services;

import de.kanoune.photoappapiusers.model.api.UserDTO;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    UserDTO createUser(UserDTO userDetails);
}
