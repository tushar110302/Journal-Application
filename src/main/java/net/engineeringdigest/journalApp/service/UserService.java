package net.engineeringdigest.journalApp.service;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.entity.UserEntity;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void saveEntry(UserEntity entity){
        userRepository.save(entity);
    }
    public void updateUser(UserEntity entity){
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        userRepository.save(entity);
    }

    public void saveUser(UserEntity entity){
        try {
            entity.setPassword(passwordEncoder.encode(entity.getPassword()));
            entity.setRoles(Arrays.asList("USER"));
            userRepository.save(entity);
        }
        catch (Exception e){
            log.info("User present", e);
        }
    }
    public List<UserEntity> showEntries(){
        return userRepository.findAll();
    }
    public Optional<UserEntity> findById(ObjectId myId){
        return userRepository.findById(myId);
    }
    public void deleteById(ObjectId myId){
        userRepository.deleteById(myId);
    }
    public UserEntity findbyUsername(String username){
        return userRepository.findByUsername(username);
    }

    public UserEntity deleteByUsername(String username) {
        return userRepository.deleteByUsername(username);
    }

    public void saveAdmin(UserEntity entity) {
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        entity.setRoles(Arrays.asList("USER","ADMIN"));
        userRepository.save(entity);
    }
}
