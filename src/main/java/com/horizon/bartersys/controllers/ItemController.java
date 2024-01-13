package com.horizon.bartersys.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.horizon.bartersys.entities.Item;
import com.horizon.bartersys.entities.User;
import com.horizon.bartersys.services.ItemService;
import com.horizon.bartersys.services.UserDetail;
import com.horizon.bartersys.config.SessionManagement;

@Controller
@RequestMapping("/item")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @Autowired
    private UserDetail userService;

    @PostMapping("/add")
    public String addItem(@ModelAttribute("item") Item item, BindingResult result) {
        User currentUser = userService.getUserByUsername(SessionManagement.getCurrentLoggedUser());
        item.setUser(currentUser);

        itemService.addItem(item);

        return "redirect:/user/profile";
    }

    @PostMapping("/delete")
    public String deleteItem(@RequestParam("itemId") Long itemId) {
        User currentUser = userService.getUserByUsername(SessionManagement.getCurrentLoggedUser());
        Item item = itemService.getItemByID(itemId);
        if( item.getUser().getId() == currentUser.getId()){
            itemService.deleteItem(item);
        }
        return "redirect:/user/profile";
    }

}
