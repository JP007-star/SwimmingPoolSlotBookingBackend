package com.smile.swimmingpool.controller;

import com.smile.swimmingpool.entity.User;
import com.smile.swimmingpool.service.UserRegistrationDto;
import com.smile.swimmingpool.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class MainController {
    @Autowired
    private UserService userService;
    //This function is used to register a user
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") UserRegistrationDto registrationDto, HttpSession session) {
        userService.save(registrationDto);
        System.out.println(registrationDto);
        return "redirect:/login?success";
    }
    @GetMapping("/")
    public ResponseEntity<?> index() {
        return ResponseEntity.ok().body(userService.findAll());
    }

}
