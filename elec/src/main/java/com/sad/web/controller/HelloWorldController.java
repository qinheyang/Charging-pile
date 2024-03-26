package com.sad.web.controller;



import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HelloWorldController {

    @GetMapping("/hello")
    public String hello(){

        return "hello world";
    }
}
