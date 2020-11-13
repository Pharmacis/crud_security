package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import web.model.User;
import web.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/admin/**")
public class AdminController {
    private UserService userService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    @Autowired
    public void setbCryptPasswordEncoder(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping("/")
    public String getUsers(Model model){
        model.addAttribute ("list_users",userService.listUsers ());
        return "index";
    }

    @GetMapping("/edit/{id}")
    public String getEditForm(@PathVariable("id") Long id, Model model){
        model.addAttribute ("user",userService.getUserById (id));
        return "edit";
    }

    @PostMapping("/edit/{id}")
    public String editUser (@ModelAttribute("user") User user){
        userService.update (user);
        return "redirect:/admin";
    }

    @GetMapping("/add")
    public String getAddForm(Model model){
        model.addAttribute("ROLES", userService.getRoles ());
        return "add";
    }

    @PostMapping ("/add")
    public String addUser (Model model,@ModelAttribute("user") User user,@RequestParam List<String> rolesValues) {
        userService.add (user);
        userService.addListOfRolesForUser (user, rolesValues);
        model.addAttribute ("ROLES", userService.getRoles ());
        return "redirect:/admin";
    }

    @GetMapping("/delete/{id}")
    public String getDeleteForm (@PathVariable ("id") Long id){
        userService.deleteById (id);
        return "redirect:/admin";
   }
}
