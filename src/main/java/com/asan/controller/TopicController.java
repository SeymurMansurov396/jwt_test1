package com.asan.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/topics")
public class TopicController {

    //@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/special")
    public String vip(){
        return "Only Vip!";
    }

    @PreAuthorize("hasAnyRole('IS_AUTHENTICATED_FULLY')")
    @GetMapping("/users")
    public String user(){
        return "Authanticated user";
    }

}
