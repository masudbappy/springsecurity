package com.masudbappy.springsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContactController {
    @GetMapping("/contact")
    public String getContactDetails() {
        // This method would typically return contact details
        return "Contact details would be returned from DB.";
    }
}
