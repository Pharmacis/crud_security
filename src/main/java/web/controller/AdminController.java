package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.UserService;

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
        return "redirect:/";
    }

    @GetMapping("/add")
    public String getAddForm(){
        return "add";
    }

    @PostMapping ("/add")
    public String addUser(@ModelAttribute("user") User user){
        userService.add (user);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String getDeleteForm (@PathVariable ("id") Long id,Model model){
        model.addAttribute ("user",userService.getUserById (id));
        return "edit";
    }
    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable ("id") Long id,Model model){
        userService.deleteById (id);
        return "redirect:/";
    }

}
