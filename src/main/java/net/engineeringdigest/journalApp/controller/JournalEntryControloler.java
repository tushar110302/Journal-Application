package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntity;
import net.engineeringdigest.journalApp.entity.UserEntity;
import net.engineeringdigest.journalApp.service.JournalService;
import net.engineeringdigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("journal")
public class JournalEntryControloler {

    @Autowired
    private JournalService journalService;

    @Autowired
    private UserService userService;

    @GetMapping("{username}")
    public ResponseEntity<?> getJournals(@PathVariable String username){
        UserEntity user = userService.findbyUsername(username);
        List<JournalEntity> entries = user.getJournals();
        if (entries!=null && !entries.isEmpty()){
            return new ResponseEntity<>(entries,HttpStatus.OK);
        }
        return new ResponseEntity<>(entries,HttpStatus.NOT_FOUND);
    }

    @PostMapping("{username}")
    public ResponseEntity<JournalEntity> addEntry(@RequestBody JournalEntity entity, @PathVariable String username){
        try {
            journalService.saveEntry(entity, username);
            return new ResponseEntity<>(entity, HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("id/{myId}")
    public ResponseEntity<JournalEntity> getSingle(@PathVariable ObjectId myId){
        Optional<JournalEntity> journalEntity = journalService.findById(myId);
        if (journalEntity.isPresent()){
            return new ResponseEntity<>(journalEntity.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{username}/{myId}")
    public ResponseEntity<?> deleteEntity(@PathVariable ObjectId myId,@PathVariable String username){
        journalService.deleteById(myId,username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PutMapping("id/{username}/{myId}")
    public ResponseEntity<JournalEntity> updateEntity(@PathVariable ObjectId myId, @RequestBody JournalEntity newEntity, @PathVariable String username){
        JournalEntity old = journalService.findById(myId).orElse(null);
        if (old != null){
            old.setTitle(newEntity.getTitle()!=null && !newEntity.getTitle().isEmpty() ? newEntity.getTitle() : old.getTitle());
            old.setContent(newEntity.getContent()!=null && !newEntity.getContent().isEmpty() ? newEntity.getContent() : old.getContent());
            journalService.saveEntry(old);
            return new ResponseEntity<>(old,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
