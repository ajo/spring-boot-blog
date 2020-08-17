package com.reljicd.controller;

import com.reljicd.model.User;
import com.reljicd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class RegistrationController {

    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/registration")
    public String registration(Model model) {

        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping(value = "/registration")
    public String createNewUser(@Valid User user,
                                BindingResult bindingResult,
                                Model model) {

        if (userService.findByEmail(user.getEmail()).isPresent()) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the email provided");
        }
        if (userService.findByUsername(user.getUsername()).isPresent()) {
            bindingResult
                    .rejectValue("username", "error.user",
                            "There is already a user registered with the username provided");
        }

        if (!bindingResult.hasErrors()) {
            // Registration successful, save user
            // Set user role to USER and set it as active
            userService.save(user);

            model.addAttribute("successMessage", "User has been registered successfully");
            model.addAttribute("user", new User());
        }

        return "registration";
    }
}
