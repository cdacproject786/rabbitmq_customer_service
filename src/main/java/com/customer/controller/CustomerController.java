package com.customer.controller;

import com.customer.dto.CustomerRequest;
import com.customer.service.CustomerService;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@Slf4j
@RestController
@RequestMapping("/api")
public class CustomerController{

    @Autowired
    private CustomerService customerService;

    @PostMapping("/customer/register")
    public ResponseEntity<?> registerCustomer(@RequestBody CustomerRequest request)
    {
        log.info(request.toString());
        this.customerService.registerCustomer(request);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping("/customer/placeorder/{email}")
    public ResponseEntity<?> placeOrder(@PathVariable("email") String email) throws MessagingException {
        this.customerService.placeOrder(email);
        return new ResponseEntity<>(null,HttpStatus.OK);
    }

    @GetMapping("/customer/otp/{email}")
    public ResponseEntity<?> getOtp(@PathVariable("email") String email) throws MessagingException {
        this.customerService.generateOtp(email);
        return new ResponseEntity<>(null,HttpStatus.OK);
    }

    @PostMapping("/customer/varifyotp/{otp}/{email}")
    public ResponseEntity<?> verifyOtp(@PathVariable("otp")String otp, @PathVariable("email") String email)
    {
        boolean validated = this.customerService.verifyOtp(otp, email);
        if(validated)
            return new ResponseEntity<>(null,HttpStatus.OK);
        else
            return new ResponseEntity<>(null,HttpStatus.FORBIDDEN);
    }
}
