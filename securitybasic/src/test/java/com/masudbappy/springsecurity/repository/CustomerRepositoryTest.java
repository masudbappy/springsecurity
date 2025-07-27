package com.masudbappy.springsecurity.repository;

import com.masudbappy.springsecurity.model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class CustomerRepositoryTest {
    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void testFindByEmail() {
        Optional<Customer> customer = customerRepository.findByEmail("amin@email.com");
        assertTrue(customer.isPresent());
    }
}
