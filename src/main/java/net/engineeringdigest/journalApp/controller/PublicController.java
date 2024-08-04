package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.UserEntity;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

//    @GetMapping("/get-all-users")
//    public ResponseEntity<?> getUser(){
//        return new ResponseEntity<>(userService.showEntries(), HttpStatus.OK);
//    }

    @PostMapping("/create-user")
    public ResponseEntity<UserEntity> addUser(@RequestBody UserEntity user){
        userService.saveUser(user);
//        userService.saveEntry(user);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }
}
