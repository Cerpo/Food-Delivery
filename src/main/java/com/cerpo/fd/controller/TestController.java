package com.cerpo.fd.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {
    @GetMapping("/something")
    public String getTestEntity() {
        return "AjjAjj";
    }
}
