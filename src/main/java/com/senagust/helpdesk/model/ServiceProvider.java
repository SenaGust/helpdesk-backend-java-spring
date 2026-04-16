package com.senagust.helpdesk.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class ServiceProvider extends User {
    @ElementCollection
    @CollectionTable(
            name = "contractor_available_hours",
            joinColumns = @JoinColumn(name = "contractor_id")
    )
    @Column(name = "\"hour\"")
    private List<Integer> availableSlotHours;
}
