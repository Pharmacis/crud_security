package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.model.Role;
import web.model.User;
import web.service.UserService;
import web.service.UserServiceImp;

import java.util.*;

@Controller

public class HelloController {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String getUsers(){
        return "startPage";
    }

    @GetMapping("/access-denied")
    public String getAccessDenied(){
        return "accessDenied";
    }

    @GetMapping("/user")
    public String getUser(Model model){
        model.addAttribute("user",  SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal());
        return "userPage";
    }




}
