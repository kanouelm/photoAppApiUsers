package de.kanoune.photoappapiusers.web.controllers;

import de.kanoune.photoappapiusers.model.api.UserDTO;
import de.kanoune.photoappapiusers.model.rest.request.UserRequest;
import de.kanoune.photoappapiusers.model.rest.response.UserResponse;
import de.kanoune.photoappapiusers.services.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

        return "Working on port " + environment.getProperty("local.server.port") + " , wiht token = " + environment.getProperty("token.secret");
    }

    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody  UserRequest requestedUserDetails) {

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserDTO userDTO = modelMapper.map(requestedUserDetails, UserDTO.class);
        userService.createUser(userDTO);

        UserResponse returnedValue = modelMapper.map(userDTO, UserResponse.class);

        return  ResponseEntity.status(HttpStatus.CREATED).body(returnedValue);
    }

    @GetMapping(value = "/{userId}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UserResponse> getUser(@PathVariable ("userId") String userId) {

        UserDTO userDTO = userService.getUserDetailsByUserId(userId);
        UserResponse returnedValue = new ModelMapper().map(userDTO, UserResponse.class);

        return ResponseEntity.status(HttpStatus.OK).body(returnedValue);
    }
}
