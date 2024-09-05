package com.techelevator.controller;

import com.techelevator.dao.UserDao;
import com.techelevator.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(path = "/users")
@PreAuthorize("isAuthenticated()")
@CrossOrigin
@RestController

public class UserController {

    private UserDao userDao;

    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping()
    public List<User> getAllUsers(){
        return userDao.getUsers();
    }
}
