package com.customer.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "custid")
    private long customerId;

    @Column(name = "fname",nullable = false)
    private String firstName;

    @Column(name = "pwd",nullable = false)
    private String password;

    @Column(name = "lname",nullable = false)
    private String lastName;

    @Column(name = "email",nullable = false,unique = true,updatable = false)
    private String email;

    @Column(name = "phone",unique = true,nullable = false)
    private String phoneNo;

    @Column(name = "otp",nullable = true)
    private long otp;

}
