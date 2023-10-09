package com.example.controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
    @RequestMapping("/")
    public String test() {
        return "Test";
    }
    @RequestMapping ("/hello")
    public String hello() {
        return "Hello World!";
    }
}
