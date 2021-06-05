package xyz.zerxoi.corefunction.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import xyz.zerxoi.corefunction.bean.Person;

@RestController
public class PersonController {
    final Person person;

    public PersonController(Person person) {
        this.person = person;
    }

    @RequestMapping("/person")
    public Person person() {
        return person;
    }
}
