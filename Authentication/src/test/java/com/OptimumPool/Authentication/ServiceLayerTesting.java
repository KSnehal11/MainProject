package com.OptimumPool.Authentication;

import com.OptimumPool.Authentication.Model.User;
import com.OptimumPool.Authentication.Repository.UserRepo;
import com.OptimumPool.Authentication.Service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ServiceLayerTesting {
    UserRepo repo = mock(UserRepo.class);
    @InjectMocks
    private UserService service;
    private User u;

    @BeforeEach
    public void setup(){
        u = new User(1111, "test", "test", 1234567890);
    }

    @AfterEach
    public void tearDown(){
        u = null;
    }

    @Test
    @DisplayName("Check Save Success")
    public void givenUserToSaveReturnedSavedUserSuccess()
    {
        when(repo.findById(u.getId())).thenReturn(Optional.ofNullable(null));
        when(repo.save(any())).thenReturn(u);
        assertEquals(u,service.addUser(u));
        verify(repo,times(1)).save(any());
        verify(repo,times(0)).findById(any());
    }
}
