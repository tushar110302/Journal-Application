package net.engineeringdigest.journalApp.service;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.entity.JournalEntity;
import net.engineeringdigest.journalApp.entity.UserEntity;
import net.engineeringdigest.journalApp.repository.JournalRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class JournalService {

    @Autowired
    private JournalRepository journalRepository;

    @Autowired
    private UserService userService;


    @Transactional
    public void saveEntry(JournalEntity entity, String username){
        UserEntity user = userService.findbyUsername(username);
        entity.setDate(LocalDateTime.now());
        JournalEntity saved = journalRepository.save(entity);
        user.getJournals().add(saved);
        userService.saveEntry(user);
    }

    public void saveEntry(JournalEntity entity){
        journalRepository.save(entity);
    }

    public List<JournalEntity> showEntries() {
        return journalRepository.findAll();
    }

    public Optional<JournalEntity> findById(ObjectId myId){
        return journalRepository.findById(myId);
    }

    @Transactional
    public void deleteById(ObjectId myId, String username){
        boolean removed = false;
        try {
            UserEntity user = userService.findbyUsername(username);
            removed = user.getJournals().removeIf(i -> i.getId().equals(myId));
            if (removed) {
                userService.saveEntry(user);
                journalRepository.deleteById(myId);
            }
        }
        catch (Exception e){
            log.info("Error Occurred while deleting",e);
        }
    }
}
