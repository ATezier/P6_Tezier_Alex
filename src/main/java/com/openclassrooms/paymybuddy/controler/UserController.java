package com.openclassrooms.paymybuddy.controler;

import com.openclassrooms.paymybuddy.configuration.SecurityConfiguration;
import com.openclassrooms.paymybuddy.dto.UserDto;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/home")
    public String registrationForm(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = SecurityConfiguration.getEmailFromAuthentication(authentication);
        double amount = 0;
        User user = userService.findUserByEmail(email);
        user.setPassword(null);
        model.addAttribute("user", user);
        model.addAttribute("amount", amount);
        return "home";
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "profile";
    }

    @PostMapping("/updateUser")
    public String updateUser(@ModelAttribute("user") UserDto user, BindingResult result, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = SecurityConfiguration.getEmailFromAuthentication(authentication);
        user.setId(userService.findUserByEmail(email).getUid());
    userService.updateUser(user);
    return "redirect:/home?success";
    }

    @PostMapping("/updateAmount")
    public String updateAmount(@ModelAttribute("amount") int amount, BindingResult result, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = SecurityConfiguration.getEmailFromAuthentication(authentication);
        userService.addMoney(amount, email);
        return "redirect:/home?success";
    }
}
