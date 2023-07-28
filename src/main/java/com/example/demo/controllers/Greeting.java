package com.example.demo.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class Greeting {
    @GetMapping("/hello-world")
    String Home() {
        return "Hello World!!";
    };
}
