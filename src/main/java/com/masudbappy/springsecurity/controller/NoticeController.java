package com.masudbappy.springsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NoticeController {
    @GetMapping("/notice")
    public String getNotice() {
        // This method would typically return notice details
        return "Notice details would be returned from DB.";
    }
}
