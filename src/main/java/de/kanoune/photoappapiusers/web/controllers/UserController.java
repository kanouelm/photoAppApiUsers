package de.kanoune.photoappapiusers.web.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @GetMapping("/status/check")
    public String getStatus() {
        return "PhotoAppApiUsers service is working!";
    }
}
