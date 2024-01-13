package com.horizon.bartersys.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.horizon.bartersys.entities.Item;
import com.horizon.bartersys.entities.User;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByUserAndDeletedFalse(User user);
    @Query("SELECT i FROM Item i WHERE i.id = :id AND i.deleted = false")
    Optional<Item> findByIdAndDeletedFalse(@Param("id") Long id);

    @Query("SELECT i FROM Item i WHERE i.id IN :ids AND i.deleted = false")
    List<Item> findAllByIdAndDeletedFalse(@Param("ids") Iterable<Long> ids);
}
