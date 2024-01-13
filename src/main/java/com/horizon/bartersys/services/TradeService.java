package com.horizon.bartersys.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.horizon.bartersys.entities.Item;
import com.horizon.bartersys.entities.Trade;
import com.horizon.bartersys.entities.User;
import com.horizon.bartersys.entities.enums.TradeStatus;
import com.horizon.bartersys.repositories.ItemRepository;
import com.horizon.bartersys.repositories.TradeRepository;

@Service
public class TradeService {
    @Autowired
    private TradeRepository tradeRepository;

    @Autowired
    private ItemRepository itemRepository;

    public List<Trade> getTradesByUser(User user){
        return tradeRepository.findTradesByUser(user);
    }

    public Trade getTradeByID (Long id){
        return tradeRepository.getReferenceById(id);
    }

    public List<Trade> getMutualTrades(User user1, User user2){
        return tradeRepository.findMutualTrades(user1, user2);
    }

    public Trade saveTrade(Trade trade) {
        return tradeRepository.save(trade);
    }

    public void cancelTrade(Long tradeId) {
        Trade trade = tradeRepository.findById(tradeId)
                .orElseThrow(() -> new RuntimeException("Trade not found"));

        if (trade.getStatus() == TradeStatus.PENDING) {
            trade.setStatus(TradeStatus.CANCELED);
            trade.setCompletionTime(LocalDateTime.now());
            tradeRepository.save(trade);
        } else {
            throw new RuntimeException("Invalid state to cancel trade");
        }
    }

    public void rejectTrade(Long tradeId) {
        Trade trade = tradeRepository.findById(tradeId)
                .orElseThrow(() -> new RuntimeException("Trade not found"));

        if (trade.getStatus() == TradeStatus.PENDING) {
            trade.setStatus(TradeStatus.REJECTED);
            trade.setCompletionTime(LocalDateTime.now());
            tradeRepository.save(trade);
        } else {
            throw new RuntimeException("Invalid state to cancel trade");
        }
    }

    public void acceptTrade(Long tradeId) {
        Trade trade = tradeRepository.findById(tradeId)
                .orElseThrow(() -> new RuntimeException("Trade not found"));
    
        if (trade.getStatus() == TradeStatus.PENDING) {
            trade.setStatus(TradeStatus.ACCEPTED);
            trade.setCompletionTime(LocalDateTime.now());
    
            List<Item> offeredItems = trade.getOfferedItems();
            updateItemOwnership(offeredItems, trade.getTargetUser());
    
            List<Item> requestedItems = trade.getRequestedItems();
            updateItemOwnership(requestedItems, trade.getInitiator());
    
            tradeRepository.save(trade);
        } else {
            throw new RuntimeException("Invalid state to accept trade");
        }
    }
    
    private void updateItemOwnership(List<Item> items, User newOwner) {
        for (Item item : items) {
            item.setUser(newOwner);
        }
        itemRepository.saveAll(items);
    }
    
}
