package com.khomsi.grid.main;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/")
    public Object sayHello(Authentication authentication) {
        return authentication.getPrincipal();
    }
}