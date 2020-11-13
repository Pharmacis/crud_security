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
    public String getUsers(Model model){
       // model.addAttribute("ROLES", userService.getRoles ());
        model.addAttribute("ROLES", Arrays.asList("ROLE_USER", "ROLE_ADMIN"));
        return "startPage";
    }

    @PostMapping("/")
    public String getStartPage (Model model,@ModelAttribute("user") User user,@RequestParam List<String> rolesValues){
        userService.add (user);
        userService.addListOfRolesForUser (user,rolesValues);
        model.addAttribute ("ROLES",userService.getRoles ());
        return "startPage";
    }

    @GetMapping("/access-denied")
    public String getAccessDenied(){
        return "accessDenied";
    }
    @GetMapping("/user")
    public String getUser(Model model){
        model.addAttribute("user", (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal());
        return "userPage";
    }




}
