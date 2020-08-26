package com.reljicd.bootstrap;

import com.reljicd.model.Post;
import com.reljicd.model.Role;
import com.reljicd.model.User;
import com.reljicd.service.PostService;
import com.reljicd.service.RoleService;
import com.reljicd.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Profile({"default", "demo"})
@Component
public class Bootstrap implements CommandLineRunner {

    private final UserService userService;
    private final PostService postService;
    private final RoleService roleService;
    private final Logger logger = LoggerFactory.getLogger(Bootstrap.class);
    private final PasswordEncoder passwordEncoder;

    public Bootstrap(UserService userService, PostService postService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.postService = postService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args){

        logger.info("=============== [Running Bootstrap] ===============");

        // Roles
        if (!roleService.findByRole("ROLE_USER").isPresent()) {
            logger.info("- Adding user role.");
            Role userRole = new Role();
            userRole.setRole("ROLE_USER");
            roleService.saveAndFlush(userRole);
        }

        if (!roleService.findByRole("ROLE_ADMIN").isPresent()) {
            logger.info("- Adding admin role.");
            Role adminRole = new Role();
            adminRole.setRole("ROLE_ADMIN");
            roleService.saveAndFlush(adminRole);
        }

        // Users
        if (userService.count() == 0) {
            logger.info("- Adding default admin account");
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin"));;
            admin.setEnabled(true);
            admin.setEmail("admin@example.com");
            admin.setName("admin");
            admin.setLastName("admin");
            userService.save(admin);

            Role adminRole = roleService.findByRole("ROLE_ADMIN").get();

            adminRole.setUsers(Arrays.asList(admin));
            roleService.saveAndFlush(adminRole);

            admin.setRoles(Arrays.asList(adminRole));
            userService.save(admin);

        }

        if (postService.count() == 0) {

            User admin = userService.findByUsername("admin").get();

            // Posts
            logger.info("- Adding default post.");
            Post article = new Post();
            article.setUser(admin);
            article.setTitle("Test Post 1");
            article.setBody("Lorem ipsum dolor sit amet, consectetur adipiscing elit, " +
                    "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.");
            postService.save(article);
        }
        logger.info("================= [Bootstrap Done] =================");
    }
}

