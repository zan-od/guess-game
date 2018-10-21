package com.example.guessgame.controller;

import com.example.guessgame.model.User;
import com.example.guessgame.service.SecurityService;
import com.example.guessgame.service.UserService;
import com.example.guessgame.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class UserController {

    private UserService userService;
    private SecurityService securityService;
    private UserValidator userValidator;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setSecurityService(SecurityService securityService){
        this.securityService = securityService;
    }

    @Autowired
    public void setUserValidator(UserValidator userValidator){
        this.userValidator = userValidator;
    }

    @GetMapping("/registration")
    public String registration(Model model) {

        model.addAttribute("userForm", new User());

        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@Valid @ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()){
            return "registration";
        }

        userService.saveUser(userForm);

        securityService.autoLogin(userForm.getUsername(), userForm.getConfirmPassword());

        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (error != null) {
            model.addAttribute("error", "Username or password is incorrect!");
        }

        if (logout != null) {
            model.addAttribute("message", "Logged out successfully!");
        }

        return "login";
    }

}
