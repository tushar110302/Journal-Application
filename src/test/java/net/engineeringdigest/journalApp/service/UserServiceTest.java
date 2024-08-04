package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

//    @Disabled
    @CsvSource({
            "tr",
            "test",
            "admin",
            "ad"
    })
//    @Test
    @ParameterizedTest
    public void testByUsername(String name){
//        assertEquals(6,2+2);
        assertTrue(userRepository.findByUsername(name).getJournals().isEmpty());
    }

    @ParameterizedTest
    @CsvSource({
            "1,1,2",
            "2,2,3",
            "5,7,12"
    })
    public void testExpr(int a,int b, int expected){
        assertEquals(expected, a+b);
    }
}
