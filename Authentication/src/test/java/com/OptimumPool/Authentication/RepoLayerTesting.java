package com.OptimumPool.Authentication;

import com.OptimumPool.Authentication.Model.User;
import com.OptimumPool.Authentication.Repository.UserRepo;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RepoLayerTesting {
    @Autowired
    private UserRepo repo;
    private User user;

    @BeforeEach
    void setup(){
        user = new User(1111, "name", "pass", 1234567890 );
    }
    @AfterEach
    void tearDown(){
        user = null;
        repo.deleteAll();
    }

    @Test
    @DisplayName("Test case for saving User object")
    public void givenUserToSaveReturnUser(){
        repo.save(user);
        User u1 = repo.findById(user.getId()).get();
        assertNotNull(u1);
        assertEquals(u1.getId(),user.getId());
    }

}
