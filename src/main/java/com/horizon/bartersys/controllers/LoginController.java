package com.horizon.bartersys.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.horizon.bartersys.config.SessionManagement;
import com.horizon.bartersys.entities.User;
import com.horizon.bartersys.services.UserDetail;

@Controller
public class LoginController {

    @Autowired
    private UserDetail userService;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }
    
    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        try{
            SessionManagement.getCurrentLoggedUser();
            return "redirect:/user/profile";


        }catch (Exception e){
            model.addAttribute("user", new User());
            return "register";
        }
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addAttribute("error","");
            return "redirect:/register";
        }
        try {
            userService.saveUser(user);
        } catch (Exception e) {
            redirectAttributes.addAttribute("error","");
            return "redirect:/register";
        }
        redirectAttributes.addAttribute("success","");
        return "redirect:/login";
    }
    
}