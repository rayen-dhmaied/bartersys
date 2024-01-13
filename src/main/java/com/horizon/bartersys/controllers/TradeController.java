package com.horizon.bartersys.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
@RequestMapping("/trade")
public class TradeController {

    @Autowired
    private TradeService tradeService;

    @Autowired
    private UserDetail userService;

    @Autowired
    private ItemService itemService;

    @GetMapping("/create")
    public String showTradeForm(@RequestParam("targetUserId") Long targetUserId, Model model) {
        User initiator = userService.getUserByUsername(SessionManagement.getCurrentLoggedUser());
        User targetUser = userService.getUserById(targetUserId);

        if( initiator.getId() == targetUser.getId()){
            return "redirect:/user";
        }

        Trade trade = new Trade();
        trade.setInitiator(initiator);
        trade.setTargetUser(targetUser);

        model.addAttribute("trade", trade);

        List<Item> userItems = itemService.getItemsByUser(initiator);
        model.addAttribute("userItems", userItems);

        List<Item> targetUserItems = itemService.getItemsByUser(targetUser);
        model.addAttribute("targetUserItems", targetUserItems);

        model.addAttribute("currentUser", initiator);
        model.addAttribute("user", targetUser);

        return "trade/create";
    }

    @PostMapping("/create")
    public String createTrade(@ModelAttribute("trade") Trade trade, @RequestParam("offeredItemIds") List<Long> offeredItemIds, @RequestParam("requestedItemIds") List<Long> requestedItemIds, @RequestParam("initiatorId") Long initiatorId ,@RequestParam("targetUserId") Long targetUserId) {

        List<Item> offeredItems = itemService.getItemsByIds(offeredItemIds);
        List<Item> requestedItems = itemService.getItemsByIds(requestedItemIds);

        User currentUser = userService.getUserById(initiatorId);
        User targetUser = userService.getUserById(targetUserId);

        trade.setInitiator(currentUser);
        trade.setTargetUser(targetUser);

        trade.setOfferedItems(offeredItems);
        trade.setRequestedItems(requestedItems);

        tradeService.saveTrade(trade);

        return "redirect:/user";
    }

    @GetMapping("/{tradeID}")
    public String getTrade(@PathVariable Long tradeID, Model model) {
        Trade trade = tradeService.getTradeByID(tradeID);
        if (trade == null) return "redirect:/user";
        User currentUser = userService.getUserByUsername(SessionManagement.getCurrentLoggedUser());

        if(trade.getInitiator().getId() == currentUser.getId() || trade.getTargetUser().getId() == currentUser.getId()){
            model.addAttribute("currentUser", currentUser);
            model.addAttribute("trade", trade);
            return "trade/detail";
        }
        return "redirect:/user";
    }

    @PostMapping("/accept/{tradeId}")
    public String acceptTrade(@PathVariable Long tradeId) {
        User currentUser = userService.getUserByUsername(SessionManagement.getCurrentLoggedUser());
        if( currentUser.getId() == tradeService.getTradeByID(tradeId).getTargetUser().getId()){
                tradeService.acceptTrade(tradeId);
        }
        return "redirect:/user";
    }

    @PostMapping("/reject/{tradeId}")
    public String rejectTrade(@PathVariable Long tradeId) {
        User currentUser = userService.getUserByUsername(SessionManagement.getCurrentLoggedUser());
        if( currentUser.getId() == tradeService.getTradeByID(tradeId).getTargetUser().getId()){
                tradeService.rejectTrade(tradeId);
        }
        return "redirect:/user";
    }

    @PostMapping("/cancel/{tradeId}")
    public String cancelTrade(@PathVariable Long tradeId) {
        User currentUser = userService.getUserByUsername(SessionManagement.getCurrentLoggedUser());
        if( currentUser.getId() == tradeService.getTradeByID(tradeId).getInitiator().getId()){
                tradeService.cancelTrade(tradeId);
        }
        return "redirect:/user";
    }
    
}

