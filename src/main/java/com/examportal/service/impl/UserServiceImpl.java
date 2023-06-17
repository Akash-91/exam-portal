package com.examportal.service.impl;

import com.examportal.entity.User;
import com.examportal.entity.UserRole;
import com.examportal.helper.exception.UserFoundException;
import com.examportal.repository.RoleRepository;
import com.examportal.repository.UserRepository;
import com.examportal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    // creating user
    @Override
    public Optional<User> createUser(User user, Set<UserRole> userRoles) throws Exception {
        Optional<User> local = checkIfUserPresent(user);
        if (!local.isEmpty()) {
            System.out.println("User is present, its: " + local.get().toString());
            throw new UserFoundException();
        } else {
            userRoles.stream().forEach(ur -> roleRepository.save(ur.getRole()));
            user.setUserRoles(userRoles);
            local = Optional.of(this.userRepository.save(user));
        }

        return local;
    }

    @Override
    public Optional<User> getUser(String userName) {
        return this.userRepository.findByUsername(userName);
    }

    @Override
    public void deleteUser(Long id) {
        this.userRepository.deleteById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    @Override
    public Optional<User> updateUser(Long id, User user) throws Exception {
        Optional<User> u = Optional.of(this.userRepository.getOne(id));
        if (u.isPresent()) {
            User userToUpdate = u.get();
            userToUpdate.setFirstName(user.getFirstName());
            userToUpdate.setLastName(user.getLastName());
        } else
            System.out.println("Username not found in DB");
        return Optional.of(this.userRepository.save(user));
    }

    private Optional<User> checkIfUserPresent(User user) {
        Optional<User> local = this.userRepository.findByUsername(user.getUsername());
        local.ifPresentOrElse(
                (value) -> {
                    System.out.println("User is present, its: "
                            + value.toString());
                },
                () -> {
                    System.out.println("Its a new User");
                });
        return local;
    }
}
