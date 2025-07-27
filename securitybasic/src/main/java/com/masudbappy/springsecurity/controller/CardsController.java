package com.masudbappy.springsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CardsController {
    @GetMapping("/myCards")
    public String getCardDetails() {
        // This method would typically return card details
        return "Card details would be returned from DB.";
    }
}
