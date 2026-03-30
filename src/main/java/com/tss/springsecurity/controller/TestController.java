package com.tss.springsecurity.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')") //  looks for ROLE_ADMIN  // hasAuthority('ADMIN') looks for ADMIN.
    public String adminTest() {
        return "Hi! Admin";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public String userTest() {
        return "Hi! User";
    }
}
