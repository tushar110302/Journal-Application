package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.api.response.QuoteResponse;
import net.engineeringdigest.journalApp.entity.UserEntity;
import net.engineeringdigest.journalApp.service.QouteService;
import net.engineeringdigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserEntryControloler {

    @Autowired
    private UserService userService;

    @Autowired
    private QouteService qouteService;

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody UserEntity newUser){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserEntity presentInDB = userService.findbyUsername(username);
        presentInDB.setUsername(newUser.getUsername());
        presentInDB.setPassword(newUser.getPassword());
        userService.updateUser(presentInDB);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteEntity(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        userService.deleteByUsername(username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<?> greet(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        return new ResponseEntity<>("HI, "+authentication.getName() , HttpStatus.OK  );
        String qoutes = "";
        QuoteResponse qoute = qouteService.getQoute();
        if(qoute!=null){
            qoutes = qoute.getFullData().getData().get(0).getContent();
        }
        return new ResponseEntity<>("HI, "+authentication.getName() + " , " + qoutes, HttpStatus.OK  );
    }
}
