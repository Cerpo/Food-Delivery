package com.cerpo.fd.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {
    @GetMapping("/something")
    public String getTestEntity() {
        return "AjjAjj";
    }

    @GetMapping("/something2")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public String getTestEntity2() {
        return "AjjAjj2";
    }
}
