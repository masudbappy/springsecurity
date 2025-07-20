package com.masudbappy.springsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoansController {
    @GetMapping("/myLoans")
    public String getLoanDetails() {
        // This method would typically return loan details
        return "Loan details would be returned from DB.";
    }
}
