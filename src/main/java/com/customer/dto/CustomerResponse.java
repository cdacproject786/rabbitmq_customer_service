package com.customer.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomerResponse {

    private long customerId;
    private String firstName;
    private String password;
    private String lastName;
    private String email;
    private String phoneNo;
    private long otp;
}
