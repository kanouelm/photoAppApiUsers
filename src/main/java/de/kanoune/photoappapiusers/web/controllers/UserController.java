package de.kanoune.photoappapiusers.web.controllers;

import de.kanoune.photoappapiusers.model.api.UserDTO;
import de.kanoune.photoappapiusers.model.rest.request.UserRequest;
import de.kanoune.photoappapiusers.services.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private Environment environment;

    @Autowired
    private UserService userService;

    @GetMapping("/status/check")
    public String getStatus() {

        return "PhotoAppApiUsers service is working and is running on the port " + environment.getProperty("local.server.port");
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody  UserRequest requestedUserDetails) {

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserDTO userDTO = modelMapper.map(requestedUserDetails, UserDTO.class);
        userService.createUser(userDTO);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
