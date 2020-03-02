package com.hblog;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebTestController {

    @GetMapping("/")
    public String webTest() {

        return "Spring Boot Test";
    }
}
