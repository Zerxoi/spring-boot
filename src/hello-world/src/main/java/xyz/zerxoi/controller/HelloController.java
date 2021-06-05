package xyz.zerxoi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String hello() {
        log.info("/hello");
        return "Hello, 世界!";
    }
}
