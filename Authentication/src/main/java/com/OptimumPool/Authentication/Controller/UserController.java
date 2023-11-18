package com.OptimumPool.Authentication.Controller;

import com.OptimumPool.Authentication.Exception.UserAlreadyExist;
import com.OptimumPool.Authentication.Model.User;
import com.OptimumPool.Authentication.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class UserController {
    @Autowired
    private UserService user;
    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody User u) throws UserAlreadyExist {
        try {
            User u11=user.addUser(u);
            return new ResponseEntity<>(u11,HttpStatus.CREATED);
        }
        catch(UserAlreadyExist uae)
        {
            throw new UserAlreadyExist();
        }
        catch(Exception e)
        {
            return new ResponseEntity<>(user.addUser(u),HttpStatus.INTERNAL_SERVER_ERROR);
        }
       // return new ResponseEntity<>(user.addUser(u),HttpStatus.CREATED);
    }
    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody User u)
    {
        return new ResponseEntity<>(user.Login(u), HttpStatus.CREATED);
    }
    @GetMapping("users")
    public ResponseEntity<?> getAllUser()
    {
        return new ResponseEntity<>(user.getAllUser() , HttpStatus.OK);
    }

    @GetMapping("users/token")
    public String getJwtToken(){
        user.sendDataToConsumer();
        return "Success";
    }



}
