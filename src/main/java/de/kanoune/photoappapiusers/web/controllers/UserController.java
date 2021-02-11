package de.kanoune.photoappapiusers.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private Environment environment;
    @GetMapping("/status/check")
    public String getStatus() {

        return "PhotoAppApiUsers service is working and is running on the port " + environment.getProperty("local.server.port");
    }
}
