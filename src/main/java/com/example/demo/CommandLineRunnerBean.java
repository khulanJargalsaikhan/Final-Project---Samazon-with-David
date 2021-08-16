package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineRunnerBean implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    public void run(String...args){
        User admin = new User("admin", "admin", true);
        Role adminRole = new Role("admin", "ROLE_ADMIN");

        User user = new User("user", "user", true);
        Role userRole = new Role("user", "ROLE_USER");
        user.setAddress("1234 xxxxx st");
        user.setEmail("user@domain.com");
        user.setState("MD");
        user.setCity("Montgomery");
        user.setPhone("555-555-5555");

        userRepository.save(admin);
        roleRepository.save(adminRole);

        userRepository.save(user);
        roleRepository.save(userRole);
    }

}
