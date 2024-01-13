package com.horizon.bartersys.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.horizon.bartersys.entities.Item;
import com.horizon.bartersys.entities.User;
import com.horizon.bartersys.repositories.ItemRepository;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;

    public List<Item> getItemsByUser(User user) {
        return itemRepository.findByUserAndDeletedFalse(user);
    }

    public List<Item> getItemsByIds(List<Long> itemIds) {
        return itemRepository.findAllByIdAndDeletedFalse(itemIds);
    }

    public Item getItemByID(Long id){
        return itemRepository.findByIdAndDeletedFalse(id).get();
    }

    public void addItem(Item item) {
        itemRepository.save(item);
    }

    public void deleteItem(Item item){
        item.setDeleted(true);

        itemRepository.save(item);
    }
}
