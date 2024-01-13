package com.horizon.bartersys.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.horizon.bartersys.config.SessionManagement;
import com.horizon.bartersys.entities.Item;
import com.horizon.bartersys.entities.Trade;
import com.horizon.bartersys.entities.User;
import com.horizon.bartersys.services.ItemService;
import com.horizon.bartersys.services.TradeService;
import com.horizon.bartersys.services.UserDetail;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserDetail userService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private TradeService tradeService;

    @GetMapping("")
    public String showCurrentUserProfile(Model model) {
        User currentUser = userService.getUserByUsername(SessionManagement.getCurrentLoggedUser());
        List<Item> userItems = itemService.getItemsByUser(currentUser);
        List<Trade> userTrades = tradeService.getTradesByUser(currentUser);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("user", currentUser);
        model.addAttribute("items", userItems);
        model.addAttribute("trades", userTrades);
        return "user/profile";
    }

    @GetMapping("/{username}")
    public String showUserProfile(@PathVariable String username, Model model) {
        User currentUser = userService.getUserByUsername(SessionManagement.getCurrentLoggedUser());
        User user = userService.getUserByUsername(username);
        if(!username.equals(currentUser.getUsername()) && user != null){
            List<Item> userItems = itemService.getItemsByUser(user);
            model.addAttribute("currentUser", currentUser);
            model.addAttribute("user", user);
            model.addAttribute("items", userItems);

            List<Trade> mutualTrades = tradeService.getMutualTrades(currentUser, user);
            model.addAttribute("trades", mutualTrades);
            return "user/profile";
        }else{
            return "redirect:/user";
        }
    }
    

    @GetMapping("/search")
    public String searchUsers(@RequestParam(name = "query", required = false) String query, Model model) {
        User currentUser = userService.getUserByUsername(SessionManagement.getCurrentLoggedUser());
        List<User> userList = userService.getUsersByUsernameOrFullName(query);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("users", userList);
        return "user/search-result";
    }
}



