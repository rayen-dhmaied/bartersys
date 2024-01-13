package com.horizon.bartersys.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.horizon.bartersys.entities.Trade;
import com.horizon.bartersys.entities.User;

import java.util.List;


public interface TradeRepository extends JpaRepository<Trade, Long> {
    @Query("SELECT t FROM Trade t WHERE t.initiator = :user OR t.targetUser = :user")
    List<Trade> findTradesByUser(@Param("user") User user);

    @Query("SELECT t FROM Trade t WHERE (t.initiator = :currentUser AND t.targetUser = :targetUser) OR (t.initiator = :targetUser AND t.targetUser = :currentUser)")
    List<Trade> findMutualTrades(@Param("currentUser") User currentUser, @Param("targetUser") User targetUser);
}
