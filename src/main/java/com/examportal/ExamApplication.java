package com.examportal;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.examportal.entity.Role;
import com.examportal.entity.User;
import com.examportal.entity.UserRole;
import com.examportal.helper.exception.UserFoundException;
import com.examportal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class ExamApplication implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public ExamApplication(UserService userService) {
        this.userService = userService;
    }

    public static void main(String[] args) {
        SpringApplication.run(ExamApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("-=-=--=-=-=-=-\nExam Portal Loading\n-=-=--=-=-=-=-\n\n");

       /* try {
            User user = new User();
            user.setUsername("Akash");
            user.setFirstName("Akash");
            user.setLastName("Payal");
            user.setEmail("akash@gmail.com");
            user.setProfile("default.png");
            user.setPassword(this.bCryptPasswordEncoder.encode("Anshu@30"));
            Role role = new Role();
            role.setRoleId(44L);
            role.setRoleName("Admin");
            // roles mapped
            UserRole userRole = new UserRole();
            userRole.setUser(user);
            userRole.setRole(role);
            Set<UserRole> userRoleSet = new HashSet<>();
            userRoleSet.add(userRole);

            userService.createUser(user, userRoleSet);
        } catch (UserFoundException e) {
            e.printStackTrace();
        }*/
    }
}
