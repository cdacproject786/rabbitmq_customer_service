package com.customer.service;

import com.customer.dto.CustomerRequest;
import com.customer.dto.CustomerResponse;
import com.customer.entity.Customer;
import com.customer.entity.Orders;
import com.customer.repository.CustomerRepository;
import com.customer.repository.OrderRepository;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@Slf4j
public class CustomerService {

    @Value("${rabbitmq.exchange.name}")
    private String ordersExchange;

    @Value("${rabbitmq.binding.key1.1}")
    private String ordersBindingKey1;

    @Value("${rabbitmq.binding.key1.2}")
    private String ordersBindingKey2;
    private static final long UPPER_BOUND = 999999;
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MailingService mailingService;

    @Autowired
    public RabbitTemplate rabbitTemplate;



    public CustomerResponse registerCustomer(CustomerRequest request)
    {
        Customer mappedCustomer = this.modelMapper.map(request,Customer.class);
        Customer savedCustomer = this.customerRepository.save(mappedCustomer);
        return this.modelMapper.map(savedCustomer, CustomerResponse.class);
    }

    public void placeOrder(String email) throws MessagingException {
        Customer customer = this.customerRepository.findByEmail(email);
        if(customer == null)
            throw new RuntimeException("The Resource with given email id is not found");
        else
        {
            //code to place the order
            Orders newOrder = new Orders(customer.getCustomerId(), LocalDateTime.now());

            this.orderRepository.save(newOrder);

            //code to put a message to the delivery service
            this.rabbitTemplate.convertAndSend(ordersExchange,ordersBindingKey1,customer);
            this.rabbitTemplate.convertAndSend(ordersExchange,ordersBindingKey2,customer);

            //code to trigger a mail
            this.mailingService.sendOrderPlacedEmail(customer.getEmail());
        }
    }

    public void generateOtp(String email) throws MessagingException {
        Customer customer = this.customerRepository.findByEmail(email);
        if(customer == null)
            throw new RuntimeException("No user found with the given email address");
        else {
            Random random = new Random();
            long generatedOtp = random.nextLong(UPPER_BOUND);
            customer.setOtp(generatedOtp);
            this.customerRepository.save(customer);
            //trigger a mail with the otp
            this.mailingService.sendOtp(email,generatedOtp);
        }
    }

    public boolean verifyOtp(String otp,String email)
    {
        Long numOtp = Long.parseLong(otp);
        Customer customer = this.customerRepository.findByEmailAndOtp(email, numOtp);

        if(customer == null)
            return false;
        else
            return true;
    }

    @RabbitListener(queues = {"${rabbitmq.queue.name2}"})
    public void consumedespatchOrderMessage(String message) throws MessagingException {
        log.info("Message consumed -> "+message);
        this.mailingService.senOrderDispatchedMail(message);
    }

    @RabbitListener(queues = {"${rabbitmq.queue.name3}"})
    public void orderDelivered(String email) throws MessagingException {
        this.mailingService.orderDeliveredMail(email);
    }
}
