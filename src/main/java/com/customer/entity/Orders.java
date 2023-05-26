package com.customer.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Getter
@Setter
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderid")
    private long orderId;

    @Column(name = "cust_id")
    private long customerId;

    @Column(name = "orderdate")
    private LocalDateTime date;

    public Orders(long customerId, LocalDateTime date) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.date = date;
    }
}
