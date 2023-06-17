package com.examportal.service;

import com.examportal.entity.User;
import com.examportal.entity.UserRole;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserService {
    // Creating User
    Optional<User> createUser(User user, Set<UserRole> userRoles) throws Exception;

    Optional<User> getUser(String userName);

    void deleteUser(Long id);

    List<User> getAllUsers();

    Optional<User> updateUser(Long id, User user) throws Exception;

}

