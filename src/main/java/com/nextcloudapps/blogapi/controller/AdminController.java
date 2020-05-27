package com.nextcloudapps.blogapi.controller;

import com.nextcloudapps.blogapi.payload.UserProfile;
import com.nextcloudapps.blogapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<UserProfile>> getALlUsers() {
        List<UserProfile> profiles=userService.getAllUserProfile();
        return new ResponseEntity<List<UserProfile>>(profiles, HttpStatus.OK);
    }

    @GetMapping("/{username}/profile")
    public ResponseEntity<UserProfile> getUSerProfile(@PathVariable(value = "username") String username) {
        UserProfile userProfile = userService.getUserProfile(username);

        return new ResponseEntity<UserProfile>(userProfile, HttpStatus.OK);
    }
}
