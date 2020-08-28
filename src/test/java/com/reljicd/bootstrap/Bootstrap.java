package com.reljicd.bootstrap;

import com.reljicd.config.TestCredentials;
import com.reljicd.model.Post;
import com.reljicd.model.Role;
import com.reljicd.model.User;
import com.reljicd.service.PostService;
import com.reljicd.service.RoleService;
import com.reljicd.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class Bootstrap implements CommandLineRunner {

    private Logger logger = LoggerFactory.getLogger(Bootstrap.class);

    private final UserService userService;
    private final PostService postService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public Bootstrap(UserService userService, PostService postService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.postService = postService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

        logger.info("=============== [Running Test Bootstrap] ===============");

        // Users & Roles
        logger.info("- Adding test users & roles.");
        User admin = new User();
        admin.setUsername(TestCredentials.ADMIN.getUsername());
        admin.setPassword(passwordEncoder.encode(TestCredentials.ADMIN.getPassword()));
        admin.setEnabled(true);
        admin.setEmail(TestCredentials.ADMIN.getUsername() + "@example.com");
        admin.setName(TestCredentials.ADMIN.getUsername());
        admin.setLastName(TestCredentials.ADMIN.getUsername());

        Role adminRole = new Role();
        adminRole.setRole("ROLE_ADMIN");
        roleService.saveAndFlush(adminRole);

        admin.setRoles(Arrays.asList(adminRole));
        userService.save(admin);

        adminRole.setUsers(Arrays.asList(admin));
        roleService.saveAndFlush(adminRole);

        // username:password is used as it is the default for @WithMockUser
        User account = new User();
        account.setUsername(TestCredentials.USER.getUsername());
        account.setPassword(passwordEncoder.encode(TestCredentials.USER.getPassword()));
        account.setEnabled(true);
        account.setEmail(TestCredentials.USER.getUsername() + "@example.com");
        account.setName(TestCredentials.USER.getUsername());
        account.setLastName(TestCredentials.USER.getUsername());

        Role userRole = new Role();
        userRole.setRole("ROLE_user");
        roleService.saveAndFlush(userRole);

        account.setRoles(Arrays.asList(userRole));
        userService.save(account);

        userRole.setUsers(Arrays.asList(account));
        roleService.saveAndFlush(userRole);

        // Posts
        logger.info("- Adding test posts.");

        Post adminPost = new Post();
        adminPost.setUser(admin);
        adminPost.setTitle("Test Post 1");
        adminPost.setBody("Lorem ipsum dolor sit amet, consectetur adipiscing elit, " +
                "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.");
        postService.save(adminPost);

        Post userPost = new Post();
        userPost.setUser(account);
        userPost.setTitle("Test Post 2");
        userPost.setBody("Lorem ipsum dolor sit amet, consectetur adipiscing elit, " +
                "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.");
        postService.save(userPost);

        logger.info("================= [Test Bootstrap Done] =================");
    }
}