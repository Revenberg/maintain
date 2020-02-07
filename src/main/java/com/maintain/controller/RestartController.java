package com.maintain.controller;

import com.maintain.SpringBatchApplication;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestartController {
     
    @RequestMapping("/restart")
    public void restart() {
        SpringBatchApplication.restart();        
    } 
}