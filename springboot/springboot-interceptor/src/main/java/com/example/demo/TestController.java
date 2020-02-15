package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/user/list")
    public String userList(){
        return "user";
    }

    @GetMapping("/admin/list")
    public String adminList(){
        return "admin";
    }
}
