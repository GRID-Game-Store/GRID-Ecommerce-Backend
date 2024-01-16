package com.khomsi.grid.main.authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")
@Validated
@RequiredArgsConstructor
public class TestController {

    @GetMapping("/all")
    public String helloAll() {
        return "Hello all!";
    }

    @GetMapping("/user")
    public String helloUser() {
        return "Hello user!";
    }

    @GetMapping("/admin")
    public String helloAdmin() {
        return "Hello admin!";
    }


    @GetMapping("/none")
    public String hellononen() {
        return "Hello none!";
    }
}