package com.horizon.bartersys.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.horizon.bartersys.entities.enums.TradeStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
public class Trade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "initiator_id")
    private User initiator;

    @ManyToOne
    @JoinColumn(name = "target_user_id")
    private User targetUser;

    @ManyToMany
    @JoinTable(name = "trade_offered_items",
               joinColumns = @JoinColumn(name = "trade_id"),
               inverseJoinColumns = @JoinColumn(name = "item_id"))
    private List<Item> offeredItems = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "trade_requested_items",
               joinColumns = @JoinColumn(name = "trade_id"),
               inverseJoinColumns = @JoinColumn(name = "item_id"))
    private List<Item> requestedItems = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TradeStatus status;

    @Column(nullable = false)
    private LocalDateTime initiationTime;

    private LocalDateTime completionTime;

    public Trade() {
        this.initiationTime = LocalDateTime.now();
        this.status = TradeStatus.PENDING;
    }

    public Long getId() {
        return id;
    }

    public User getInitiator() {
        return initiator;
    }

    public void setInitiator(User initiator) {
        this.initiator = initiator;
    }

    public User getTargetUser() {
        return targetUser;
    }

    public void setTargetUser(User targetUser) {
        this.targetUser = targetUser;
    }

    public List<Item> getOfferedItems() {
        return offeredItems;
    }

    public void setOfferedItems(List<Item> offeredItems) {
        this.offeredItems = offeredItems;
    }

    public List<Item> getRequestedItems() {
        return requestedItems;
    }

    public void setRequestedItems(List<Item> requestedItems) {
        this.requestedItems = requestedItems;
    }

    public TradeStatus getStatus() {
        return status;
    }

    public void setStatus(TradeStatus status) {
        this.status = status;
    }

    public LocalDateTime getInitiationTime() {
        return initiationTime;
    }

    public void setInitiationTime(LocalDateTime initiationTime) {
        this.initiationTime = initiationTime;
    }

    public LocalDateTime getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(LocalDateTime completionTime) {
        this.completionTime = completionTime;
    }
}

