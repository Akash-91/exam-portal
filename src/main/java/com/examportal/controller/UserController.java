package com.examportal.controller;

import com.examportal.entity.*;
import com.examportal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    // Creating User
    @PostMapping("/")
    public Optional<User> createUser(@RequestBody User user) throws Exception {
        // role predefined for all incoming user via this service
        user.setUsername(user.getUsername());
        user.setProfile("default.png");
        // encoding Password
        user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));
        Role role = new Role();
        role.setRoleId(45L);
        role.setRoleName("Normal");
        // roles mapped
        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(role);
        Set<UserRole> userRoleSet = new HashSet<>();
        userRoleSet.add(userRole);
        // call service
        return userService.createUser(user, userRoleSet);
    }

    //get user
    @GetMapping("/{userName}")
    public User getUser(@PathVariable("userName") String userName) {
        return this.userService.getUser(userName).get();
    }

    //Find all
    @GetMapping("/")
    public List<User> getUser() {
        return this.userService.getAllUsers();
    }


    // Delete user
    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        this.userService.deleteUser(id);
    }


    // Update user
    @PutMapping("/{id}")
    public Optional<User> updateUser(@PathVariable("id") Long id,
                                     @RequestBody User user) throws Exception {
        return this.userService.updateUser(id, user);
    }
}
